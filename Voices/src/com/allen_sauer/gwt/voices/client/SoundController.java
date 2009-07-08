/*
 * Copyright 2009 Fred Sauer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.allen_sauer.gwt.voices.client;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;

import com.allen_sauer.gwt.voices.client.ui.FlashMovieWidget;
import com.allen_sauer.gwt.voices.client.ui.VoicesMovieWidget;
import com.allen_sauer.gwt.voices.client.util.DOMUtil;

/**
 * Main class with which client code interact in order to create {@link Sound}
 * objects, which can be played. In addition, each SoundController defines its
 * own default volume and provides the ability to prioritize Flash based sound.
 * 
 * <p>
 * For the time being do not create 16 or more SoundControllers as that would
 * result in 16+ Flash Players, which triggers an Adobe bug, mentioned <a
 * href="http://bugzilla.mozilla.org/show_bug.cgi?id=289873#c41">here</a>.
 */
public class SoundController {
  /**
   * Enumeration for varying levels of MIME type support.
   */
  public enum MimeTypeSupport {
    /**
     * Play back of the MIME type is known to NOT be supported in this browser,
     * based on known capabilities of browsers with the same user agent and
     * installed plugins.
     */
    MIME_TYPE_NOT_SUPPORTED,

    /**
     * Play back of the MIME type is known to be supported in this browser,
     * based on known capabilities of browsers with the same user agent and
     * installed plugins, but this capability has not yet been initialized.
     * Usually this is due to a browser plugin, such as <a href=
     * 'http://www.adobe.com/products/flashplayer/'>Adobe&nbsp;Flash&nbsp;Player</a>
     * , <a href=
     * 'http://www.apple.com/quicktime/download/'>Apple&nbsp;QuickTime</a> or <a
     * href='http://www.microsoft.com/windows/windowsmedia/'>Windows&nbsp;Media&nbsp;Player</a>
     * .
     */
    MIME_TYPE_SUPPORT_NOT_READY,

    /**
     * Play back of the MIME type is known to be supported in this browser,
     * based on known capabilities of browsers with the same user agent and
     * installed plugins.
     */
    MIME_TYPE_SUPPORT_READY,

    /**
     * It is unknown (cannot be determined) whether play back of the MIME type
     * is supported in this browser, based on known capabilities of browsers
     * with the same user agent and installed plugins.
     */
    MIME_TYPE_SUPPORT_UNKNOWN,
  };

  static final int DEFAULT_VOLUME = 100;

  /**
   * @deprecated Use {@link MimeTypeSupport#MIME_TYPE_SUPPORT_UNKNOWN} enum
   *             value instead.
   */
  @Deprecated
  public static final MimeTypeSupport MIME_TYPE_SUPPORT_UNKNOWN = MimeTypeSupport.MIME_TYPE_SUPPORT_UNKNOWN;

  /**
   * @deprecated Use {@link MimeTypeSupport#MIME_TYPE_SUPPORT_READY} enum value
   *             instead.
   */
  @Deprecated
  public static final MimeTypeSupport MIME_TYPE_SUPPORTED = MimeTypeSupport.MIME_TYPE_SUPPORT_READY;

  /**
   * @deprecated Use {@link MimeTypeSupport#MIME_TYPE_SUPPORT_NOT_READY} enum
   *             value instead.
   */
  @Deprecated
  public static final MimeTypeSupport MIME_TYPE_SUPPORTED_NOT_LOADED = MimeTypeSupport.MIME_TYPE_SUPPORT_NOT_READY;

  /**
   * @deprecated Use {@link MimeTypeSupport#MIME_TYPE_NOT_SUPPORTED} enum value
   *             instead.
   */
  @Deprecated
  public static final MimeTypeSupport MIME_TYPE_UNSUPPORTED = MimeTypeSupport.MIME_TYPE_NOT_SUPPORTED;

  static {
    setVersion();
  }

  private static native void setVersion()
  /*-{
    $wnd.$GWT_VOICES_VERSION = "@GWT_VOICES_VERSION@";
  }-*/;

  private int defaultVolume = DEFAULT_VOLUME;
  private boolean prioritizeFlashSound = false;
  /**
   * Our DOM sound container which is positioned off screen.
   */
  protected final AbsolutePanel soundContainer = new AbsolutePanel();
  private VoicesMovieWidget voicesMovie;

  /**
   * Default constructor to be used by client code.
   */
  public SoundController() {
    initSoundContainer();
  }

  /**
   * Create a new Sound object using the provided MIME type and URL. To enable
   * streaming, use {@link #createSound(String, String, boolean)}.
   * 
   * @param mimeType MIME type of the new Sound object
   * @param url location of the new Sound object
   * @return a new Sound object
   */
  public Sound createSound(String mimeType, String url) {
    return createSound(mimeType, url, false);
  }

  /**
   * Create a new Sound object using the provided MIME type and URL.
   * 
   * @param mimeType MIME type of the new Sound object
   * @param url location of the new Sound object
   * @param streaming whether or not to allow play back to start before sound
   *          has been fully downloaded
   * @return a new Sound object
   */
  public Sound createSound(String mimeType, String url, boolean streaming) {
    Sound sound = implCreateSound(mimeType, url, streaming);
    sound.setVolume(defaultVolume);
    return sound;
  }

  /**
   * Lazily instantiate Flash Movie so browser plug-in is not unnecessarily
   * triggered.
   * 
   * @return the new movie widget
   */
  protected VoicesMovieWidget getVoicesMovie() {
    if (voicesMovie == null) {
      voicesMovie = new VoicesMovieWidget(DOMUtil.getUniqueId());
      soundContainer.add(voicesMovie);
    }
    return voicesMovie;
  }

  private Sound implCreateSound(String mimeType, String url, boolean streaming) {
    if (FlashMovieWidget.isExternalInterfaceSupported()) {
      VoicesMovieWidget vm = getVoicesMovie();
      MimeTypeSupport mimeTypeSupport = vm.getMimeTypeSupport(mimeType);
      if (mimeTypeSupport == MimeTypeSupport.MIME_TYPE_SUPPORT_READY
          || mimeTypeSupport == MimeTypeSupport.MIME_TYPE_SUPPORT_NOT_READY) {
        FlashSound sound = new FlashSound(mimeType, url, streaming, vm);
        return sound;
      }
    }
    return new NativeSound(mimeType, url, streaming, soundContainer.getElement());
  }

  private void initSoundContainer() {
    // place off screen with fixed dimensions and overflow:hidden
    RootPanel.get().add(soundContainer, -500, -500);
    soundContainer.setPixelSize(0, 0);
  }

  /**
   * Determine if Flash play back is prioritized over other play back methods.
   * Defaults to <code>false</code>.
   * 
   * @return <code>true</code> if Flash based sound is being prioritized
   */
  public boolean isPrioritizeFlashSound() {
    return prioritizeFlashSound;
  }

  /**
   * Set the default volume (range <code>0-100</code>) for new sound.
   * 
   * @param defaultVolume the default volume (range <code>0-100</code>) to be
   *          used for new sounds
   */
  public void setDefaultVolume(int defaultVolume) {
    this.defaultVolume = defaultVolume;
  }

  /**
   * Set whethe Flash play back is to be prioritized over other play back
   * methods. Defaults to <code>false</code>.
   * 
   * @param prioritizeFlashSound whether or not to prioritize Flash play back
   */
  public void setPrioritizeFlashSound(boolean prioritizeFlashSound) {
    this.prioritizeFlashSound = prioritizeFlashSound;
  }
}

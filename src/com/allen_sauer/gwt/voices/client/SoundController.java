/*
 * Copyright 2008 Fred Sauer
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

public class SoundController {
  /**
   * Enumeration for varying levels of MIME type support.
   */
  public enum MimeTypeSupport {
    /**
     * It is unknown (cannot be determined) whether play back of the MIME type
     * is supported in this browser, based on known capabilities of browsers
     * with the same user agent and installed plugins.
     */
    MIME_TYPE_SUPPORT_UNKNOWN,

    /**
     * Play back of the MIME type is known to be supported in this browser,
     * based on known capabilities of browsers with the same user agent and
     * installed plugins.
     */
    MIME_TYPE_SUPPORTED,

    /**
     * Play back of the MIME type is known to be supported in this browser,
     * based on known capabilities of browsers with the same user agent and
     * installed plugins, but this capability has not yet been initialized.
     * Usually this is due to a browser plugin, such as
     * <a href='http://www.adobe.com/products/flashplayer/'>Adobe&nbsp;Flash&nbsp;Player</a>,
     * <a href='http://www.apple.com/quicktime/download/'>Apple&nbsp;QuickTime</a> or
     * <a href='http://www.microsoft.com/windows/windowsmedia/'>Windows&nbsp;Media&nbsp;Player</a>.
     */
    MIME_TYPE_SUPPORTED_NOT_LOADED,

    /**
     * Play back of the MIME type is known to NOT be supported in this browser,
     * based on known capabilities of browsers with the same user agent and
     * installed plugins.
     */
    MIME_TYPE_UNSUPPORTED,
  };

  /**
   * @deprecated Use {@MimeTypeSupport.MIME_TYPE_SUPPORT_UNKNOWN} enum value instead.
   */
  @Deprecated
  public static final MimeTypeSupport MIME_TYPE_SUPPORT_UNKNOWN = MimeTypeSupport.MIME_TYPE_SUPPORT_UNKNOWN;

  /**
   * @deprecated Use {@MimeTypeSupport.MIME_TYPE_SUPPORTED} enum value instead.
   */
  @Deprecated
  public static final MimeTypeSupport MIME_TYPE_SUPPORTED = MimeTypeSupport.MIME_TYPE_SUPPORTED;

  /**
   * @deprecated Use {@MimeTypeSupport.MIME_TYPE_SUPPORTED_NOT_LOADED} enum value instead.
   */
  @Deprecated
  public static final MimeTypeSupport MIME_TYPE_SUPPORTED_NOT_LOADED = MimeTypeSupport.MIME_TYPE_SUPPORTED_NOT_LOADED;

  /**
   * @deprecated Use {@MimeTypeSupport.MIME_TYPE_UNSUPPORTED} enum value instead.
   */
  @Deprecated
  public static final MimeTypeSupport MIME_TYPE_UNSUPPORTED = MimeTypeSupport.MIME_TYPE_UNSUPPORTED;

  static final int DEFAULT_VOLUME = 100;

  static {
    setVersion();
  }

  private static native void setVersion()
  /*-{
    $wnd.$GWT_VOICES_VERSION = "@GWT_VOICES_VERSION@";
  }-*/;

  protected final AbsolutePanel soundContainer = new AbsolutePanel();
  private int defaultVolume = DEFAULT_VOLUME;
  private boolean prioritizeFlashSound = false;
  private VoicesMovieWidget voicesMovie;

  public SoundController() {
    initSoundContainer();
  }

  public Sound createSound(String mimeType, String url) {
    Sound sound = implCreateSound(mimeType, url);
    sound.setVolume(defaultVolume);
    return sound;
  }

  public boolean isPrioritizeFlashSound() {
    return prioritizeFlashSound;
  }

  public void setDefaultVolume(int defaultVolume) {
    this.defaultVolume = defaultVolume;
  }

  public void setPrioritizeFlashSound(boolean prioritizeFlashSound) {
    this.prioritizeFlashSound = prioritizeFlashSound;
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

  private Sound implCreateSound(String mimeType, String url) {
    if (FlashMovieWidget.isExternalInterfaceSupported()) {
      VoicesMovieWidget vm = getVoicesMovie();
      MimeTypeSupport mimeTypeSupport = vm.getMimeTypeSupport(mimeType);
      if (mimeTypeSupport == MimeTypeSupport.MIME_TYPE_SUPPORTED
          || mimeTypeSupport == MimeTypeSupport.MIME_TYPE_SUPPORTED_NOT_LOADED) {
        FlashSound sound = new FlashSound(mimeType, url, vm);
        return sound;
      }
    }
    return new NativeSound(mimeType, url, soundContainer.getElement());
  }

  private void initSoundContainer() {
    // place off screen with fixed dimensions and overflow:hidden
    RootPanel.get().add(soundContainer, -500, -500);
    soundContainer.setPixelSize(0, 0);
  }
}

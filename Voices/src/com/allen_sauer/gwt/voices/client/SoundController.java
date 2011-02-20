/*
 * Copyright 2010 Fred Sauer
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

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;

import com.allen_sauer.gwt.voices.client.ui.FlashMovie;
import com.allen_sauer.gwt.voices.client.ui.VoicesMovie;
import com.allen_sauer.gwt.voices.client.util.DOMUtil;

/**
 * Main class with which client code interact in order to create {@link Sound} objects, which can be
 * played. In addition, each SoundController defines its own default volume and provides the ability
 * to prioritize Flash based sound.
 *
 * <p>
 * For the time being do not create 16 or more SoundControllers as that would result in 16+ Flash
 * Players, which triggers an Adobe bug, mentioned <a
 * href="http://bugzilla.mozilla.org/show_bug.cgi?id=289873#c41">here</a>.
 */
public class SoundController {
  /**
   * Enumeration for varying levels of MIME type support.
   */
  public enum MimeTypeSupport {
    /**
     * Play back of the MIME type is known to NOT be supported in this browser, based on known
     * capabilities of browsers with the same user agent and installed plugins.
     */
    MIME_TYPE_NOT_SUPPORTED,

    /**
     * Play back of the MIME type is known to be supported in this browser, based on known
     * capabilities of browsers with the same user agent and installed plugins, but this capability
     * has not yet been initialized. Usually this is due to a browser plugin, such as <a
     * href='http://www.adobe.com/products/flashplayer/'>Adobe&nbsp;Flash&nbsp;Player</a>, <a
     * href='http://www.apple.com/quicktime/download/'>Apple&nbsp;QuickTime</a> or <a
     * href='http://www.microsoft.com/windows/windowsmedia/'>Windows&nbsp;Media&nbsp;Player</a>.
     */
    MIME_TYPE_SUPPORT_NOT_READY,

    /**
     * Play back of the MIME type is known to be supported in this browser, based on known
     * capabilities of browsers with the same user agent and installed plugins.
     */
    MIME_TYPE_SUPPORT_READY,

    /**
     * It is unknown (cannot be determined) whether play back of the MIME type is supported in this
     * browser, based on known capabilities of browsers with the same user agent and installed
     * plugins.
     */
    MIME_TYPE_SUPPORT_UNKNOWN,
  }

  static final int DEFAULT_VOLUME = 100;

  static {
    setVersion();
  }

  private static native void setVersion()
  /*-{
    $wnd.$GWT_VOICES_VERSION = "@GWT_VOICES_VERSION@";
  }-*/;

  /**
   * Our DOM sound container which is positioned off screen.
   */
  protected final Element soundContainer = DOM.createDiv();
  private int defaultVolume = DEFAULT_VOLUME;
  private VoicesMovie voicesWrapper;

  /**
   * Default constructor to be used by client code.
   */
  public SoundController() {
    initSoundContainer();
  }

  /**
   * Create a new Sound object using the provided MIME type and URL. To enable streaming, use
   * {@link #createSound(String, String, boolean)}.
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
   * @param streaming whether or not to allow play back to start before sound has been fully
   *          downloaded
   * @return a new Sound object
   */
  public Sound createSound(String mimeType, String url, boolean streaming) {
    Sound sound = createSoundImpl(mimeType, url, streaming);
    sound.setVolume(defaultVolume);
    return sound;
  }

  /**
   * Set the default volume (range <code>0-100</code>) for new sound.
   *
   * @param defaultVolume the default volume (range <code>0-100</code>) to be used for new sounds
   */
  public void setDefaultVolume(int defaultVolume) {
    this.defaultVolume = defaultVolume;
  }

  /**
   * Lazily instantiate Flash Movie so browser plug-in is not unnecessarily triggered.
   *
   * @return the new movie widget
   */
  protected VoicesMovie getVoicesMovie() {
    if (voicesWrapper == null) {
      voicesWrapper = new VoicesMovie(DOMUtil.getUniqueId());
      DOM.appendChild(soundContainer, voicesWrapper.getElement());
    }
    return voicesWrapper;
  }

  private Sound createSoundImpl(String mimeType, String url, boolean streaming) {
    if (Html5Sound.getMimeTypeSupport(mimeType) == MimeTypeSupport.MIME_TYPE_SUPPORT_READY) {
      return new Html5Sound(mimeType, url, streaming);
    }
    if (FlashMovie.isExternalInterfaceSupported()) {
      VoicesMovie vm = getVoicesMovie();
      MimeTypeSupport mimeTypeSupport = vm.getMimeTypeSupport(mimeType);
      if (mimeTypeSupport == MimeTypeSupport.MIME_TYPE_SUPPORT_READY
          || mimeTypeSupport == MimeTypeSupport.MIME_TYPE_SUPPORT_NOT_READY) {
        FlashSound sound = new FlashSound(mimeType, url, streaming, vm);
        return sound;
      }
    }
    return new NativeSound(mimeType, url, streaming, soundContainer);
  }

  private void initSoundContainer() {
    // place off screen with fixed dimensions and overflow:hidden
    RootPanel.getBodyElement().appendChild(soundContainer);
    Style style = soundContainer.getStyle();
    style.setPosition(Position.ABSOLUTE);
    style.setOverflow(Overflow.HIDDEN);
    style.setLeft(-500, Unit.PX);
    style.setTop(-500, Unit.PX);
    style.setWidth(0, Unit.PX);
    style.setHeight(0, Unit.PX);
  }
}

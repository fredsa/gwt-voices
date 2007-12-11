/*
 * Copyright 2007 Fred Sauer
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
package com.allen_sauer.gwt.voices.client.ui.impl;

import com.google.gwt.user.client.Element;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.util.StringUtil;

/**
 * {@link com.allen_sauer.gwt.voices.client.ui.NativeSoundWidget} implementation
 * for IE.
 */
public class NativeSoundImplIE6 extends NativeSoundImpl {
  private static final String[] BGSOUND_NO_VOLUME_CONTROL_MIME_TYPES = {Sound.MIME_TYPE_AUDIO_X_MIDI,};
  /**
   * List based on <a href='http://support.microsoft.com/kb/297477'>How to apply
   * a background sound to a Web page in FrontPage</a> knowledge base article.
   */
  private static final String[] BGSOUND_SUPPORTED_MIME_TYPES = {
      Sound.MIME_TYPE_AUDIO_X_AIFF, Sound.MIME_TYPE_AUDIO_BASIC, Sound.MIME_TYPE_AUDIO_X_MIDI, Sound.MIME_TYPE_AUDIO_MPEG,
      Sound.MIME_TYPE_AUDIO_X_WAV,};

  public native Element createElement(String url)
  /*-{
    var elem = $doc.createElement("bgsound");
    elem.src = url;
    // elem.loop = 1; // -1 = infinitely, 0 = one time, n = number of times
    return elem;
  }-*/;

  public int getMimeTypeSupport(String mimeType) {
    return StringUtil.contains(BGSOUND_SUPPORTED_MIME_TYPES, mimeType) ? SoundController.MIME_TYPE_SUPPORTED
        : SoundController.MIME_TYPE_UNSUPPORTED;
  }

  public void preload(Element soundControllerElement, String mimeType, String url) {
    if (!StringUtil.contains(BGSOUND_NO_VOLUME_CONTROL_MIME_TYPES, mimeType)) {
      super.preload(soundControllerElement, mimeType, url);
    }
  }

  /**
   * Best guess at conversion formula from standard -100 .. 100 range to -10000 ..
   * 10000 range used by IE.
   * 
   * @TODO location documentation for IE
   */
  public native void setBalance(Element elem, int balance)
  /*-{
    if (balance == -100) {
      balance = -10000;
    } else if (balance == 100) {
      balance = 10000;
    } else if (balance < 0) {
      balance = 100 - 10000 / (100 + balance);
    } else {
      balance = 10000 / (100 - balance) - 100;
    }
    elem.balance = "" + balance; // -10000 .. 10000
  }-*/;

  /**
   * Best guess at conversion formula from standard 0 .. 100 range to -10000 ..
   * 0 range used by IE.
   * 
   * @TODO location documentation for IE
   */
  public native void setVolume(Element elem, int volume)
  /*-{
    elem.volume = volume == 0 ? -10000 : (-10000 / volume); // -10000 .. 0
  }-*/;
}
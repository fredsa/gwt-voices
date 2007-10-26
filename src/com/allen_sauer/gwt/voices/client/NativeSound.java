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
package com.allen_sauer.gwt.voices.client;

import com.google.gwt.user.client.Element;

import com.allen_sauer.gwt.voices.client.ui.NativeSoundWidget;
import com.allen_sauer.gwt.voices.client.util.DOMUtil;

public class NativeSound extends AbstractSound {
  private NativeSoundWidget nativeSoundWidget;
  private Element preloadSoundElement;
  private Element soundControllerElement;
  private int volume;

  public NativeSound(String mimeType, String url, Element soundControllerElement) {
    super(mimeType, url);
    this.soundControllerElement = soundControllerElement;
    nativeSoundWidget = new NativeSoundWidget(soundControllerElement, url);
    int mimeTypeSupport = NativeSoundWidget.getMimeTypeSupport(mimeType);
    switch (mimeTypeSupport) {
      case SoundController.MIME_TYPE_SUPPORTED:
        setLoadState(Sound.LOAD_STATE_SUPPORTED);
        break;
      case SoundController.MIME_TYPE_UNSUPPORTED:
        setLoadState(Sound.LOAD_STATE_UNSUPPORTED);
        break;
      case SoundController.MIME_TYPE_SUPPORT_UNKNOWN:
        setLoadState(Sound.LOAD_STATE_UNKNOWN);
        break;
      case SoundController.MIME_TYPE_SUPPORTED_NOT_LOADED:
        setLoadState(Sound.LOAD_STATE_SUPPORTED_NOT_LOADED);
        break;
      default:
        throw new IllegalArgumentException("unknown MIME type support " + mimeTypeSupport);
    }
  }

  public String getSoundType() {
    return DOMUtil.getNodeName(nativeSoundWidget.getElement());
  }

  public int getVolume() {
    return volume;
  }

  public void play() {
    nativeSoundWidget.play();
  }

  public void setBalance(int balance) {
    nativeSoundWidget.setBalance(balance);
  }

  public void setVolume(int volume) {
    this.volume = volume;
    nativeSoundWidget.setVolume(volume);
  }

  public void stop() {
    nativeSoundWidget.stop();
  }
}

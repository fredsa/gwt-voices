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

import com.google.gwt.user.client.Element;

import com.allen_sauer.gwt.voices.client.SoundController.MimeTypeSupport;
import com.allen_sauer.gwt.voices.client.ui.NativeSoundWidget;
import com.allen_sauer.gwt.voices.client.util.DOMUtil;

public class NativeSound extends AbstractSound {
  private final NativeSoundWidget nativeSoundWidget;
  private int volume;

  public NativeSound(String mimeType, String url, Element soundControllerElement) {
    super(mimeType, url);
    nativeSoundWidget = new NativeSoundWidget(soundControllerElement, mimeType, url);
    MimeTypeSupport mimeTypeSupport = NativeSoundWidget.getMimeTypeSupport(mimeType);
    switch (mimeTypeSupport) {
      case MIME_TYPE_SUPPORT_READY:
        setLoadState(Sound.LoadState.LOAD_STATE_SUPPORTED);
        break;
      case MIME_TYPE_NOT_SUPPORTED:
        setLoadState(Sound.LoadState.LOAD_STATE_UNSUPPORTED);
        break;
      case MIME_TYPE_SUPPORT_UNKNOWN:
        setLoadState(Sound.LoadState.LOAD_STATE_UNKNOWN);
        break;
      case MIME_TYPE_SUPPORT_NOT_READY:
        setLoadState(Sound.LoadState.LOAD_STATE_SUPPORTED_NOT_LOADED);
        break;
      default:
        throw new IllegalArgumentException("unknown MIME type support " + mimeTypeSupport);
    }
  }

  @Override
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

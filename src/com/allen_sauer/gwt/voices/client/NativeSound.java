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

import com.allen_sauer.gwt.voices.client.util.DOMUtil;

public class NativeSound extends AbstractSound {
  private Element soundControllerElement;
  private Element soundElement;

  public NativeSound(Element soundControllerElement, String url) {
    this.soundControllerElement = soundControllerElement;
    soundElement = DOMUtil.createSoundElement(url);
  }

  public int getLoadState() {
    return Sound.LOAD_STATE_UNKNOWN;
  }

  public String getUrl() {
    return null; // TODO Replace auto-generated method stub
  }

  public boolean isStreaming() {
    return false; // TODO Replace auto-generated method stub
  }

  public void play() {
    DOMUtil.playSoundElement(soundControllerElement, soundElement);
  }

  public void setBalance(int balance) {
    DOMUtil.setSoundElementBalance(soundElement, balance);
  }

  public void setVolume(int volume) {
    DOMUtil.setSoundElementVolume(soundElement, volume);
  }

  public void stop() {
    DOMUtil.stopSoundElement(soundElement);
  }
}

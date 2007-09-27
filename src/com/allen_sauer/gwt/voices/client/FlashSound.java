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
import com.google.gwt.user.client.ui.RootPanel;

import java.util.ArrayList;

class FlashSound extends AbstractSound {
  private static ArrayList soundList = new ArrayList();
  private static VoicesMovie voicesMovie = new VoicesMovie();

  static {
    voicesMovie.setPixelSize(10, 10);
    RootPanel.get().add(voicesMovie, -500, -500);
  }

  private static void soundCompleted(int index) {
    ((FlashSound) soundList.get(index)).soundCompleted();
  }

  private static void soundLoaded(final int index) {
    ((FlashSound) soundList.get(index)).soundLoaded();
  }

  private int balance = 0;
  private final int id;
  private int loadState = Sound.LOAD_STATE_NOT_LOADED;
  private boolean playSoundWhenLoaded = false;
  private final Element soundControllerElement;
  private final String url;
  private int volume = 100;

  public FlashSound(Element soundControllerElement, String url) {
    this.soundControllerElement = soundControllerElement;
    id = soundList.size();
    this.url = url;
    soundList.add(this);
    voicesMovie.registerSound(this);
  }

  public int getId() {
    return id;
  }

  public int getLoadState() {
    return loadState;
  }

  public String getUrl() {
    return url;
  }

  public void play() {
    if (loadState == Sound.LOAD_STATE_LOADED) {
      voicesMovie.playSound(id);
    } else {
      playSoundWhenLoaded = true;
    }
  }

  public void setBalance(int balance) {
    this.balance = balance;
    if (loadState == Sound.LOAD_STATE_LOADED) {
      voicesMovie.setBalance(id, balance);
    }
  }

  public void setVolume(int volume) {
    this.volume = volume;
    if (loadState == Sound.LOAD_STATE_LOADED) {
      voicesMovie.setVolume(id, volume);
    }
  }

  public void stop() {
    if (loadState == Sound.LOAD_STATE_LOADED) {
      voicesMovie.stopSound(id);
    } else {
      playSoundWhenLoaded = false;
    }
  }

  public String toString() {
    return "FlashSound(\"" + url + "\")";
  }

  protected void soundCompleted() {
    soundHandlerCollection.fireOnSoundComplete(this);
  }

  protected void soundLoaded() {
    loadState = Sound.LOAD_STATE_LOADED;
    if (volume != 100) {
      voicesMovie.setVolume(id, volume);
    }
    if (playSoundWhenLoaded) {
      play();
      playSoundWhenLoaded = false;
    }
    soundHandlerCollection.fireOnSoundLoadStateChange(this);
  }
}

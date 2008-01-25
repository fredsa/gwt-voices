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

import com.allen_sauer.gwt.voices.client.ui.VoicesMovieWidget;

import java.util.ArrayList;

public class FlashSound extends AbstractSound {
  private static ArrayList soundList = new ArrayList();

  private static void soundCompleted(int index) {
    ((FlashSound) soundList.get(index)).soundCompleted();
  }

  private static void soundLoaded(final int index) {
    ((FlashSound) soundList.get(index)).soundLoaded();
  }

  private int balance = 0;
  private boolean playSoundWhenLoaded = false;
  private final int soundNumber;
  private final VoicesMovieWidget voicesMovie;
  private int volume = SoundController.DEFAULT_VOLUME;

  public FlashSound(String mimeType, String url, VoicesMovieWidget voicesMovie) {
    super(mimeType, url);
    this.voicesMovie = voicesMovie;
    soundNumber = soundList.size();
    soundList.add(this);
    voicesMovie.registerSound(this);
  }

  public int getSoundNumber() {
    return soundNumber;
  }

  public String getSoundType() {
    return "Flash";
  }

  public int getVolume() {
    return volume;
  }

  public void play() {
    if (getLoadState() == Sound.LOAD_STATE_LOADED) {
      voicesMovie.playSound(soundNumber);
    } else {
      playSoundWhenLoaded = true;
    }
  }

  public void setBalance(int balance) {
    this.balance = balance;
    if (getLoadState() == Sound.LOAD_STATE_LOADED) {
      voicesMovie.setBalance(soundNumber, balance);
    }
  }

  public void setVolume(int volume) {
    this.volume = volume;
    if (getLoadState() == Sound.LOAD_STATE_LOADED) {
      voicesMovie.setVolume(soundNumber, volume);
    }
  }

  public void stop() {
    if (getLoadState() == Sound.LOAD_STATE_LOADED) {
      voicesMovie.stopSound(soundNumber);
    } else {
      playSoundWhenLoaded = false;
    }
  }

  protected void soundCompleted() {
    soundHandlerCollection.fireOnSoundComplete(this);
  }

  protected void soundLoaded() {
    setLoadState(Sound.LOAD_STATE_LOADED);
    if (volume != SoundController.DEFAULT_VOLUME) {
      voicesMovie.setVolume(soundNumber, volume);
    }
    if (playSoundWhenLoaded) {
      play();
      playSoundWhenLoaded = false;
    }
    soundHandlerCollection.fireOnSoundLoadStateChange(this);
  }
}
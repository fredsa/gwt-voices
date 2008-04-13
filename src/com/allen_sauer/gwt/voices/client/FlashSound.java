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

import com.allen_sauer.gwt.voices.client.ui.VoicesMovieWidget;

import java.util.ArrayList;

/**
 * <a href='http://www.adobe.com/products/flashplayer/'>Adobe&nbsp;Flash&nbsp;Player</a>
 * based sound.
 */
public class FlashSound extends AbstractSound {
  // CHECKSTYLE_JAVADOC_OFF

  private static ArrayList<FlashSound> soundList = new ArrayList<FlashSound>();

  @SuppressWarnings("unused")
  private static void playbackCompleted(int index) {
    soundList.get(index).playbackCompleted();
  }

  @SuppressWarnings("unused")
  private static void soundLoaded(final int index) {
    soundList.get(index).soundLoaded();
  }

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

  @Override
  public String getSoundType() {
    return "Flash";
  }

  public int getVolume() {
    return volume;
  }

  public void play() {
    if (getLoadState() == Sound.LoadState.LOAD_STATE_SUPPORTED_AND_READY) {
      voicesMovie.playSound(soundNumber);
    } else {
      playSoundWhenLoaded = true;
    }
  }

  public void setBalance(int balance) {
    if (getLoadState() == Sound.LoadState.LOAD_STATE_SUPPORTED_AND_READY) {
      voicesMovie.setBalance(soundNumber, balance);
    }
  }

  public void setVolume(int volume) {
    this.volume = volume;
    if (getLoadState() == Sound.LoadState.LOAD_STATE_SUPPORTED_AND_READY) {
      voicesMovie.setVolume(soundNumber, volume);
    }
  }

  public void stop() {
    if (getLoadState() == Sound.LoadState.LOAD_STATE_SUPPORTED_AND_READY) {
      voicesMovie.stopSound(soundNumber);
    } else {
      playSoundWhenLoaded = false;
    }
  }

  protected void playbackCompleted() {
    soundHandlerCollection.fireOnPlaybackComplete(this);
  }

  protected void soundLoaded() {
    setLoadState(Sound.LoadState.LOAD_STATE_SUPPORTED_AND_READY);
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

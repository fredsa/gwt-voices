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

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;

import com.allen_sauer.gwt.voices.client.ui.FlashMovieWidget;
import com.allen_sauer.gwt.voices.client.ui.VoicesMovieWidget;

public class SoundController {
  public static final int MIME_TYPE_SUPPORT_UNKNOWN = 1;
  public static final int MIME_TYPE_SUPPORTED = 4;
  public static final int MIME_TYPE_SUPPORTED_NOT_LOADED = 3;
  public static final int MIME_TYPE_UNSUPPORTED = 2;

  private static String getLowercaseExtension(String filename) {
    int pos = filename.indexOf('.');
    if (pos == -1) {
      return "";
    } else {
      return filename.substring(pos).toLowerCase();
    }
  }

  protected final AbsolutePanel soundContainer = new AbsolutePanel();
  private int defaultVolume;
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
   */
  protected VoicesMovieWidget getVoicesMovie() {
    if (voicesMovie == null) {
      voicesMovie = new VoicesMovieWidget();
      soundContainer.add(voicesMovie);
    }
    return voicesMovie;
  }

  private Sound implCreateSound(String mimeType, String url) {
    if (FlashMovieWidget.isExternalInterfaceSupported()) {
      VoicesMovieWidget vm = getVoicesMovie();
      int mimeTypeSupport = vm.getMimeTypeSupport(mimeType);
      if (mimeTypeSupport == MIME_TYPE_SUPPORTED
          || mimeTypeSupport == MIME_TYPE_SUPPORTED_NOT_LOADED) {
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

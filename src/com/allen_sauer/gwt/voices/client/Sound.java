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

import com.google.gwt.user.client.ui.RootPanel;

import com.allen_sauer.gwt.voices.client.handler.FiresSoundEvents;
import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.allen_sauer.gwt.voices.client.handler.SoundHandlerCollection;
import com.allen_sauer.gwt.voices.client.handler.SoundLoadEvent;

import java.util.ArrayList;

public class Sound implements FiresSoundEvents {
  private static ArrayList soundList = new ArrayList();
  private static VoicesMovie voicesMovie = new VoicesMovie();

  static {
    voicesMovie.setPixelSize(10, 10);
    RootPanel.get().add(voicesMovie, -500, -500);
  }

  private static void soundCompleted(int index) {
    ((Sound) soundList.get(index)).soundCompleted();
  }

  private static void soundLoaded(int index) {
    ((Sound) soundList.get(index)).soundLoaded();
  }

  private final int id;
  private boolean loaded = false;
  private SoundHandlerCollection soundHandlerCollection = new SoundHandlerCollection();
  private final boolean streaming;
  private final String url;

  public Sound(String url, boolean streaming) {
    id = soundList.size();
    this.url = url;
    this.streaming = streaming;
    soundList.add(this);
    voicesMovie.registerSound(this);
  }

  public void addSoundHandler(SoundHandler handler) {
    soundHandlerCollection.add(handler);
    if (loaded) {
      handler.onSoundLoad(new SoundLoadEvent(this));
    }
  }

  public int getId() {
    return id;
  }

  public String getUrl() {
    return url;
  }

  public boolean isLoaded() {
    return loaded;
  }

  public boolean isStreaming() {
    return streaming;
  }

  public boolean play() {
    if (loaded) {
      return voicesMovie.playSound(id);
    } else {
      return false;
    }
  }

  public void removeSoundHandler(SoundHandler handler) {
    soundHandlerCollection.remove(handler);
  }

  public String toString() {
    return "Sound[" + id + ", " + url + ", " + (streaming ? "" : "NOT ")
        + "STREAMING]";
  }

  protected void soundCompleted() {
    soundHandlerCollection.fireOnSoundComplete(this);
  }

  protected void soundLoaded() {
    loaded = true;
    soundHandlerCollection.fireOnSoundLoad(this);
  }
}

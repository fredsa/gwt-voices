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

import java.util.ArrayList;

public class Sound {
  private static ArrayList soundList = new ArrayList();
  private static VoicesMovie voicesMovie = new VoicesMovie();

  static {
    voicesMovie.setPixelSize(10, 10);
    RootPanel.get().add(voicesMovie, -500, -500);
  }

  private static void soundCompleted(int id) {
    // Log.debug("soundCompleted(" + id + ")");
  }

  private static void soundLoaded(int index) {
    // Log.debug("soundLoaded(" + index + ")");
    ((Sound) soundList.get(index)).loaded = true;
  }

  private final int id;
  private boolean loaded = false;
  private final boolean streaming;
  private final String url;

  public Sound(String url, boolean streaming) {
    id = soundList.size();
    this.url = url;
    this.streaming = streaming;
    soundList.add(this);
    voicesMovie.registerSound(this);
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

  public String toString() {
    return "Sound[" + id + ", " + url + ", " + (streaming ? "" : "NOT ")
        + "STREAMING]";
  }
}

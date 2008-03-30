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
package com.allen_sauer.gwt.voices.client.handler;

import com.allen_sauer.gwt.voices.client.Sound;

import java.util.EventObject;

public class SoundLoadStateChangeEvent extends EventObject {
  private static String loadStateToString(int loadState) {
    switch (loadState) {
      case Sound.LOAD_STATE_LOADED:
        return "loaded";
      case Sound.LOAD_STATE_SUPPORTED_NOT_LOADED:
        return "supported; not loaded";
      case Sound.LOAD_STATE_SUPPORTED:
        return "supported";
      case Sound.LOAD_STATE_UNSUPPORTED:
        return "unsupported";
      case Sound.LOAD_STATE_UNKNOWN:
        return "unknown load state";
      case Sound.LOAD_STATE_UNINITIALIZED:
        return "uninitialized";
      default:
        throw new IllegalArgumentException("loadState=" + loadState);
    }
  }

  private final int loadState;

  public SoundLoadStateChangeEvent(Object source) {
    super(source);
    Sound sound = (Sound) source;
    loadState = sound.getLoadState();
  }

  public int getLoadState() {
    return loadState;
  }

  public String getLoadStateAsString() {
    return loadStateToString(loadState);
  }

  public String toString() {
    Sound sound = (Sound) getSource();
    return "SoundLoadStateChangeEvent: " + sound + "; state=" + loadStateToString(loadState);
  }
}
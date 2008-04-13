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
package com.allen_sauer.gwt.voices.client.handler;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.Sound.LoadState;

import java.util.EventObject;

@SuppressWarnings("serial")
public class SoundLoadStateChangeEvent extends EventObject {
  private static String loadStateToString(LoadState loadState) {
    switch (loadState) {
      case LOAD_STATE_SUPPORTED_AND_READY:
        return "loaded";
      case LOAD_STATE_SUPPORTED_NOT_READY:
        return "supported; not loaded";
      case LOAD_STATE_SUPPORTED_MAYBE_READY:
        return "supported";
      case LOAD_STATE_NOT_SUPPORTED:
        return "unsupported";
      case LOAD_STATE_NOT_KNOWN:
        return "unknown load state";
      case LOAD_STATE_UNINITIALIZED:
        return "uninitialized";
      default:
        throw new IllegalArgumentException("loadState=" + loadState);
    }
  }

  private final LoadState loadState;

  public SoundLoadStateChangeEvent(Object source) {
    super(source);
    Sound sound = (Sound) source;
    loadState = sound.getLoadState();
  }

  public LoadState getLoadState() {
    return loadState;
  }

  public String getLoadStateAsString() {
    return loadStateToString(loadState);
  }

  @Override
  public String toString() {
    Sound sound = (Sound) getSource();
    return "SoundLoadStateChangeEvent: " + sound + "; state=" + loadStateToString(loadState);
  }
}
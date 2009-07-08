/*
 * Copyright 2009 Fred Sauer
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
import com.allen_sauer.gwt.voices.client.util.StringUtil;

import java.util.EventObject;

/**
 * Event object representing a load state change.
 */
@SuppressWarnings("serial")
public class SoundLoadStateChangeEvent extends EventObject {
  /**
   * Convert a load state <code>enum</code> to a string representation.
   * 
   * @param loadState the load state
   * @return a string representation of the load state
   */
  private static String loadStateToString(LoadState loadState) {
    switch (loadState) {
      case LOAD_STATE_SUPPORTED_AND_READY:
        return "supported and ready";
      case LOAD_STATE_SUPPORTED_NOT_READY:
        return "supported; not ready";
      case LOAD_STATE_SUPPORTED_MAYBE_READY:
        return "supported; maybe ready";
      case LOAD_STATE_NOT_SUPPORTED:
        return "not supported";
      case LOAD_STATE_SUPPORT_NOT_KNOWN:
        return "support not known";
      case LOAD_STATE_UNINITIALIZED:
        return "uninitialized";
      default:
        throw new IllegalArgumentException("loadState=" + loadState);
    }
  }

  private final LoadState loadState;

  /**
   * Event constructor.
   * 
   * @param source the sound object
   */
  public SoundLoadStateChangeEvent(Object source) {
    super(source);
    Sound sound = (Sound) source;
    loadState = sound.getLoadState();
  }

  /**
   * Determine the load state for this event.
   * 
   * @return this event's load state
   */
  public LoadState getLoadState() {
    return loadState;
  }

  /**
   * Get a string representation of this event's load state.
   * 
   * @return string representation of this event's load state
   */
  public String getLoadStateAsString() {
    return loadStateToString(loadState);
  }

  /**
   * Get a string representation of this event object.
   * 
   * @return a string representation of this event object
   */
  @Override
  public String toString() {
    Sound sound = (Sound) getSource();
    return StringUtil.getSimpleName(SoundLoadStateChangeEvent.class) + ": " + sound + "; state="
        + loadStateToString(loadState);
  }
}
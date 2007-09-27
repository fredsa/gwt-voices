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

import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.allen_sauer.gwt.voices.client.handler.SoundHandlerCollection;
import com.allen_sauer.gwt.voices.client.handler.SoundLoadStateChangeEvent;

abstract class AbstractSound implements Sound {
  protected final SoundHandlerCollection soundHandlerCollection = new SoundHandlerCollection();

  public void addSoundHandler(SoundHandler handler) {
    soundHandlerCollection.add(handler);
    int loadState = getLoadState();
    if (loadState != Sound.LOAD_STATE_NOT_LOADED) {
      handler.onSoundLoadStateChange(new SoundLoadStateChangeEvent(this));
    }
  }

  public void removeSoundHandler(SoundHandler handler) {
    soundHandlerCollection.remove(handler);
  }
}

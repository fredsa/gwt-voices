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
import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.allen_sauer.gwt.voices.client.handler.SoundHandlerCollection;
import com.allen_sauer.gwt.voices.client.handler.SoundLoadStateChangeEvent;

abstract class AbstractSound implements Sound {
  private static final LoadState INITIAL_LOAD_STATE = LOAD_STATE_UNINITIALIZED;
  protected final SoundHandlerCollection soundHandlerCollection = new SoundHandlerCollection();

  private LoadState loadState = INITIAL_LOAD_STATE;
  private final String mimeType;
  private final String url;

  public AbstractSound(String mimeType, String url) {
    this.mimeType = mimeType;
    this.url = url;
  }

  public void addEventHandler(SoundHandler handler) {
    soundHandlerCollection.add(handler);
    if (loadState != INITIAL_LOAD_STATE) {
      handler.onSoundLoadStateChange(new SoundLoadStateChangeEvent(this));
    }
  }

  public final LoadState getLoadState() {
    return loadState;
  }

  public String getMimeType() {
    return mimeType;
  }

  public abstract String getSoundType();

  public String getUrl() {
    return url;
  }

  public void removeEventHandler(SoundHandler handler) {
    soundHandlerCollection.remove(handler);
  }

  public final void setLoadState(LoadState loadState) {
    this.loadState = loadState;
    if (loadState != INITIAL_LOAD_STATE) {
      soundHandlerCollection.fireOnSoundLoadStateChange(this);
    }
  }

  @Override
  public String toString() {
    return getSoundType() + "(\"" + mimeType + "\", \"" + url + "\")";
  }
}

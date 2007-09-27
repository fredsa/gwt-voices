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

import com.allen_sauer.gwt.voices.client.handler.FiresSoundEvents;

public interface Sound extends FiresSoundEvents {
  public static final int LOAD_STATE_LOADED = 3;
  public static final int LOAD_STATE_NOT_LOADED = 2;
  public static final int LOAD_STATE_UNKNOWN = 1;

  public abstract int getLoadState();

  public abstract String getUrl();

  public abstract void play();

  public abstract void setBalance(int balance);

  public abstract void setVolume(int volume);

  public abstract void stop();
}
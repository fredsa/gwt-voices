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

import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("serial")
public class SoundHandlerCollection extends ArrayList<SoundHandler> {
  public void fireOnSoundComplete(Object sender) {
    SoundCompleteEvent event = new SoundCompleteEvent(sender);

    for (SoundHandler handler : this) {
      handler.onSoundComplete(event);
    }
  }

  public void fireOnSoundLoadStateChange(Object sender) {
    SoundLoadStateChangeEvent event = new SoundLoadStateChangeEvent(sender);

    for (Iterator<SoundHandler> iterator = iterator(); iterator.hasNext();) {
      SoundHandler handler = iterator.next();
      handler.onSoundLoadStateChange(event);
    }
  }
}

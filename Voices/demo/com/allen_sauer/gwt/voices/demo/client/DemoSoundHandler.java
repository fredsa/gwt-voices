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
package com.allen_sauer.gwt.voices.demo.client;

import com.google.gwt.user.client.ui.HTML;

import com.allen_sauer.gwt.voices.client.handler.PlaybackCompleteEvent;
import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.allen_sauer.gwt.voices.client.handler.SoundLoadStateChangeEvent;

/**
 * Sound handler for the online demo, which displays sound events as they occur.
 */
public final class DemoSoundHandler implements SoundHandler {
  // CHECKSTYLE_JAVADOC_OFF
  private static final String BLUE = "blue";
  private static final String GREEN = "green";
  private final HTML eventTextArea;

  public DemoSoundHandler(HTML soundHandlerHTML) {
    eventTextArea = soundHandlerHTML;
  }

  private void log(String text, String color) {
    eventTextArea.setHTML("<span style='color: " + color + "'>" + text + "</span>"
        + (eventTextArea.getHTML().length() == 0 ? "" : "<br>") + eventTextArea.getHTML());
  }

  public void onPlaybackComplete(PlaybackCompleteEvent event) {
    log(event.toString(), GREEN);
  }

  public void onSoundLoadStateChange(SoundLoadStateChangeEvent event) {
    log(event.toString(), BLUE);
  }
}
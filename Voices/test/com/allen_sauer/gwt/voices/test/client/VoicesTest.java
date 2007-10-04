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
package com.allen_sauer.gwt.voices.test.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.log.client.LogUncaughtExceptionHandler;
import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;

public class VoicesTest implements EntryPoint {
  private static native String getCompatMode()
  /*-{
    return $doc.compatMode;
  }-*/;

  public void onModuleLoad() {
    // set uncaught exception handler
    GWT.setUncaughtExceptionHandler(new LogUncaughtExceptionHandler() {
    });

    // use deferred command to catch initialization exceptions
    DeferredCommand.addCommand(new Command() {
      public void execute() {
        onModuleLoad2();
      }
    });
  }

  public void onModuleLoad2() {
    RootPanel.get().add(
        new HTML("VoicesTest is in <b>" + getCompatMode() + "</b> mode."));
    SoundController soundController = new SoundController();
    final Sound sound = soundController.createSound(Sound.MIME_TYPE_AUDIO_MPEG,
        "freesoundproject/33637__HerbertBoland__CinematicBoomNorm.mp3");
    Button button = new Button("play");
    button.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        sound.play();
      }
    });
    RootPanel.get().add(button);
  }
}

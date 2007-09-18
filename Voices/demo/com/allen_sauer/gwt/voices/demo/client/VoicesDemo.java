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
package com.allen_sauer.gwt.voices.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.allen_sauer.gwt.log.client.LogUncaughtExceptionHandler;

public class VoicesDemo implements EntryPoint {
  private static final String DEMO_SOUNDS_PANEL = "demo-sounds-panel";
  private static final String DEMO_EVENT_TEXT_AREA = "demo-event-text-area";
  private static final DemoSound[] demoSounds = {
      new DemoSound("28917__junggle__btn107", "junggle",
          "http://freesound.iua.upf.edu/usersViewSingle.php?id=128404",
          "btn107.wav",
          "http://freesound.iua.upf.edu/samplesViewSingle.php?id=28917"),
      new DemoSound("36846__EcoDTR__LaserRocket", "EcoDTR",
          "http://freesound.iua.upf.edu/usersViewSingle.php?id=181367",
          "LaserRocket.wav",
          "http://freesound.iua.upf.edu/samplesViewSingle.php?id=36846"),
      new DemoSound("35643__sandyrb__USAT_BOMB", "sandyrb",
          "http://freesound.iua.upf.edu/usersViewSingle.php?id=14771",
          "USAT BOMB.wav",
          "http://freesound.iua.upf.edu/samplesViewSingle.php?id=35643"),
      new DemoSound("34961__grandpablaine2__grenade_reverse_reverb",
          "grandpablaine2",
          "http://freesound.iua.upf.edu/usersViewSingle.php?id=147084",
          "grenade_reverse_reverb.wav",
          "http://freesound.iua.upf.edu/samplesViewSingle.php?id=34961"),
      new DemoSound("33637__HerbertBoland__CinematicBoomNorm", "HerbertBoland",
          "http://freesound.iua.upf.edu/usersViewSingle.php?id=129090",
          "CinematicBoomNorm.wav",
          "http://freesound.iua.upf.edu/samplesViewSingle.php?id=33637"),};

  public void onModuleLoad() {
    // set uncaught exception handler
    GWT.setUncaughtExceptionHandler(new LogUncaughtExceptionHandler());

    // use deferred command to catch initialization exceptions
    DeferredCommand.addCommand(new Command() {
      public void execute() {
        onModuleLoad2();
      }
    });
  }

  public void onModuleLoad2() {
    VerticalPanel soundsPanel = new VerticalPanel();
    DOM.setInnerHTML(RootPanel.get(DEMO_SOUNDS_PANEL).getElement(), null);
    RootPanel.get(DEMO_SOUNDS_PANEL).add(soundsPanel);

    // text area to log drag events as they are triggered
    final HTML eventTextArea = new HTML();
    RootPanel.get(DEMO_EVENT_TEXT_AREA).add(eventTextArea);

    DemoSoundHandler demoSoundHandler = new DemoSoundHandler(eventTextArea);

    for (int i = 0; i < demoSounds.length; i++) {
      demoSounds[i].addSoundHandler(demoSoundHandler);
      soundsPanel.add(new DemoSoundPanel(demoSounds[i]));
    }
  }
}

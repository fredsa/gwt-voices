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

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.voices.client.handler.SoundCompleteEvent;
import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.allen_sauer.gwt.voices.client.handler.SoundLoadEvent;

public class DemoSoundPanel extends Composite {
  public DemoSoundPanel(final DemoSound demoSound) {
    // use a horizontal panel to hold our content
    HorizontalPanel horizontalPanel = new HorizontalPanel();
    initWidget(horizontalPanel);

    // add a (temporarily disabled) play button
    final Button playButton = new Button("wait...");
    playButton.setEnabled(false);
    playButton.addStyleName("voices-button");
    horizontalPanel.add(playButton);

    // enable the play button once the sound has loaded
    demoSound.addSoundHandler(new SoundHandler() {
      public void onSoundComplete(SoundCompleteEvent event) {
      }

      public void onSoundLoad(SoundLoadEvent event) {
        playButton.setEnabled(true);
        playButton.setText("play");
      }
    });

    // play the sound when button is clicked
    playButton.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        demoSound.play();
      }
    });

    // display a description of the sound next to the button
    horizontalPanel.add(new HTML("&nbsp;" + demoSound.toHTMLString()));
  }
}

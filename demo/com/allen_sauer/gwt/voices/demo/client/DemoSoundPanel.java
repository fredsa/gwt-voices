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
package com.allen_sauer.gwt.voices.demo.client;

import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.voices.client.handler.PlaybackCompleteEvent;
import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.allen_sauer.gwt.voices.client.handler.SoundLoadStateChangeEvent;

// CHECKSTYLE_JAVADOC_OFF
public class DemoSoundPanel extends Composite {
  public DemoSoundPanel(final ThirdPartySound thirdPartySound) {
    // use a horizontal panel to hold our content
    HorizontalPanel horizontalPanel = new HorizontalPanel();
    initWidget(horizontalPanel);

    // add a (temporarily disabled) play button
    final Button playButton = new Button("wait...");
    playButton.setEnabled(false);
    playButton.addStyleName("voices-button");
    horizontalPanel.add(playButton);

    final Button stopButton = new Button("wait...");
    stopButton.setEnabled(false);
    stopButton.addStyleName("voices-button");
    horizontalPanel.add(stopButton);

    // display a description of the sound next to the button
    horizontalPanel.add(new HTML("&nbsp;" + thirdPartySound.toHTMLString()));

    // display a load state status
    final HTML loadStateHTML = new HTML();
    horizontalPanel.add(loadStateHTML);

    // enable the play button once the sound has loaded
    thirdPartySound.getSound().addEventHandler(new SoundHandler() {
      public void onPlaybackComplete(PlaybackCompleteEvent event) {
      }

      public void onSoundLoadStateChange(final SoundLoadStateChangeEvent event) {
        // simulate a slight variable delay for local development
        new Timer() {
          @Override
          public void run() {
            loadStateHTML.setHTML("&nbsp; (load state: <code>" + event.getLoadStateAsString()
                + "</code>)");
            switch (event.getLoadState()) {
              case LOAD_STATE_SUPPORTED_AND_READY:
              case LOAD_STATE_SUPPORTED_NOT_READY:
              case LOAD_STATE_SUPPORTED_MAYBE_READY:
                playButton.setEnabled(true);
                playButton.setText("play");
                stopButton.setEnabled(true);
                stopButton.setText("stop");
                break;
              case LOAD_STATE_NOT_SUPPORTED:
                playButton.setText("(plugin unavailable)");
                break;
              case LOAD_STATE_SUPPORT_NOT_KNOWN:
                playButton.setEnabled(true);
                playButton.setText("play (may not work)");
                stopButton.setEnabled(true);
                stopButton.setText("stop");
                break;
              case LOAD_STATE_UNINITIALIZED:
              default:
                throw new IllegalArgumentException("Unhandled state " + event.getLoadState());
            }
          }
        }.schedule(Random.nextInt(500) + 200);
      }
    });

    // play the sound when button is clicked
    playButton.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        thirdPartySound.getSound().play();
      }
    });

    stopButton.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        thirdPartySound.getSound().stop();
      }
    });
  }
}
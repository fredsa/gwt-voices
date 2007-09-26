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

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.voices.client.util.DOMUtil;

public class NativeSound extends AbstractSound {
  private Element soundControllerElement;
  private Element soundElement;

  public NativeSound(Element soundControllerElement, String url) {
    this.soundControllerElement = soundControllerElement;
    soundElement = DOMUtil.createSoundElement(url);

    Button stopButton = new Button("stop");
    RootPanel.get().add(stopButton);
    stopButton.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        stop();
      }
    });

    Button playButton = new Button("play");
    RootPanel.get().add(playButton);
    playButton.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        play();
      }
    });

    Button lowButton = new Button("low");
    RootPanel.get().add(lowButton);
    lowButton.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        setVolume(5);
      }
    });

    Button highButton = new Button("high");
    RootPanel.get().add(highButton);
    highButton.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        setVolume(100);
      }
    });

    Button volumeButton = new Button("set volume");
    RootPanel.get().add(volumeButton);
    final TextBox volumeTextBox = new TextBox();
    volumeTextBox.setText("10");
    RootPanel.get().add(volumeTextBox);
    volumeButton.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        setVolume(Integer.parseInt(volumeTextBox.getText()));
        play();
      }
    });

    Button balanceButton = new Button("set balance");
    RootPanel.get().add(balanceButton);
    final TextBox balanceTextBox = new TextBox();
    balanceTextBox.setText("-100");
    RootPanel.get().add(balanceTextBox);
    balanceButton.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        setBalance(Integer.parseInt(balanceTextBox.getText()));
        play();
      }
    });
  }

  public int getLoadState() {
    return Sound.LOAD_STATE_UNKNOWN;
  }

  public String getUrl() {
    return null; // TODO Replace auto-generated method stub
  }

  public boolean isStreaming() {
    return false; // TODO Replace auto-generated method stub
  }

  public void play() {
    DOMUtil.playSoundElement(soundControllerElement, soundElement);
  }

  public void setBalance(int balance) {
    DOMUtil.setSoundElementBalance(soundElement, balance);
  }

  public void setVolume(int volume) {
    DOMUtil.setSoundElementVolume(soundElement, volume);
  }

  public void stop() {
    DOMUtil.stopSoundElement(soundElement);
  }
}

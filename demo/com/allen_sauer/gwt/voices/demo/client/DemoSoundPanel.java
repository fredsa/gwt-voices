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

public class DemoSoundPanel extends Composite {
  public DemoSoundPanel(final DemoSound demoSound) {
    HorizontalPanel horizontalPanel = new HorizontalPanel();
    initWidget(horizontalPanel);

    Button playButton = new Button("play");
    playButton.addStyleName("voices-button");
    horizontalPanel.add(playButton);

    playButton.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        demoSound.play();
      }
    });

    horizontalPanel.add(new HTML("&nbsp;" + demoSound.toHTMLString()));
  }
}

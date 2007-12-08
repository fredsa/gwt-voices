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
package com.allen_sauer.gwt.voices.demo.client.ui;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.demo.client.DemoSoundHandler;
import com.allen_sauer.gwt.voices.demo.client.DemoSoundPanel;
import com.allen_sauer.gwt.voices.demo.client.FreeSound;

import java.util.ArrayList;
import java.util.Iterator;

public class MimeTypeDemo extends DeferredContentPanel {
  private final DemoSoundHandler demoSoundHandler;
  private final ArrayList freesoundList;
  private final String mimeType;

  public MimeTypeDemo(String mimeType, ArrayList freesoundList, DemoSoundHandler demoSoundHandler) {
    this.mimeType = mimeType;
    this.freesoundList = freesoundList;
    this.demoSoundHandler = demoSoundHandler;
  }

  public Panel initContent() {
    VerticalPanel containerPanel = new VerticalPanel();
    SoundController soundController = new SoundController();

    HTML note = null;
    VerticalPanel soundsPanel = new VerticalPanel();

    for (Iterator iterator = freesoundList.iterator(); iterator.hasNext();) {
      FreeSound freesound = (FreeSound) iterator.next();
      Sound sound = soundController.createSound(mimeType, freesound.getActualURL());
      sound.addEventHandler(demoSoundHandler);
      freesound.setSound(sound);
      if (note == null) {
        note = new HTML("Note:<ul><li>Some browsers will play these sound files natively, while others may require"
            + " plugins such as <a href='http://www.adobe.com/products/flashplayer/'>Adobe&nbsp;Flash&nbsp;Player</a>,"
            + " <a href='http://www.apple.com/quicktime/download/'>Apple&nbsp;QuickTime</a>"
            + " or <a href='http://www.microsoft.com/windows/windowsmedia/'>Windows&nbsp;Media&nbsp;Player</a>"
            + " in order to hear sound.</li>\n"
            + "<li>Based on your current browser/platform/plugin configuration, gwt-voices is using (or trying to use)" + " <b>"
            + sound.getSoundType() + "</b> to play <code>" + mimeType + "</code> sounds.</li></ul>");
        note.addStyleName("demo-note");
        containerPanel.add(note);
      }
      soundsPanel.add(new DemoSoundPanel(freesound));
    }
    containerPanel.add(soundsPanel);
    return containerPanel;
  }
}

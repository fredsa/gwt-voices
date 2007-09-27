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
package com.allen_sauer.gwt.voices.demo.client.embed;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.demo.client.DemoSoundHandler;
import com.allen_sauer.gwt.voices.demo.client.DemoSoundPanel;
import com.allen_sauer.gwt.voices.demo.client.FreeSound;

public class EmbedDemo extends Composite {
  private VerticalPanel containerPanel;
  private final DemoSoundHandler demoSoundHandler;
  private FreeSound[] freeSounds;

  public EmbedDemo(DemoSoundHandler demoSoundHandler) {
    this.demoSoundHandler = demoSoundHandler;
    containerPanel = new VerticalPanel();
    initWidget(containerPanel);
  }

  protected void onLoad() {
    super.onLoad();
    SoundController soundController = new SoundController();

    freeSounds = new FreeSound[] {
        new FreeSound(soundController, "35631__reinsamba__crystal_glass.wav",
            "crystal_glass",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=35631",
            "reinsamba",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=18799"),
        new FreeSound(soundController, "38403__THE_bizniss__snap.wav", "snap",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=38403",
            "THE_bizniss",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=382028"),
        new FreeSound(soundController,
            "22740__FranciscoPadilla__37_Click_Finger.wav", "37 Click Finger",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=22740",
            "FranciscoPadilla",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=132693"),
        new FreeSound(soundController, "9874__vixuxx__crow.wav", "crow",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=9874",
            "vixuxx",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=28679"),};

    HTML note = new HTML(
        "Note: Some browsers will play these sound files natively, while others may require"
            + " plugins such as <a href='http://www.apple.com/quicktime/download/'>QuickTime</a>"
            + " or <a href='http://www.microsoft.com/windows/windowsmedia/'>Windows Media Player</a>"
            + " in order to hear sound.<br>\n");
    note.addStyleName("demo-note");
    containerPanel.add(note);

    VerticalPanel soundsPanel = new VerticalPanel();
    containerPanel.add(soundsPanel);
    for (int i = 0; i < freeSounds.length; i++) {
      freeSounds[i].getSound().addSoundHandler(demoSoundHandler);
      soundsPanel.add(new DemoSoundPanel(freeSounds[i]));
    }
  }
}

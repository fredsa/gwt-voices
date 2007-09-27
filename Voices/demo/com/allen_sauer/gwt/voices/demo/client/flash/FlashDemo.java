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
package com.allen_sauer.gwt.voices.demo.client.flash;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.demo.client.DemoSoundHandler;
import com.allen_sauer.gwt.voices.demo.client.DemoSoundPanel;
import com.allen_sauer.gwt.voices.demo.client.FreeSound;

public class FlashDemo extends Composite {
  private VerticalPanel containerPanel;
  private final DemoSoundHandler demoSoundHandler;
  private FreeSound[] freeSounds;

  public FlashDemo(DemoSoundHandler demoSoundHandler) {
    this.demoSoundHandler = demoSoundHandler;
    containerPanel = new VerticalPanel();
    initWidget(containerPanel);
  }

  protected void onLoad() {
    super.onLoad();
    SoundController soundController = new SoundController();

    freeSounds = new FreeSound[] {
        new FreeSound(soundController, "28917__junggle__btn107.mp3", "btn107",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=28917",
            "junggle",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=128404"),
        new FreeSound(soundController, "36846__EcoDTR__LaserRocket.mp3",
            "LaserRocket",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=36846",
            "EcoDTR",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=181367"),
        new FreeSound(soundController, "35643__sandyrb__USAT_BOMB.mp3",
            "USAT BOMB",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=35643",
            "sandyrb",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=14771"),
        new FreeSound(soundController,
            "34961__grandpablaine2__grenade_reverse_reverb.mp3",
            "grenade_reverse_reverb",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=34961",
            "grandpablaine2",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=147084"),
        new FreeSound(soundController,
            "33637__HerbertBoland__CinematicBoomNorm.mp3", "CinematicBoomNorm",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=33637",
            "HerbertBoland",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=129090"),};

    HTML note = new HTML(
        "Note: You will need the <a href='http://www.adobe.com/products/flashplayer/'>Adobe Flash Player</a>"
            + " (version 8 or newer) browser plugin in order to hear sound.<br>\n"
            + " Also, audio files will not load from the local filesystem."
            + " You will need to use a web server.");
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

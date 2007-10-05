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
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.demo.client.ui.DelayedAttachDisclosurePanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class VoicesDemo implements EntryPoint {
  private static final String DEMO_EVENT_TEXT_AREA = "demo-event-text-area";
  private static final String DEMO_PANELS = "demo-panels";
  private static FreeSound[] freeSounds;
  private static HashMap mimeTypeSoundMap = new HashMap();

  static {
    freeSounds = new FreeSound[] {
        new FreeSound(Sound.MIME_TYPE_AUDIO_X_WAV,
            "freesoundproject/35631__reinsamba__crystal_glass.wav",
            "crystal_glass",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=35631",
            "reinsamba",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=18799"),
        new FreeSound(Sound.MIME_TYPE_AUDIO_X_WAV,
            "freesoundproject/38403__THE_bizniss__snap.wav", "snap",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=38403",
            "THE_bizniss",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=382028"),
        new FreeSound(Sound.MIME_TYPE_AUDIO_X_WAV,
            "freesoundproject/22740__FranciscoPadilla__37_Click_Finger.wav",
            "37 Click Finger",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=22740",
            "FranciscoPadilla",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=132693"),
        new FreeSound(Sound.MIME_TYPE_AUDIO_X_WAV,
            "freesoundproject/9874__vixuxx__crow.wav", "crow",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=9874",
            "vixuxx",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=28679"),
        new FreeSound(Sound.MIME_TYPE_AUDIO_X_AIFF,
            "freesoundproject/9874__vixuxx__crow.aiff", "crow",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=9874",
            "vixuxx",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=28679"),
        new FreeSound(Sound.MIME_TYPE_AUDIO_BASIC,
            "freesoundproject/9874__vixuxx__crow.au", "crow",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=9874",
            "vixuxx",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=28679"),
        new FreeSound(Sound.MIME_TYPE_AUDIO_MPEG,
            "freesoundproject/28917__junggle__btn107.mp3", "btn107",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=28917",
            "junggle",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=128404"),
        new FreeSound(Sound.MIME_TYPE_AUDIO_MPEG,
            "freesoundproject/36846__EcoDTR__LaserRocket.mp3", "LaserRocket",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=36846",
            "EcoDTR",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=181367"),
        new FreeSound(Sound.MIME_TYPE_AUDIO_MPEG,
            "freesoundproject/35643__sandyrb__USAT_BOMB.mp3", "USAT BOMB",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=35643",
            "sandyrb",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=14771"),
        new FreeSound(
            Sound.MIME_TYPE_AUDIO_MPEG,
            "freesoundproject/34961__grandpablaine2__grenade_reverse_reverb.mp3",
            "grenade_reverse_reverb",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=34961",
            "grandpablaine2",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=147084"),
        new FreeSound(Sound.MIME_TYPE_AUDIO_MPEG,
            "freesoundproject/33637__HerbertBoland__CinematicBoomNorm.mp3",
            "CinematicBoomNorm",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=33637",
            "HerbertBoland",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=129090"),};

    for (int i = 0; i < freeSounds.length; i++) {
      String mimeType = freeSounds[i].getMimeType();
      ArrayList freesoundList = (ArrayList) mimeTypeSoundMap.get(mimeType);
      if (freesoundList == null) {
        freesoundList = new ArrayList();
        mimeTypeSoundMap.put(mimeType, freesoundList);
      }
      freesoundList.add(freeSounds[i]);
    }
  }

  public void onModuleLoad() {
    // set uncaught exception handler
    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      public void onUncaughtException(Throwable throwable) {
        String text = "Uncaught exception: ";
        while (throwable != null) {
          StackTraceElement[] stackTraceElements = throwable.getStackTrace();
          text += new String(throwable.toString() + "\n");
          for (int i = 0; i < stackTraceElements.length; i++) {
            text += "    at " + stackTraceElements[i] + "\n";
          }
          throwable = throwable.getCause();
          if (throwable != null) {
            text += "Caused by: ";
          }
        }
        DialogBox dialogBox = new DialogBox(true);
        DOM.setStyleAttribute(dialogBox.getElement(), "backgroundColor",
            "#ABCDEF");
        text = text.replaceAll(" ", "&nbsp;");
        dialogBox.setHTML("<pre>" + text + "</pre>");
        dialogBox.center();
      }
    });

    // use deferred command to catch initialization exceptions
    DeferredCommand.addCommand(new Command() {
      public void execute() {
        onModuleLoad2();
      }
    });
  }

  public void onModuleLoad2() {
    // text area to log sound events as they are triggered
    final HTML eventTextArea = new HTML();
    RootPanel.get(DEMO_EVENT_TEXT_AREA).add(eventTextArea);

    DemoSoundHandler demoSoundHandler = new DemoSoundHandler(eventTextArea);

    DOM.setInnerHTML(RootPanel.get(DEMO_PANELS).getElement(), "");

    for (Iterator iterator = mimeTypeSoundMap.keySet().iterator(); iterator.hasNext();) {
      String mimeType = (String) iterator.next();
      ArrayList freesoundList = (ArrayList) mimeTypeSoundMap.get(mimeType);
      DelayedAttachDisclosurePanel disclosurePanel = new DelayedAttachDisclosurePanel(
          mimeType, new MimeTypeDemo(mimeType, freesoundList, demoSoundHandler));
      RootPanel.get(DEMO_PANELS).add(disclosurePanel);
    }
  }
}

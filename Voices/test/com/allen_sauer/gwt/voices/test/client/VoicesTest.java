/*
 * Copyright 2009 Fred Sauer
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.allen_sauer.gwt.voices.test.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.DataResource.DoNotEmbed;
import com.google.gwt.resources.client.DataResource.MimeType;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import com.allen_sauer.gwt.voices.client.FlashSound;
import com.allen_sauer.gwt.voices.client.Html5Sound;
import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.handler.PlaybackCompleteEvent;
import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.allen_sauer.gwt.voices.client.handler.SoundLoadStateChangeEvent;

// CHECKSTYLE_JAVADOC_OFF
public class VoicesTest implements EntryPoint {
  interface Bundle extends ClientBundle {
    Bundle RESOURCES = GWT.create(Bundle.class);

    @MimeType("audio/mpeg")
    @Source("28917__junggle__btn107.mp3")
    DataResource junggle();

    @MimeType("audio/mpeg")
    @Source("28917__junggle__btn107.mp3")
    @DoNotEmbed
    DataResource junggleNoEmbed();

    @Source("36846__EcoDTR__LaserRocket.mp3")
    DataResource laserRocket();
  }

  private static native String getCompatMode()
  /*-{
		return $doc.compatMode;
  }-*/;

  /**
   * Use DeferredCommand to ensure an UncaughtExceptionHandler is installed before any of our real
   * code executes.
   */
  public void onModuleLoad() {
    // set uncaught exception handler
    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      public void onUncaughtException(Throwable throwable) {
        String text = "Uncaught exception: ";
        while (throwable != null) {
          StackTraceElement[] stackTraceElements = throwable.getStackTrace();
          text += throwable.toString() + "\n";
          for (StackTraceElement element : stackTraceElements) {
            text += "    at " + element + "\n";
          }
          throwable = throwable.getCause();
          if (throwable != null) {
            text += "Caused by: ";
          }
        }
        DialogBox dialogBox = new DialogBox(true);
        DOM.setStyleAttribute(dialogBox.getElement(), "backgroundColor", "#ABCDEF");
        text = text.replaceAll(" ", "&nbsp;");
        dialogBox.setHTML("<pre>" + text + "</pre>");
        dialogBox.center();
      }
    });

    // use deferred command to catch initialization exceptions
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      public void execute() {
        onModuleLoad2();
      }
    });
  }

  /**
   * The actual entry point that we use.
   */
  public void onModuleLoad2() {
    RootPanel.get().add(new HTML("VoicesTest is in <b>" + getCompatMode() + "</b> mode."));

    addTest();
  }
  private void addButton(SoundController sc, String mimeType, String url) {
    final Sound sound = sc.createSound(mimeType, url, false);
    sound.addEventHandler(new SoundHandler() {
      public void onPlaybackComplete(PlaybackCompleteEvent event) {
        System.out.println(event);
      }

      public void onSoundLoadStateChange(SoundLoadStateChangeEvent event) {
        System.out.println(event);
      }
    });
    Button button = new Button(url);
    button.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        sound.play();
      }
    });
    RootPanel.get().add(button);
  }

  private void addButton(final String[] urls, String[] mimeTypes, SoundController sc, int i) {
    final Sound sound = sc.createSound(mimeTypes[i], urls[i], false);
    sound.addEventHandler(new SoundHandler() {
      public void onPlaybackComplete(PlaybackCompleteEvent event) {
        System.out.println(event);
      }

      public void onSoundLoadStateChange(SoundLoadStateChangeEvent event) {
        System.out.println(event);
      }
    });
    Button button = new Button(urls[i]);
    final int index = i;
    button.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        sound.play();
      }
    });
    RootPanel.get().add(button);
  }

  @SuppressWarnings("deprecation")
  private void addTest() {
    final String[] urls = new String[] {
        "freesoundproject/36846__EcoDTR__LaserRocket.mp3",
        "freesoundproject/22740__FranciscoPadilla__37_Click_Finger.wav",
        "http://media3.7digital.com/clips/34/2934485.clip.mp3",};
    String[] mimeTypes = new String[] {
        Sound.MIME_TYPE_AUDIO_MPEG, Sound.MIME_TYPE_AUDIO_X_WAV, Sound.MIME_TYPE_AUDIO_MPEG,};
    SoundController sc = new SoundController();
    sc.setPreferredSoundType(FlashSound.class);
    sc.setPreferredSoundType(Html5Sound.class);

    for (int i = 0; i < urls.length; i++) {
      String mimeType = mimeTypes[i];
      String url = urls[i];
      addButton(sc, mimeType, url);
    }
    addButton(sc, Sound.MIME_TYPE_AUDIO_MPEG, Bundle.RESOURCES.laserRocket().getUrl());
    addButton(sc, Sound.MIME_TYPE_AUDIO_MPEG, Bundle.RESOURCES.junggle().getUrl());
    addButton(sc, Sound.MIME_TYPE_AUDIO_MPEG, Bundle.RESOURCES.junggleNoEmbed().getUrl());
  }
}

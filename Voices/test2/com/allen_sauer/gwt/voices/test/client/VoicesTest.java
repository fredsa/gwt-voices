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
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
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

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.SoundType;
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

    @DoNotEmbed
    @MimeType("audio/mpeg")
    @Source("28917__junggle__btn107.mp3")
    DataResource junggleNoEmbed();

    @Source("36846__EcoDTR__LaserRocket.mp3")
    DataResource laserRocket();

    @Source("En-us-squid.ogg")
    DataResource squid();

    @DoNotEmbed
    @MimeType("audio/mpeg")
    @Source("the-quick-brown-fox-jumps-over-the-lazy-dog.mp3")
    DataResource theQuickBrownFoxJumpsOverTheLazyDog();
  }

  private static final int CHANNELS = 40;

  private static native String getCompatMode()
  /*-{
    return $doc.compatMode;
  }-*/;

  /**
   * Use DeferredCommand to ensure an UncaughtExceptionHandler is installed before any of our real
   * code executes.
   */
  @Override
  public void onModuleLoad() {
    // set uncaught exception handler
    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      @Override
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
        DialogBox dialogBox = new DialogBox(true, false);
        DOM.setStyleAttribute(dialogBox.getElement(), "backgroundColor", "#ABCDEF");
        text = text.replaceAll(" ", "&nbsp;");
        dialogBox.setHTML("<pre>" + text + "</pre>");
        dialogBox.center();
      }
    });

    // use deferred command to catch initialization exceptions
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        onModuleLoad2();
      }
    });
  }

  /**
   * The actual entry point that we use.
   */
  public void onModuleLoad2() {
    log("VoicesTest is in <b>" + getCompatMode() + "</b> mode.");

    addPlaybackTests();
    addChannelTest();
  }

  protected void log(String msg) {
    RootPanel.get().add(new HTML(msg));
  }

  private void addButton(SoundController sc, String mimeType, String url) {
    final Sound sound = sc.createSound(mimeType, url, false, false);
    sound.addEventHandler(new SoundHandler() {
      @Override
      public void onPlaybackComplete(PlaybackCompleteEvent event) {
        System.out.println(event);
      }

      @Override
      public void onSoundLoadStateChange(SoundLoadStateChangeEvent event) {
        System.out.println(event);
      }
    });
    Button button = new Button(sound.getClass().getName() + " " + url);
    button.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        sound.play();
      }
    });
    RootPanel.get().add(button);
  }

  private void addButton(final String[] urls, String[] mimeTypes, SoundController sc, int i) {
    final Sound sound = sc.createSound(mimeTypes[i], urls[i], false, false);
    sound.addEventHandler(new SoundHandler() {
      @Override
      public void onPlaybackComplete(PlaybackCompleteEvent event) {
        System.out.println(event);
      }

      @Override
      public void onSoundLoadStateChange(SoundLoadStateChangeEvent event) {
        System.out.println(event);
      }
    });
    Button button = new Button(urls[i]);
    button.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        sound.play();
      }
    });
    RootPanel.get().add(button);
  }

  private void addChannelTest() {
    final Button button = new Button("channel test");
    RootPanel.get().add(button);
    button.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        startChannelTest();
      }
    });
  }

  @SuppressWarnings({"deprecation"})
  private void addPlaybackTests() {
    final String[] urls = new String[] {
        "freesoundproject/36846__EcoDTR__LaserRocket.mp3",
        "freesoundproject/22740__FranciscoPadilla__37_Click_Finger.wav",
        "http://media3.7digital.com/clips/34/2934485.clip.mp3",};
    String[] mimeTypes = new String[] {
        Sound.MIME_TYPE_AUDIO_MPEG, Sound.MIME_TYPE_AUDIO_X_WAV, Sound.MIME_TYPE_AUDIO_MPEG,};
    SoundController sc = new SoundController();
    sc.setPreferredSoundTypes(SoundType.FLASH);
    sc.setPreferredSoundTypes(SoundType.HTML5);

    for (int i = 0; i < urls.length; i++) {
      String mimeType = mimeTypes[i];
      String url = urls[i];
      addButton(sc, mimeType, url);
    }
    addButton(sc, Sound.MIME_TYPE_AUDIO_MPEG, Bundle.RESOURCES.laserRocket().getUrl());
    addButton(sc, Sound.MIME_TYPE_AUDIO_MPEG, Bundle.RESOURCES.junggle().getUrl());
    addButton(sc, Sound.MIME_TYPE_AUDIO_MPEG, Bundle.RESOURCES.junggleNoEmbed().getUrl());
  }

  @SuppressWarnings({"deprecation"})
  private void startChannelTest() {
    SoundController sc = new SoundController();
    sc.setPreferredSoundTypes(SoundType.HTML5);
    sc.setPreferredSoundTypes(SoundType.FLASH);
    final Sound[] sounds = new Sound[CHANNELS];
    final HTML[] status = new HTML[CHANNELS];

    for (int i = 0; i < CHANNELS; i++) {
      final int ii = i;
      status[i] = new HTML("Channel " + i);
      RootPanel.get().add(status[i]);

      sounds[i] = sc.createSound("audio/mpeg",
          Bundle.RESOURCES.theQuickBrownFoxJumpsOverTheLazyDog().getUrl());

      sounds[i].addEventHandler(new SoundHandler() {
        @Override
        public void onPlaybackComplete(PlaybackCompleteEvent event) {
          status[ii].setHTML("Channel " + ii + ": " + event.toString());
        }

        @Override
        public void onSoundLoadStateChange(SoundLoadStateChangeEvent event) {
          status[ii].setHTML("Channel " + ii + ": " + event.toString());
        }
      });
    }

    Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
      int i = 0;

      @Override
      public boolean execute() {
        //        boolean played = sounds[i].play();
        //        status[i].setHTML("Channel " + i + ": " + (played ? "played" : "!played"));
        return ++i < sounds.length;
      }
    }, 10);
  }

}

/*
 * Copyright 2010 Fred Sauer
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
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.SoundType;
import com.allen_sauer.gwt.voices.demo.client.ui.DeferredContentDisclosurePanel;
import com.allen_sauer.gwt.voices.demo.client.ui.MimeTypeDemo;
import com.allen_sauer.gwt.voices.demo.client.ui.SupportedMimeTypeSummary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

// CHECKSTYLE_JAVADOC_OFF
public class VoicesDemo implements EntryPoint {
  @SuppressWarnings("deprecation")
  public static SortedSet<String> MIME_TYPES = new TreeSet<String>(Arrays.asList(new String[] {
      Sound.MIME_TYPE_AUDIO_BASIC, Sound.MIME_TYPE_AUDIO_MPEG_MP3, Sound.MIME_TYPE_AUDIO_MPEG,
      Sound.MIME_TYPE_AUDIO_MP4, Sound.MIME_TYPE_AUDIO_MP4_MP4A_40_2, Sound.MIME_TYPE_AUDIO_X_AIFF,
      Sound.MIME_TYPE_AUDIO_X_MIDI, Sound.MIME_TYPE_AUDIO_WAV_UNKNOWN,
      Sound.MIME_TYPE_AUDIO_WAV_PCM, Sound.MIME_TYPE_AUDIO_WAV_ADPCM, Sound.MIME_TYPE_AUDIO_OGG,
      Sound.MIME_TYPE_AUDIO_OGG_FLAC, Sound.MIME_TYPE_AUDIO_OGG_SPEEX,
      Sound.MIME_TYPE_AUDIO_OGG_VORBIS,}));
  private static HashMap<String, ArrayList<ThirdPartySound>> mimeTypeSoundMap = new HashMap<String, ArrayList<ThirdPartySound>>();

  private static ThirdPartySound[] thirdPartySounds;

  static {
    thirdPartySounds = new ThirdPartySound[] {
        new FreesoundProjectSound(Sound.MIME_TYPE_AUDIO_WAV_PCM,
            "freesoundproject/35631__reinsamba__crystal_glass.wav", "crystal_glass",
            "http://freesound.org/people/reinsamba/sounds/35631", "reinsamba",
            "http://freesound.org/people/reinsamba/",
            "RIFF (little-endian) data, WAVE audio, Microsoft PCM, 16 bit, stereo 44100 Hz"),
        new FreesoundProjectSound(Sound.MIME_TYPE_AUDIO_WAV_PCM,
            "freesoundproject/38403__THE_bizniss__snap.wav", "snap",
            "http://freesound.org/people/THE_bizniss/sounds/38403/", "THE_bizniss",
            "http://freesound.org/people/THE_bizniss",
            "RIFF (little-endian) data, WAVE audio, Microsoft PCM, 16 bit, mono 44100 Hz"),
        new FreesoundProjectSound(Sound.MIME_TYPE_AUDIO_WAV_PCM,
            "freesoundproject/22740__FranciscoPadilla__37_Click_Finger.wav", "37 Click Finger",
            "http://freesound.org/people/FranciscoPadilla/sounds/22740/", "FranciscoPadilla",
            "http://freesound.org/people/FranciscoPadilla",
            "RIFF (little-endian) data, WAVE audio, Microsoft PCM, 16 bit, mono 44100 Hz"),

        new FreesoundProjectSound(Sound.MIME_TYPE_AUDIO_WAV_ADPCM,
            "freesoundproject/9874__vixuxx__crow.wav", "crow",
            "http://freesound.org/people/FranciscoPadilla/sounds/22740/", "vixuxx",
            "http://freesound.org/people/FranciscoPadilla",
            "RIFF (little-endian) data, WAVE audio, IMA ADPCM, mono 22050 Hz"),

        new FreesoundProjectSound(Sound.MIME_TYPE_AUDIO_X_AIFF,
            "freesoundproject/9874__vixuxx__crow.aiff", "crow",
            "http://freesound.org/people/vixuxx/sounds/9874/", "vixuxx",
            "http://freesound.org/people/vixuxx", "IFF data, AIFF audio"),

        new FreesoundProjectSound(Sound.MIME_TYPE_AUDIO_BASIC,
            "freesoundproject/9874__vixuxx__crow.au", "crow",
            "http://freesound.org/people/vixuxx/sounds/9874/", "vixuxx",
            "http://freesound.org/people/vixuxx",
            "Sun/NeXT audio data: 16-bit linear PCM, mono, 22050 Hz"),

        new FreesoundProjectSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3,
            "freesoundproject/28917__junggle__btn107.mp3", "btn107",
            "http://freesound.org/people/junggle/sounds/28917/", "junggle",
            "http://freesound.org/people/junggle",
            "Audio file with ID3 version 23.0 tag, MP3 encoding"),
        new FreesoundProjectSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3,
            "freesoundproject/36846__EcoDTR__LaserRocket.mp3", "LaserRocket",
            "http://freesound.org/people/EcoDTR/sounds/36846/", "EcoDTR",
            "http://freesound.org/people/EcoDTR",
            "Audio file with ID3 version 23.0 tag, MP3 encoding"),
        new FreesoundProjectSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3,
            "freesoundproject/35643__sandyrb__USAT_BOMB.mp3", "USAT BOMB",
            "http://freesound.org/people/sandyrb/sounds/35643/", "sandyrb",
            "http://freesound.org/people/sandyrb",
            "Audio file with ID3 version 23.0 tag, MP3 encoding"),
        new FreesoundProjectSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3,
            "freesoundproject/34961__grandpablaine2__grenade_reverse_reverb.mp3",
            "grenade_reverse_reverb",
            "http://freesound.org/people/grandpablaine2/sounds/34961/", "grandpablaine2",
            "http://freesound.org/people/grandpablaine2",
            "Audio file with ID3 version 2\\012- 2.\\012- 0 tag\\012- , MP3 encoding"),
        new FreesoundProjectSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3,
            "freesoundproject/33637__HerbertBoland__CinematicBoomNorm.mp3", "CinematicBoomNorm",
            "http://freesound.org/people/HerbertBoland/sounds/33637/", "HerbertBoland",
            "http://freesound.org/people/HerbertBoland",
            "Audio file with ID3 version 2\\012- 2.\\012- 0 tag\\012- , MP3 encoding"),

        new FreesoundProjectSound(Sound.MIME_TYPE_AUDIO_OGG_VORBIS,
            "freesoundproject/91960__billengholm_yahoo.com__opensurdo.ogg", "opensurdo",
            "http://www.freesound.org/people/billengholm@yahoo.com/sounds/91960/", "billengholm@yahoo.com",
            "http://www.freesound.org/people/billengholm@yahoo.com1",
            "Ogg data, Vorbis audio, stereo, 44100 Hz, ~128000 bps"),
        new WikipediaSound(Sound.MIME_TYPE_AUDIO_OGG_VORBIS, "wikipedia/En-us-squid.ogg",
            "En-us-squid.ogg",
            "http://upload.wikimedia.org/wikipedia/commons/f/f1/En-us-squid.ogg",
            "http://en.wiktionary.org/wiki/squid",
            "Ogg data, Vorbis audio, mono, 44100 Hz, ~96001 bps"),

        new WikipediaSound(Sound.MIME_TYPE_AUDIO_X_MIDI, "wikipedia/Bass_sample2.mid",
            "Bass_sample2.mid",
            "http://upload.wikimedia.org/wikipedia/commons/b/b0/Bass_sample2.mid",
            "http://en.wikipedia.org/wiki/Musical_Instrument_Digital_Interface",
            "Standard MIDI data (format 1) using 2 tracks at 1/240"),
        new WikipediaSound(Sound.MIME_TYPE_AUDIO_X_MIDI, "wikipedia/Drum_sample.mid",
            "Drum_sample.mid",
            "http://upload.wikimedia.org/wikipedia/commons/6/61/Drum_sample.mid",
            "http://en.wikipedia.org/wiki/Musical_Instrument_Digital_Interface",
            "Standard MIDI data (format 1) using 2 tracks at 1/240"),

        new FreesoundProjectSound(Sound.MIME_TYPE_AUDIO_WAV_PCM,
            "freesoundproject/12742__Leady__reverse_fill_effect.wav", "reverse fill effect",
            "http://www.freesound.org/samplesViewSingle.php?id=12742", "Leady",
            "http://www.freesound.org/usersViewSingle.php?id=34346",
            "RIFF (little-endian) data, WAVE audio, Microsoft PCM, 16 bit, stereo 44100 Hz"),

        new WikipediaSound(Sound.MIME_TYPE_AUDIO_OGG_VORBIS, "wikipedia/Rondo_Alla_Turka.ogg",
            "Rondo_Alla_Turka.ogg",
            "http://upload.wikimedia.org/wikipedia/commons/b/bd/Rondo_Alla_Turka.ogg",
            "http://en.wikipedia.org/wiki/File:Rondo_Alla_Turka.ogg",
            "Ogg data, Vorbis audio, stereo, 44100 Hz, ~80001 bps, created by: Xiph.Org libVorbis I (1.0)"),

    };
  }

  @Override
  public void onModuleLoad() {
    // set uncaught exception handler
    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(Throwable throwable) {
        String text = "Uncaught exception: ";
        while (throwable != null) {
          StackTraceElement[] stackTraceElements = throwable.getStackTrace();
          text += new String(throwable.toString() + "\n");
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
        System.err.print(text);
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

  public void onModuleLoad2() {
    RootPanel mainPanel = RootPanel.get("demo-main-panel");
    StyleInjector.injectAtStart(DemoClientBundle.INSTANCE.css().getText());

    // text area to log sound events as they are triggered
    final HTML eventTextArea = new HTML();
    RootPanel.get("demo-event-text-area").add(eventTextArea);

    DemoSoundHandler demoSoundHandler = new DemoSoundHandler(eventTextArea);

    DisclosurePanel soundSupportMatrix = new DisclosurePanel(
        "Sound Support Matrix for this browser");
    soundSupportMatrix.setContent(new SupportedMimeTypeSummary());
    mainPanel.add(soundSupportMatrix);

    DisclosurePanel crowdSourceSupportMatrix = new DisclosurePanel(
        "HTML5 MIME Type support in popular browsers");
    Frame crowdSourceFrame = new Frame("https://crowd-source-dot-gwt-voices.appspot.com/?embed=true");
    crowdSourceFrame.setPixelSize(3000, 600);
    crowdSourceFrame.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
    crowdSourceSupportMatrix.setContent(crowdSourceFrame);
    mainPanel.add(crowdSourceSupportMatrix);

    // initialize mimeTypeSoundMap
    for (ThirdPartySound thirdPartySound : thirdPartySounds) {
      String mimeType = thirdPartySound.getMimeType();
      assert MIME_TYPES.contains(mimeType) : "MIME_TYPES must contain '" + mimeType + "'";
      ArrayList<ThirdPartySound> freesoundList = mimeTypeSoundMap.get(mimeType);
      if (freesoundList == null) {
        freesoundList = new ArrayList<ThirdPartySound>();
        mimeTypeSoundMap.put(mimeType, freesoundList);
      }
      freesoundList.add(thirdPartySound);
    }

    // display one panel for each unique MIME type, using the order supplied by
    // MIME_TYPES
    for (String mimeType : MIME_TYPES) {
      ArrayList<ThirdPartySound> soundList = mimeTypeSoundMap.get(mimeType);
      if (soundList != null) {
        SoundController sc = new SoundController();
        for (SoundType soundType : SoundType.values()) {
          maybeShowType(mainPanel, demoSoundHandler, mimeType, soundList, sc, soundType);
        }
      }
    }
    DOM.getElementById("demo-loading").removeFromParent();
  }

  @SuppressWarnings({"deprecation"})
  private void maybeShowType(RootPanel mainPanel, DemoSoundHandler demoSoundHandler,
      String mimeType, ArrayList<ThirdPartySound> soundList, SoundController sc,
      SoundType soundType) {
    soundList = copyOf(soundList);

    sc.setPreferredSoundTypes(soundType);
    Sound snd = sc.createSound(mimeType, soundList.get(0).getActualURL());
    if (snd.getSoundType() == soundType) {
      mainPanel.add(new DeferredContentDisclosurePanel(
          mimeType + " (" + snd.getSoundType().name() + ")",
          new MimeTypeDemo(mimeType, soundList, demoSoundHandler, soundType)));
    }
  }

  private ArrayList<ThirdPartySound> copyOf(ArrayList<ThirdPartySound> soundList) {
    ArrayList<ThirdPartySound> soundListCopy;
    soundListCopy = new ArrayList<ThirdPartySound>();
    for (ThirdPartySound s : soundList) {
      soundListCopy.add(s.copyOf());
    }
    return soundListCopy;
  }

}

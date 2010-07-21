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
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.demo.client.ui.DeferredContentDisclosurePanel;
import com.allen_sauer.gwt.voices.demo.client.ui.MimeTypeDemo;
import com.allen_sauer.gwt.voices.demo.client.ui.SupportedMimeTypeSummary;

import java.util.ArrayList;
import java.util.HashMap;

// CHECKSTYLE_JAVADOC_OFF
public class VoicesDemo implements EntryPoint {
  private static ThirdPartySound[] freeSounds;
  private static HashMap<String, ArrayList<ThirdPartySound>> mimeTypeSoundMap = new HashMap<
      String, ArrayList<ThirdPartySound>>();

  /**
   * IANA assigned media type <code>audio/mp4</code>, from RFC 4337. Typical filename extensions
   * include <code>.mp1</code>, <code>.mp2</code> and <code>.mp3</code>.
   */
  private static String MIME_TYPE_AUDIO_MP4 = "audio/mp4";

  /**
   * IANA assigned media type <code>aaudio/mp4; codecs=mp4a.40.2</code>, from RFC 4337. Typical filename extensions
   * include <code>.mp1</code>, <code>.mp2</code> and <code>.mp3</code>.
   */
  private static String MIME_TYPE_AUDIO_MP4_MP4A_40_2 = "audio/mp4; codecs=mp4a.40.2";

  /**
   * Using <code>audio/wav</code> instead of the more popular, but unregistered,
   * <code>audio/wav</code>. Typical filename extension is <code>.wav</code>.
   */
  private static String MIME_TYPE_AUDIO_WAV = "audio/wav";

  /**
   * Using <code>audio/x-wav</code> instead of the more popular, but unregistered,
   * <code>audio/wav</code>. Typical filename extension is <code>.wav</code>.
   */
  private static String MIME_TYPE_AUDIO_VND_WAV = "audio/vnd.wave";

  /**
   * MIME Type <code>audio/vnd.wave; codecs=0</code> for WAVE audio in unknown format.
   * See RFC 2361 (WAVE and AVI Codec Registries).
   * Typical filename extension is <code>.wav</code>.
   */
  private static String MIME_TYPE_AUDIO_VND_WAVE_UNKNOWN = "audio/vnd.wave; codecs=0";

  /**
   * MIME Type <code>audio/x-wav; codecs=0</code> for WAVE audio in unknown format.
   * See RFC 2361 (WAVE and AVI Codec Registries).
   * Typical filename extension is <code>.wav</code>.
   */
  private static String MIME_TYPE_AUDIO_X_WAV_UNKNOWN = "audio/x-wav; codecs=0";

  /**
   * MIME Type <code>audio/wav; codecs=0</code> for WAVE audio in unknown format.
   * See RFC 2361 (WAVE and AVI Codec Registries).
   * Typical filename extension is <code>.wav</code>.
   */
  private static String MIME_TYPE_AUDIO_WAV_UNKNOWN = "audio/wav; codecs=0";

  /**
   * MIME Type <code>audio/vnd.wave; codecs=1</code> for WAVE audio in Microsoft PCM format.
   * See RFC 2361 (WAVE and AVI Codec Registries).
   * Typical filename extension is <code>.wav</code>.
   */
  private static String MIME_TYPE_AUDIO_VND_WAVE_PCM = "audio/vnd.wave; codecs=1";

  /**
   * MIME Type <code>audio/x-wav; codecs=1</code> for WAVE audio in Microsoft PCM format.
   * See RFC 2361 (WAVE and AVI Codec Registries).
   * Typical filename extension is <code>.wav</code>.
   */
  private static String MIME_TYPE_AUDIO_X_WAV_PCM = "audio/x-wav; codecs=1";
  /**
   * MIME Type <code>audio/wav; codecs=1</code> for WAVE audio in Microsoft PCM format.
   * See RFC 2361 (WAVE and AVI Codec Registries).
   * Typical filename extension is <code>.wav</code>.
   */
  private static String MIME_TYPE_AUDIO_WAV_PCM = "audio/wav; codecs=1";

  /**
   * MIME Type <code>audio/vnd.wave; codecs=2</code> for WAVE audio in Microsoft ADPCM format.
   * See RFC 2361 (WAVE and AVI Codec Registries).
   * Typical filename extension is <code>.wav</code>.
   */
  private static String MIME_TYPE_AUDIO_VND_WAVE_ADPCM = "audio/vnd-wave; codecs=2";

  /**
   * MIME Type <code>audio/x-wav; codecs=2</code> for WAVE audio in Microsoft ADPCM format.
   * See RFC 2361 (WAVE and AVI Codec Registries).
   * Typical filename extension is <code>.wav</code>.
   */
  private static String MIME_TYPE_AUDIO_X_WAV_ADPCM = "audio/x-wav; codecs=2";

  /**
   * MIME Type <code>audio/wav; codecs=2</code> for WAVE audio in Microsoft ADPCM format.
   * See RFC 2361 (WAVE and AVI Codec Registries).
   * Typical filename extension is <code>.wav</code>.
   */
  private static String MIME_TYPE_AUDIO_WAV_ADPCM = "audio/wav; codecs=2";

  public static String[] MIME_TYPES = {
      Sound.MIME_TYPE_AUDIO_BASIC, Sound.MIME_TYPE_AUDIO_MPEG, MIME_TYPE_AUDIO_MP4,
      MIME_TYPE_AUDIO_MP4_MP4A_40_2, Sound.MIME_TYPE_AUDIO_X_AIFF, Sound.MIME_TYPE_AUDIO_X_MIDI,
      MIME_TYPE_AUDIO_WAV, Sound.MIME_TYPE_AUDIO_X_WAV, MIME_TYPE_AUDIO_VND_WAV,
      MIME_TYPE_AUDIO_WAV_UNKNOWN, MIME_TYPE_AUDIO_X_WAV_UNKNOWN, MIME_TYPE_AUDIO_VND_WAVE_UNKNOWN,
      MIME_TYPE_AUDIO_WAV_PCM, MIME_TYPE_AUDIO_X_WAV_PCM, MIME_TYPE_AUDIO_VND_WAVE_PCM,
      MIME_TYPE_AUDIO_WAV_ADPCM, MIME_TYPE_AUDIO_X_WAV_ADPCM, MIME_TYPE_AUDIO_VND_WAVE_ADPCM,
      Sound.MIME_TYPE_AUDIO_OGG, Sound.MIME_TYPE_AUDIO_OGG_FLAC, Sound.MIME_TYPE_AUDIO_OGG_SPEEX,
      Sound.MIME_TYPE_AUDIO_OGG_VORBIS,};

  static {
    freeSounds = new ThirdPartySound[] {
        new FreesoundProjectSound(MIME_TYPE_AUDIO_VND_WAVE_PCM,
            "freesoundproject/35631__reinsamba__crystal_glass.wav", "crystal_glass",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=35631", "reinsamba",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=18799",
            "RIFF (little-endian) data, WAVE audio, Microsoft PCM, 16 bit, stereo 44100 Hz"),
        new FreesoundProjectSound(MIME_TYPE_AUDIO_VND_WAVE_PCM,
            "freesoundproject/38403__THE_bizniss__snap.wav", "snap",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=38403", "THE_bizniss",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=382028",
            "RIFF (little-endian) data, WAVE audio, Microsoft PCM, 16 bit, mono 44100 Hz"),
        new FreesoundProjectSound(MIME_TYPE_AUDIO_VND_WAVE_PCM,
            "freesoundproject/22740__FranciscoPadilla__37_Click_Finger.wav", "37 Click Finger",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=22740", "FranciscoPadilla",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=132693",
            "RIFF (little-endian) data, WAVE audio, Microsoft PCM, 16 bit, mono 44100 Hz"),
        new FreesoundProjectSound(MIME_TYPE_AUDIO_VND_WAVE_ADPCM,
            "freesoundproject/9874__vixuxx__crow.wav", "crow",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=9874", "vixuxx",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=28679",
            "RIFF (little-endian) data, WAVE audio, IMA ADPCM, mono 22050 Hz"),
        new FreesoundProjectSound(Sound.MIME_TYPE_AUDIO_X_AIFF,
            "freesoundproject/9874__vixuxx__crow.aiff", "crow",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=9874", "vixuxx",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=28679", "IFF data, AIFF audio"),
        new FreesoundProjectSound(Sound.MIME_TYPE_AUDIO_BASIC,
            "freesoundproject/9874__vixuxx__crow.au", "crow",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=9874", "vixuxx",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=28679",
            "Sun/NeXT audio data: 16-bit linear PCM, mono, 22050 Hz"),
        new FreesoundProjectSound(Sound.MIME_TYPE_AUDIO_MPEG,
            "freesoundproject/28917__junggle__btn107.mp3", "btn107",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=28917", "junggle",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=128404",
            "Audio file with ID3 version 23.0 tag, MP3 encoding"),
        new FreesoundProjectSound(Sound.MIME_TYPE_AUDIO_MPEG,
            "freesoundproject/36846__EcoDTR__LaserRocket.mp3", "LaserRocket",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=36846", "EcoDTR",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=181367",
            "Audio file with ID3 version 23.0 tag, MP3 encoding"),
        new FreesoundProjectSound(Sound.MIME_TYPE_AUDIO_MPEG,
            "freesoundproject/35643__sandyrb__USAT_BOMB.mp3", "USAT BOMB",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=35643", "sandyrb",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=14771",
            "Audio file with ID3 version 23.0 tag, MP3 encoding"),
        new FreesoundProjectSound(Sound.MIME_TYPE_AUDIO_MPEG,
            "freesoundproject/34961__grandpablaine2__grenade_reverse_reverb.mp3",
            "grenade_reverse_reverb", "http://freesound.iua.upf.edu/samplesViewSingle.php?id=34961",
            "grandpablaine2", "http://freesound.iua.upf.edu/usersViewSingle.php?id=147084", ""),
        new FreesoundProjectSound(Sound.MIME_TYPE_AUDIO_MPEG,
            "freesoundproject/33637__HerbertBoland__CinematicBoomNorm.mp3", "CinematicBoomNorm",
            "http://freesound.iua.upf.edu/samplesViewSingle.php?id=33637", "HerbertBoland",
            "http://freesound.iua.upf.edu/usersViewSingle.php?id=129090", ""),
        new FreesoundProjectSound(Sound.MIME_TYPE_AUDIO_OGG,
            "freesoundproject/91960__billengholm_yahoo.com__opensurdo.ogg", "opensurdo",
            "http://www.freesound.org/samplesViewSingle.php?id=91960", "billengholm@yahoo.com",
            "http://www.freesound.org/usersViewSingle.php?id=1513331", ""),
        new WikipediaSound(Sound.MIME_TYPE_AUDIO_OGG, "wikipedia/En-us-squid.ogg",
            "En-us-squid.ogg", "http://upload.wikimedia.org/wikipedia/commons/f/f1/En-us-squid.ogg",
            "http://en.wiktionary.org/wiki/squid"),
        new WikipediaSound(Sound.MIME_TYPE_AUDIO_X_MIDI, "wikipedia/Bass_sample2.mid",
            "Bass_sample2.mid",
            "http://upload.wikimedia.org/wikipedia/commons/b/b0/Bass_sample2.mid",
            "http://en.wikipedia.org/wiki/Musical_Instrument_Digital_Interface"),
        new WikipediaSound(Sound.MIME_TYPE_AUDIO_X_MIDI, "wikipedia/Drum_sample.mid",
            "Drum_sample.mid", "http://upload.wikimedia.org/wikipedia/commons/6/61/Drum_sample.mid",
            "http://en.wikipedia.org/wiki/Musical_Instrument_Digital_Interface"),};

    for (ThirdPartySound element : freeSounds) {
      String mimeType = element.getMimeType();
      ArrayList<ThirdPartySound> freesoundList = mimeTypeSoundMap.get(mimeType);
      if (freesoundList == null) {
        freesoundList = new ArrayList<ThirdPartySound>();
        mimeTypeSoundMap.put(mimeType, freesoundList);
      }
      freesoundList.add(element);
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
        System.err.print(text);
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
    StyleInjector.injectAtStart(DemoClientBundle.INSTANCE.css().getText());

    // text area to log sound events as they are triggered
    final HTML eventTextArea = new HTML();
    RootPanel.get(DemoClientBundle.INSTANCE.css().demoEventTextArea()).add(eventTextArea);

    DemoSoundHandler demoSoundHandler = new DemoSoundHandler(eventTextArea);

    RootPanel mainPanel = RootPanel.get(DemoClientBundle.INSTANCE.css().demoMainPanel());
    DOM.setInnerHTML(mainPanel.getElement(), "");

    DisclosurePanel soundSupportMatrix = new DisclosurePanel("Sound Support Matrix");
    soundSupportMatrix.setContent(new SupportedMimeTypeSummary());
    mainPanel.add(soundSupportMatrix);

    for (String mimeType : MIME_TYPES) {
      ArrayList<ThirdPartySound> soundList = mimeTypeSoundMap.get(mimeType);
      if (soundList != null) {
        DeferredContentDisclosurePanel disclosurePanel = new DeferredContentDisclosurePanel(
            mimeType, new MimeTypeDemo(mimeType, soundList, demoSoundHandler));
        mainPanel.add(disclosurePanel);
      }
    }
  }

}

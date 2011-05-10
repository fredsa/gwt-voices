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
package com.allen_sauer.gwt.voices.client;

import static com.allen_sauer.gwt.voices.client.Sound.LoadState.LOAD_STATE_NOT_SUPPORTED;
import static com.allen_sauer.gwt.voices.client.Sound.LoadState.LOAD_STATE_SUPPORTED_MAYBE_READY;
import static com.allen_sauer.gwt.voices.client.Sound.LoadState.LOAD_STATE_SUPPORT_NOT_KNOWN;

import com.google.gwt.dom.client.AudioElement;
import com.google.gwt.media.client.Audio;

import com.allen_sauer.gwt.voices.client.SoundController.MimeTypeSupport;

/**
 * Sound object representing sounds which can be played back via HTML5 audio.
 */
public class Html5Sound extends AbstractSound {
  // CHECKSTYLE_JAVADOC_OFF

  /**
   * @param mimeType the requested MIME type and optional codec according to RFC 4281
   * @return the level of support for the provided MIME type
   */
  public static MimeTypeSupport getMimeTypeSupport(String mimeType) {
    if (!Audio.isSupported()) {
      return MimeTypeSupport.MIME_TYPE_NOT_SUPPORTED;
    }
    String support = Audio.createIfSupported().getAudioElement().canPlayType(mimeType);
    assert support != null;
    if (AudioElement.CAN_PLAY_PROBABLY.equals(support)) {
      return MimeTypeSupport.MIME_TYPE_SUPPORT_READY;
    }
    if (AudioElement.CAN_PLAY_MAYBE.equals(support)) {
      return MimeTypeSupport.MIME_TYPE_SUPPORT_READY;
    }
    return MimeTypeSupport.MIME_TYPE_SUPPORT_UNKNOWN;
  }

  private AudioElement e;
  private final String url;

  /**
   * @param mimeType the requested MIME type and optional codec according to RFC 4281
   * @param url the URL of the audio resource
   * @param streaming whether or not to stream the content, although currently ignored
   */
  public Html5Sound(String mimeType, String url, boolean streaming) {
    super(mimeType, url, streaming);
    this.url = url;

    assert Audio.isSupported();
    e = Audio.createIfSupported().getAudioElement();
    assert e != null;

    e.setSrc(url);

    MimeTypeSupport mimeTypeSupport = getMimeTypeSupport(mimeType);
    switch (mimeTypeSupport) {
      case MIME_TYPE_SUPPORT_READY:
        setLoadState(LOAD_STATE_SUPPORTED_MAYBE_READY);
        break;
      case MIME_TYPE_NOT_SUPPORTED:
        setLoadState(LOAD_STATE_NOT_SUPPORTED);
        break;
      case MIME_TYPE_SUPPORT_UNKNOWN:
        setLoadState(LOAD_STATE_SUPPORT_NOT_KNOWN);
        throw new IllegalArgumentException("unexpected MIME type support " + mimeTypeSupport);
      default:
        throw new IllegalArgumentException("unknown MIME type support " + mimeTypeSupport);
    }
  }

  public int getBalance() {
    // not implemented
    return SoundController.DEFAULT_BALANCE;
  }

  public boolean getLooping() {
    return e.isLoop();
  }

  @Override
  public String getSoundType() {
    return "HTML5";
  }

  public int getVolume() {
    return (int) (e.getVolume() * 100d);
  }

  public boolean play() {
    e.pause();
    try {
      // IE9 has been seen to throw an exception here
      e.setCurrentTime(0);
    } catch (Exception ignore) {
    }
    if (e.getCurrentTime() != 0) {
      /*
       * Workaround Chrome's inability to play the same audio twice:
       * http://code.google.com/p/chromium/issues/detail?id=71323
       * http://code.google.com/p/chromium/issues/detail?id=75725
       */
      e = Audio.createIfSupported().getAudioElement();
      e.setSrc(url);
    }
    e.play();
    // best guess is that the sound played, so return true
    return true;
  }

  public void setBalance(int balance) {
    // not implemented
  }

  public void setLooping(boolean looping) {
    e.setLoop(looping);
  }

  public void setVolume(int volume) {
    assert volume >= 0;
    assert volume <= 100;
    e.setVolume(volume / 100d);
  }

  public void stop() {
    e.pause();
  }

}

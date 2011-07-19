/*
 * Copyright 2010 Fred Sauer
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
package com.allen_sauer.gwt.voices.client;

import static com.allen_sauer.gwt.voices.client.Sound.LoadState.LOAD_STATE_NOT_SUPPORTED;
import static com.allen_sauer.gwt.voices.client.Sound.LoadState.LOAD_STATE_SUPPORTED_MAYBE_READY;
import static com.allen_sauer.gwt.voices.client.Sound.LoadState.LOAD_STATE_SUPPORTED_NOT_READY;
import static com.allen_sauer.gwt.voices.client.Sound.LoadState.LOAD_STATE_SUPPORT_NOT_KNOWN;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

import com.allen_sauer.gwt.voices.client.SoundController.MimeTypeSupport;

/**
 * Sound object using the Web Audio API.
 */
public class WebAudioSound extends AbstractSound {

  private String mimeType;
  private Element voice;
  private static Element audioContext;
  private JavaScriptObject buffer;
  private int volume;
  private boolean looping;

  public static MimeTypeSupport getMimeTypeSupport(String mimeType) {
    if (audioContext == null) {
      return MimeTypeSupport.MIME_TYPE_NOT_SUPPORTED;
    }
    // TODO: Update once http://code.google.com/p/chromium/issues/detail?id=88122
    // and http://code.google.com/p/chromium/issues/detail?id=88131 are addressed
    if (mimeType.equals(MIME_TYPE_AUDIO_OGG_VORBIS) || mimeType.equals(MIME_TYPE_AUDIO_WAV_PCM)) {
      return MimeTypeSupport.MIME_TYPE_SUPPORT_READY;
    }
    return MimeTypeSupport.MIME_TYPE_NOT_SUPPORTED;
  }

  public WebAudioSound(String mimeType, String url, boolean streaming) {
    super(mimeType, url, streaming);

    this.mimeType = mimeType;

    createVoice(url);

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
        break;
      case MIME_TYPE_SUPPORT_NOT_READY:
        setLoadState(LOAD_STATE_SUPPORTED_NOT_READY);
        break;
      default:
        throw new IllegalArgumentException("unknown MIME type support " + mimeTypeSupport);
    }
  }

  static {
    audioContext = createAudioContext();
  }

  private native static Element createAudioContext() /*-{
    try {
      return new AudioContext();
    } catch (ignore) {
    }

    try {
      return new webkitAudioContext();
    } catch (ignore) {
    }

    return null;
  }-*/;

  private native void createVoice(String url) /*-{
    var context = @com.allen_sauer.gwt.voices.client.WebAudioSound::audioContext;

    var request = new $wnd.XMLHttpRequest();
    request.open("GET", url, true);
    request.responseType = "arraybuffer";

    var self = this;
    request.onload = function() {
      self.@com.allen_sauer.gwt.voices.client.WebAudioSound::buffer = context
          .createBuffer(request.response, false);
    }

    request.send();
  }-*/;

  @Override
  public int getBalance() {
    // TODO(fredsa): Auto-generated method stub
    return 0;
  }

  @Override
  public boolean getLooping() {
    return looping;
  }

  @Override
  public int getVolume() {
    return volume;
  }

  @Override
  public native boolean play() /*-{
    this.@com.allen_sauer.gwt.voices.client.WebAudioSound::stop()();
    var context = @com.allen_sauer.gwt.voices.client.WebAudioSound::audioContext;

    var voice = context.createBufferSource();
    this.@com.allen_sauer.gwt.voices.client.WebAudioSound::voice = voice;

    if (this.@com.allen_sauer.gwt.voices.client.WebAudioSound::looping) {
      // TODO: Need to clarify 'loop' vs. 'looping'
      // See http://code.google.com/p/chromium/issues/detail?id=88119
      if ('looping' in voice) {
        voice.looping = true;
      }
      if ('loop' in voice) {
        voice.loop = true;
      }
    }

    var node = voice;

    var volume = this.@com.allen_sauer.gwt.voices.client.WebAudioSound::volume;
    if (volume != @com.allen_sauer.gwt.voices.client.SoundController::DEFAULT_VOLUME) {
      var gainNode = context.createGainNode();
      gainNode.gain.value = volume
          / @com.allen_sauer.gwt.voices.client.SoundController::DEFAULT_VOLUME;
      node.connect(gainNode);
      node = gainNode;
    }

    node.connect(context.destination);

    voice.buffer = this.@com.allen_sauer.gwt.voices.client.WebAudioSound::buffer;

    voice.noteOn(context.currentTime);
    return true;
  }-*/;

  @Override
  public void setBalance(int balance) {
    // TODO(fredsa): Auto-generated method stub
  }

  @Override
  public void setLooping(boolean looping) {
    this.looping = looping;
  }

  @Override
  public void setVolume(int volume) {
    this.volume = volume;
  }

  @Override
  public native void stop() /*-{
    var context = @com.allen_sauer.gwt.voices.client.WebAudioSound::audioContext;
    var voice = this.@com.allen_sauer.gwt.voices.client.WebAudioSound::voice;
    if (voice == null) {
      return;
    }
    voice.noteOff(context.currentTime);
    this.@com.allen_sauer.gwt.voices.client.WebAudioSound::voice = null;
  }-*/;

  @Override
  public String getSoundType() {
    return "Web Audio";
  }

}

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

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;

import com.allen_sauer.gwt.voices.client.SoundController.MimeTypeSupport;
import com.allen_sauer.gwt.voices.client.ui.impl.Html5SoundImpl;

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
    return Html5SoundImpl.getMimeTypeSupport(mimeType);
  }

  private Element e;

  /**
   * @param mimeType the requested MIME type and optional codec according to RFC 4281
   * @param url the URL of the audio resource
   * @param streaming whether or not to stream the content, although currently ignored
   */
  public Html5Sound(String mimeType, String url, boolean streaming) {
    super(mimeType, url, streaming);
    e = Html5SoundImpl.createElement(url);

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

  @Override
  public String getSoundType() {
    return "HTML5";
  }

  public int getVolume() {
    return Html5SoundImpl.getVolume(e);
  }


  public void play() {
    Html5SoundImpl.pause(e);
    e.removeFromParent();
    try {
      Html5SoundImpl.setCurrentTime(e, 0);
    } catch (Exception ignore) {
    }
    RootPanel.getBodyElement().appendChild(e);
    Html5SoundImpl.play(e);
  }

  public void setBalance(int balance) {
    assert balance >= -100;
    assert balance <= 100;
    Html5SoundImpl.setBalance(e, balance);
  }

  public void setVolume(int volume) {
    assert volume >= 0;
    assert volume <= 100;
    Html5SoundImpl.setVolume(e, volume);
  }

  public void stop() {
    Html5SoundImpl.pause(e);
  }

}

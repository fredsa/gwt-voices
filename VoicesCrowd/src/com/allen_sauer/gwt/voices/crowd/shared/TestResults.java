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
package com.allen_sauer.gwt.voices.crowd.shared;

import java.io.Serializable;

public class TestResults implements Serializable {
  private static String MIME_TYPE_AUDIO_MP4 = "audio/mp4";
  private static String MIME_TYPE_AUDIO_MP4_MP4A_40_2 = "audio/mp4; codecs=mp4a.40.2";
  private static String MIME_TYPE_AUDIO_WAV = "audio/wav";
  private static String MIME_TYPE_AUDIO_VND_WAV = "audio/vnd.wave";
  private static String MIME_TYPE_AUDIO_VND_WAVE_UNKNOWN = "audio/vnd.wave; codecs=0";
  private static String MIME_TYPE_AUDIO_X_WAV_UNKNOWN = "audio/x-wav; codecs=0";
  private static String MIME_TYPE_AUDIO_WAV_UNKNOWN = "audio/wav; codecs=0";
  private static String MIME_TYPE_AUDIO_VND_WAVE_PCM = "audio/vnd.wave; codecs=1";
  private static String MIME_TYPE_AUDIO_X_WAV_PCM = "audio/x-wav; codecs=1";
  private static String MIME_TYPE_AUDIO_WAV_PCM = "audio/wav; codecs=1";
  private static String MIME_TYPE_AUDIO_VND_WAVE_ADPCM = "audio/vnd-wave; codecs=2";
  private static String MIME_TYPE_AUDIO_X_WAV_ADPCM = "audio/x-wav; codecs=2";
  private static String MIME_TYPE_AUDIO_WAV_ADPCM = "audio/wav; codecs=2";
  private static String MIME_TYPE_AUDIO_BASIC = "audio/basic";
  private static String MIME_TYPE_AUDIO_MPEG = "audio/mpeg";
  private static String MIME_TYPE_AUDIO_X_AIFF = "audio/x-aiff";
  private static String MIME_TYPE_AUDIO_X_MIDI = "audio/x-midi";
  private static String MIME_TYPE_AUDIO_X_WAV = "audio/x-wav";
  private static String MIME_TYPE_AUDIO_OGG = "audio/ogg";
  private static String MIME_TYPE_AUDIO_OGG_VORBIS = "audio/ogg; codecs=vorbis";
  private static String MIME_TYPE_AUDIO_OGG_FLAC = "audio/ogg; codecs=flac";
  private static String MIME_TYPE_AUDIO_OGG_SPEEX = "audio/ogg; codecs=speex";

  public static MimeType[] MIME_TYPES = {new MimeType(MIME_TYPE_AUDIO_BASIC),
      new MimeType(MIME_TYPE_AUDIO_MPEG),
      new MimeType(MIME_TYPE_AUDIO_MP4),
      new MimeType(MIME_TYPE_AUDIO_MP4_MP4A_40_2),
      new MimeType(MIME_TYPE_AUDIO_X_AIFF),
      new MimeType(MIME_TYPE_AUDIO_X_MIDI),
      new MimeType(MIME_TYPE_AUDIO_WAV),
      new MimeType(MIME_TYPE_AUDIO_X_WAV),
      new MimeType(MIME_TYPE_AUDIO_VND_WAV),
      new MimeType(MIME_TYPE_AUDIO_WAV_UNKNOWN),
      new MimeType(MIME_TYPE_AUDIO_X_WAV_UNKNOWN),
      new MimeType(MIME_TYPE_AUDIO_VND_WAVE_UNKNOWN),
      new MimeType(MIME_TYPE_AUDIO_WAV_PCM),
      new MimeType(MIME_TYPE_AUDIO_X_WAV_PCM),
      new MimeType(MIME_TYPE_AUDIO_VND_WAVE_PCM),
      new MimeType(MIME_TYPE_AUDIO_WAV_ADPCM),
      new MimeType(MIME_TYPE_AUDIO_X_WAV_ADPCM),
      new MimeType(MIME_TYPE_AUDIO_VND_WAVE_ADPCM),
      new MimeType(MIME_TYPE_AUDIO_OGG),
      new MimeType(MIME_TYPE_AUDIO_OGG_FLAC),
      new MimeType(MIME_TYPE_AUDIO_OGG_SPEEX),
      new MimeType(MIME_TYPE_AUDIO_OGG_VORBIS),};

  private String[] results;

  public TestResults(String testResultsString) {
    this.results = testResultsString.split("\\|");
  }

  public TestResults(String[] results) {
    this.results = results;
  }

  public TestResults() {
    this.results = (new String[] {});
  }

  @Override
  public String toString() {
    StringBuffer tr = new StringBuffer();
    for (int i = 0; i < MIME_TYPES.length; i++) {
      if (i > 0) {
        tr.append("|");
      }
      tr.append(getResults()[i]);
    }
    return tr.toString();
  }

  public String[] getResults() {
    return results;
  }

}

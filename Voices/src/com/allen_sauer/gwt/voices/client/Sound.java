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

import com.allen_sauer.gwt.voices.client.handler.FiresSoundEvents;

/**
 * Common interface for all concrete supported sound implementations.
 */
public interface Sound extends FiresSoundEvents {
  /**
   * Enumeration of sound load states.
   */
  enum LoadState {
    /**
     * Play back of this sound's MIME type is known NOT to be supported. Calling
     * {@link Sound#play()} may in rare occasions still work.
     */
    LOAD_STATE_NOT_SUPPORTED,

    /**
     * The sound load state is unknown, and cannot be determined. Hope for the best. Calling
     * {@link Sound#play()} may or may not work.
     */
    LOAD_STATE_SUPPORT_NOT_KNOWN,

    /**
     * Play back of this sound's MIME type is supported and this sound object is ready for immediate
     * play back. This means that the sound file has either been downloaded by the client (when not
     * streaming), or is ready for streaming.
     */
    LOAD_STATE_SUPPORTED_AND_READY,

    /**
     * Play back of this sound's MIME type is known to be supported, however the client browser is
     * unable to provide load notification events. It cannot be programmatically determined when the
     * client has downloaded the sound. When possible, an attempt will be made to begin downloading
     * the sound file in the background. The load state will NOT change to
     * {@link LoadState#LOAD_STATE_SUPPORTED_AND_READY}, or indeed to any other state.
     */
    LOAD_STATE_SUPPORTED_MAYBE_READY,

    /**
     * Play back of this sound's MIME type is known to be supported, however the sound file has not
     * yet been loaded. The load state is at some point expected to change to
     * {@link LoadState#LOAD_STATE_SUPPORTED_AND_READY}.
     */
    LOAD_STATE_SUPPORTED_NOT_READY,

    /**
     * All new sounds start in this load state, after which they will transition at least once to a
     * new load state.
     */
    LOAD_STATE_UNINITIALIZED,
  }

  /**
   * IANA assigned media type <code>audio/basic</code>, from RFC 2045/2046. Typical filename
   * extensions include <code>.au</code> and <code>.snd</code>.
   */
  String MIME_TYPE_AUDIO_BASIC = "audio/basic";

  /**
   * IANA assigned media type <code>audio/mp4</code>, from RFC 4337. Typical filename extension
   * <code>.mp4</code>.
   *
   * @deprecated Use a more specific MIME Type which includes an appropriate <code>codecs</code> parameter
   */
  @Deprecated
  String MIME_TYPE_AUDIO_MP4 = "audio/mp4";

  /**
   * MIME Type <code>audio/mp4; codecs=mp4a.40.2</code>, for AAC low complexity MP4 as described in
   * RFC 4281. Typical filename extension <code>.mp4</code>.
   */
  String MIME_TYPE_AUDIO_MP4_MP4A_40_2 = "audio/mp4; codecs=mp4a.40.2";

  /**
   * IANA assigned media type <code>audio/mpeg</code>, from RFC 3003. Typical filename extensions
   * include <code>.mp1</code>, <code>.mp2</code> and <code>.mp3</code>.
   *
   * @deprecated Use a more specific MIME Type which includes an appropriate <code>codecs</code> parameter
   */
  @Deprecated
  String MIME_TYPE_AUDIO_MPEG = "audio/mpeg";

  /**
   * Non-standard MIME type <code>audio/mpeg; codecs=MP3</code> for MP3 audio.
   * Typical filename extension <code>.mp3</code>.
   */
  String MIME_TYPE_AUDIO_MPEG_MP3 = "audio/mpeg; codecs=MP3";

  /**
   * IANA assigned media type <code>audio/ogg</code>, from RFC 5334. Typical filename
   * extensions include <code>.ogg</code>.
   *
   * @deprecated Use a more specific MIME Type which includes an appropriate <code>codecs</code> parameter
   */
  @Deprecated
  String MIME_TYPE_AUDIO_OGG = "audio/ogg";

  /**
   * IANA assigned media type <code>audio/ogg; codecs=flac</code>, from RFC 5334. Typical filename
   * extensions include <code>.ogg</code>.
   */
  String MIME_TYPE_AUDIO_OGG_FLAC = "audio/ogg; codecs=flac";

  /**
   * IANA assigned media type <code>audio/ogg; codecs=speex</code>, from RFC 5334. Typical filename
   * extensions include <code>.ogg</code>.
   */
  String MIME_TYPE_AUDIO_OGG_SPEEX = "audio/ogg; codecs=speex";

  /**
   * IANA assigned media type <code>audio/ogg; codecs=vorbis</code>, from RFC 5334. Typical filename
   * extensions include <code>.ogg</code>.
   */
  String MIME_TYPE_AUDIO_OGG_VORBIS = "audio/ogg; codecs=vorbis";

  /**
   * MIME Type <code>audio/wav; codecs=2</code> for WAVE audio in Microsoft ADPCM format.
   * See RFC 2361 (WAVE and AVI Codec Registries).
   * Typical filename extension is <code>.wav</code>.
   */
  String MIME_TYPE_AUDIO_WAV_ADPCM = "audio/wav; codecs=2";

  /**
   * MIME Type <code>audio/wav; codecs=1</code> for WAVE audio in Microsoft PCM format.
   * See RFC 2361 (WAVE and AVI Codec Registries).
   * Typical filename extension is <code>.wav</code>.
   */
  String MIME_TYPE_AUDIO_WAV_PCM = "audio/wav; codecs=1";

  /**
   * MIME Type <code>audio/wav; codecs=0</code> for WAVE audio in unknown format.
   * See RFC 2361 (WAVE and AVI Codec Registries).
   * Typical filename extension is <code>.wav</code>.
   */
  String MIME_TYPE_AUDIO_WAV_UNKNOWN = "audio/wav; codecs=0";

  /**
   * Using <code>audio/x-aiff</code> instead of the more popular, but unregistered,
   * <code>audio/aiff</code>. Typical filename extension is <code>.aif</code>.
   */
  String MIME_TYPE_AUDIO_X_AIFF = "audio/x-aiff";

  /**
   * Using <code>audio/x-midi</code> instead of the more popular, but unregistered,
   * <code>audio/midi</code>. Typical filename extensions include <code>.mid</code> and
   * <code>.midi</code>.
   */
  String MIME_TYPE_AUDIO_X_MIDI = "audio/x-midi";

  /**
   * Using <code>audio/x-wav</code> instead of the more popular, but
   * unregistered, <code>audio/wav</code>. Typical filename extension is
   * <code>.wav</code>.
   *
   * @deprecated Prefer <code>audio/wav</code> over <code>audio/x-wav</code> due
   *             to better MAC Safari support. Also use a more specific MIME
   *             Type which includes an appropriate <code>codecs</code>
   *             parameter
   */
  @Deprecated
  String MIME_TYPE_AUDIO_X_WAV = "audio/x-wav";

  /**
   * Determine the current {@link LoadState} of this sound.
   *
   * @return current {@link LoadState}
   */
  LoadState getLoadState();

  /**
   * Determine whether this sound is to be played in a loop.
   *
   * @return true if this sounds is to be played in a loop
   */
  boolean getLooping();

  /**
   * Determine this sound's MIME type.
   *
   * @return this sound's MIME type
   */
  String getMimeType();

  /**
   * Get a brief, human readable, description of the sound type, which is an indication of the
   * mechanism used for play back. The returned value is subject to change and is not intended to be
   * machine parseable.
   *
   * @return brief text describing type of sound
   */
  String getSoundType();

  /**
   * Determine the URL for this sound.
   *
   * @return this sound's URL
   */
  String getUrl();

  /**
   * Determine the current volume (range <code>0..100</code>).
   *
   * @return this sound's current play back volume (range <code>0..100</code>)
   */
  int getVolume();

  /**
   * Play (or restart) this sound.
   */
  void play();

  /**
   * Set the left/right speaker balance (range <code>-100..100</code>).
   *
   * @param balance new balance (range <code>-100..100</code>)
   */
  void setBalance(int balance);

  /**
   * Set whether this sound should be played in a loop.
   *
   * @param loop true if the sound is to be looped
   */
  void setLooping(boolean looping);

  /**
   * Set this sound's volume (range <code>0..100</code>).
   *
   * @param volume new volume (range <code>0..100</code>)
   */
  void setVolume(int volume);

  /**
   * Stop play back of this sound.
   */
  void stop();
}
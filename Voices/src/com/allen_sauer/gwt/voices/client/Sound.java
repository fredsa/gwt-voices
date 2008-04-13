/*
 * Copyright 2008 Fred Sauer
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
     * The sound load state is unknown, and cannot be determined. Hope for the best.
     * Calling {@link Sound#play()} may or may not work.
     */
    LOAD_STATE_NOT_KNOWN,

    /**
     * Play back of this sound's MIME type is known NOT to be supported.
     * Calling {@link Sound#play()} may in rare occasions still work.
     */
    LOAD_STATE_NOT_SUPPORTED,

    /**
     * Play back of this sound's MIME type is supported and this sound object
     * is ready for immediate play back. This usually means that the sound
     * file has been downloaded by the client.
     */
    LOAD_STATE_SUPPORTED_AND_READY,

    /**
     * Play back of this sound's MIME type is known to be supported, however
     * the client browser is unable to provide load notification events. It
     * cannot be programmatically determined when the client has downloaded
     * the sound. When possible, an attempt will be made to begin downloading
     * the sound file in the background. The load state will NOT change to
     * {@link LoadState#LOAD_STATE_SUPPORTED_AND_READY}, or indeed to any other state.
     */
    LOAD_STATE_SUPPORTED_MAYBE_READY,

    /**
     * Play back of this sound's MIME type is known to be supported,
     * however the sound file has not yet been loaded. The load state is at some
     * point expected to change to {@link LoadState#LOAD_STATE_SUPPORTED_AND_READY}.
     */
    LOAD_STATE_SUPPORTED_NOT_READY,

    /**
     * All new sounds start in this load state, after which they will transition
     * at least once to a new load state.
     */
    LOAD_STATE_UNINITIALIZED,
  };

  /**
   * @deprecated Use {@link LoadState#LOAD_STATE_SUPPORTED_AND_READY} enum value instead.
   */
  @Deprecated
  LoadState LOAD_STATE_LOADED = LoadState.LOAD_STATE_SUPPORTED_AND_READY;

  /**
   * @deprecated Use {@link LoadState#LOAD_STATE_SUPPORTED_MAYBE_READY} enum value instead.
   */
  @Deprecated
  LoadState LOAD_STATE_SUPPORTED = LoadState.LOAD_STATE_SUPPORTED_MAYBE_READY;

  /**
   * @deprecated Use {@link LoadState#LOAD_STATE_SUPPORTED_NOT_READY} enum value instead.
   */
  @Deprecated
  LoadState LOAD_STATE_SUPPORTED_NOT_LOADED = LoadState.LOAD_STATE_SUPPORTED_NOT_READY;

  /**
   * @deprecated Use {@link LoadState#LOAD_STATE_UNINITIALIZED} enum value instead.
   */
  @Deprecated
  LoadState LOAD_STATE_UNINITIALIZED = LoadState.LOAD_STATE_UNINITIALIZED;

  /**
   * @deprecated Use {@link LoadState#LOAD_STATE_NOT_KNOWN} enum value instead.
   */
  @Deprecated
  LoadState LOAD_STATE_UNKNOWN = LoadState.LOAD_STATE_NOT_KNOWN;

  /**
   * @deprecated Use {@link LoadState#LOAD_STATE_NOT_SUPPORTED} enum value instead.
   */
  @Deprecated
  LoadState LOAD_STATE_UNSUPPORTED = LoadState.LOAD_STATE_NOT_SUPPORTED;

  /**
   * IANA assigned media type <code>audio/basic</code> for RFC 2045/2046.
   * Typical filename extensions include <code>.au</code> and
   * <code>.snd</code>.
   */
  String MIME_TYPE_AUDIO_BASIC = "audio/basic";

  /**
   * IANA assigned media type <code>audio/mpeg</code> for RFC 3003. Typical
   * filename extensions include <code>.mp1</code>, <code>.mp2</code> and
   * <code>.mp3</code>.
   */
  String MIME_TYPE_AUDIO_MPEG = "audio/mpeg";

  /**
   * Using <code>audio/x-aiff</code> instead of the more popular, but
   * unregistered, <code>audio/aiff</code>. Typical filename extension is
   * <code>.aif</code>.
   */
  String MIME_TYPE_AUDIO_X_AIFF = "audio/x-aiff";

  /**
   * Using <code>audio/x-midi</code> instead of the more popular, but
   * unregistered, <code>audio/midi</code>. Typical filename extensions
   * include <code>.mid</code> and <code>.midi</code>.
   */
  String MIME_TYPE_AUDIO_X_MIDI = "audio/x-midi";

  /**
   * Using <code>audio/x-wav</code> instead of the more popular, but
   * unregistered, <code>audio/wav</code>. Typical filename extension is
   * <code>.wav</code>.
   */
  String MIME_TYPE_AUDIO_X_WAV = "audio/x-wav";

  LoadState getLoadState();

  String getMimeType();

  String getSoundType();

  String getUrl();

  int getVolume();

  void play();

  void setBalance(int balance);

  void setVolume(int volume);

  void stop();
}
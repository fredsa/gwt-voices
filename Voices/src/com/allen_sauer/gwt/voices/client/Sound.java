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
     * Sound is known to have been loaded. Usually this means that the
     * corresponding sound file has been downloaded and now exists in
     * the browser cache.
     */
    LOAD_STATE_LOADED,

    /**
     * Sound is known to be of a supported MIME type, however the current
     * browser runtime environment is unable to provide load notification
     * events.
      */
    LOAD_STATE_SUPPORTED,

    /**
     * Sound is known to be of a supported MIME type, however the sound
     * has not been loaded yet. The load state will later change to
     * {@link LoadState.LOAD_STATE_LOADED}.
     */
    LOAD_STATE_SUPPORTED_NOT_LOADED,

    /**
     * All sounds start in this load state, after which they will transition
     * to a new load state.
     */
    LOAD_STATE_UNINITIALIZED,

    /**
     * The sound load state is unknown (cannot be determined). Hope for the best.
     */
    LOAD_STATE_UNKNOWN,

    /**
     * The sound MIME type is known to NOT be supported.
     */
    LOAD_STATE_UNSUPPORTED,
  };

  /**
   * @deprecated Use {@LoadState.LOAD_STATE_LOADED} enum value instead.
   */
  @Deprecated
  public static final LoadState LOAD_STATE_LOADED = LoadState.LOAD_STATE_LOADED;

  /**
   * @deprecated Use {@LoadState.LOAD_STATE_SUPPORTED} enum value instead.
   */
  @Deprecated
  public static final LoadState LOAD_STATE_SUPPORTED = LoadState.LOAD_STATE_SUPPORTED;

  /**
   * @deprecated Use {@LoadState.LOAD_STATE_SUPPORTED_NOT_LOADED} enum value instead.
   */
  @Deprecated
  public static final LoadState LOAD_STATE_SUPPORTED_NOT_LOADED = LoadState.LOAD_STATE_SUPPORTED_NOT_LOADED;

  /**
   * @deprecated Use {@LoadState.LOAD_STATE_UNINITIALIZED} enum value instead.
   */
  @Deprecated
  public static final LoadState LOAD_STATE_UNINITIALIZED = LoadState.LOAD_STATE_UNINITIALIZED;

  /**
   * @deprecated Use {@LoadState.LOAD_STATE_UNKNOWN} enum value instead.
   */
  @Deprecated
  public static final LoadState LOAD_STATE_UNKNOWN = LoadState.LOAD_STATE_UNKNOWN;

  /**
   * @deprecated Use {@LoadState.LOAD_STATE_UNSUPPORTED} enum value instead.
   */
  @Deprecated
  public static final LoadState LOAD_STATE_UNSUPPORTED = LoadState.LOAD_STATE_UNSUPPORTED;

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
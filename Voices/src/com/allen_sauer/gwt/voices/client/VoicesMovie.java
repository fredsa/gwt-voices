/*
 * Copyright 2007 Fred Sauer
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

import com.google.gwt.user.client.Element;

import java.util.ArrayList;
import java.util.Iterator;

class VoicesMovie extends FlashMovie {
  private static final String __VOICES__ = "__VOICES__";
  private static final String VOICES_SWF = "Voices.swf";

  private boolean ready = false;
  private ArrayList unitializedSoundList = new ArrayList();

  public VoicesMovie() {
    super(__VOICES__, VOICES_SWF);
    installFlashCallbackHooks();
  }

  // JSNI Helper for GWT Issue 1651 (JSNI inherited method support in web mode)
  public Element getElement() {
    return super.getElement();
  }

  protected void onUnload() {
    super.onUnload();
    removeFlashCallbackHooks();
  }

  boolean playSound(int id) {
    if (ready) {
      callPlaySound(id);
    }
    return ready;
  }

  void registerSound(Sound sound) {
    if (ready) {
      doCreateSound(sound);
    } else {
      unitializedSoundList.add(sound);
    }
  }

  private native void callCreateSound(int id, String soundURL, boolean streaming)
  /*-{
    var elem = this.@com.allen_sauer.gwt.voices.client.VoicesMovie::getElement()();
    elem.createSound(id, soundURL, streaming);
  }-*/;

  private native void callPlaySound(int id)
  /*-{
    var elem = this.@com.allen_sauer.gwt.voices.client.VoicesMovie::getElement()();
    elem.playSound(id);
  }-*/;

  private void doCreateSound(Sound sound) {
    callCreateSound(sound.getId(), sound.getUrl(), sound.isStreaming());
  }

  private native void installFlashCallbackHooks()
  /*-{
    var self = this;
    $doc.VoicesMovie = {};
    
    try {
      $doc.VoicesMovie.ready = function() {
        var elem = self.@com.allen_sauer.gwt.voices.client.VoicesMovie::getElement()();
        self.@com.allen_sauer.gwt.voices.client.VoicesMovie::movieReady()();
        $doc.VoicesMovieReady = null;
      }
    } catch (e) {
      throw new Error("Exception defining $doc.VoicesMovieReady:\n" + e);
    }
    
    try {
      $doc.VoicesMovie.log = function(text) {
        @com.allen_sauer.gwt.log.client.Log::debug(Ljava/lang/String;)("FLASH: " + text);
      }
    } catch (e) {
      throw new Error("Exception defining $doc.VoicesMovieLog:\n" + e);
    }
    
    try {
      $doc.VoicesMovie.soundLoaded = function(id) {
        @com.allen_sauer.gwt.voices.client.Sound::soundLoaded(I)(id);
      }
    } catch (e) {
      throw new Error("Exception defining $doc.VoicesSoundLoaded:\n" + e);
    }
    
    try {
      $doc.VoicesMovie.soundCompleted = function(id) {
        @com.allen_sauer.gwt.voices.client.Sound::soundCompleted(I)(id);
      }
    } catch (e) {
      throw new Error("Exception defining $doc.VoicesSoundCompleted:\n" + e);
    }
  }-*/;

  private void movieReady() {
    ready = true;
    for (Iterator iterator = unitializedSoundList.iterator(); iterator.hasNext();) {
      Sound sound = (Sound) iterator.next();
      doCreateSound(sound);
      iterator.remove();
    }
  }

  private native void removeFlashCallbackHooks()
  /*-{
    $doc.VoicesMovie = null;
  }-*/;
}

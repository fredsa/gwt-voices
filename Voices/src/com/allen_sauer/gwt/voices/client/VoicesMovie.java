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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
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

  public void setBalance(int id, int balance) {
    if (ready) {
      callSetBalance(id, balance);
    }
  }

  public void setVolume(int id, int volume) {
    if (ready) {
      callSetVolume(id, volume);
    }
  }

  /**
   * Defer the actual work of a flash callback so that any exceptions can be
   * caught by the browser or the uncaught exception handler, rather than being
   * swallow by flash.
   */
  protected void deferFlashCallback(final JavaScriptObject func) {
    DeferredCommand.addCommand(new Command() {
      public native void callFunc(JavaScriptObject func, VoicesMovie thiz)
      /*-{
        func.apply(thiz);
      }-*/;

      public void execute() {
        callFunc(func, VoicesMovie.this);
      }
    });
  }

  protected void onUnload() {
    super.onUnload();
    removeFlashCallbackHooks();
  }

  void playSound(int id) {
    if (ready) {
      callPlaySound(id);
    }
  }

  void registerSound(FlashSound flashSound) {
    if (ready) {
      doCreateSound(flashSound);
    } else {
      unitializedSoundList.add(flashSound);
    }
  }

  void stopSound(int id) {
    if (ready) {
      callStopSound(id);
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

  private native void callSetBalance(int id, int balance)
  /*-{
    var elem = this.@com.allen_sauer.gwt.voices.client.VoicesMovie::getElement()();
    elem.setBalance(id, balance);
  }-*/;

  private native void callSetVolume(int id, int volume)
  /*-{
    var elem = this.@com.allen_sauer.gwt.voices.client.VoicesMovie::getElement()();
    elem.setVolume(id, volume);
  }-*/;

  private native void callStopSound(int id)
  /*-{
    var elem = this.@com.allen_sauer.gwt.voices.client.VoicesMovie::getElement()();
    elem.stopSound(id);
  }-*/;

  private void doCreateSound(FlashSound flashSound) {
    callCreateSound(flashSound.getId(), flashSound.getUrl(), false);
  }

  private native void installFlashCallbackHooks()
  /*-{
    var self = this;
    $doc.VoicesMovie = {};

    $doc.VoicesMovie.ready = function() {
      try {
        self.@com.allen_sauer.gwt.voices.client.VoicesMovie::deferFlashCallback(Lcom/google/gwt/core/client/JavaScriptObject;)(function() {
          var elem = this.@com.allen_sauer.gwt.voices.client.VoicesMovie::getElement()();
          this.@com.allen_sauer.gwt.voices.client.VoicesMovie::movieReady()();
          $doc.VoicesMovieReady = null;
        });
        return true;
      } catch(e) {
        // tell flash since throwing error would get lost
        return "Exception: " + e.message + " / " + e.description;
      }
    }
    
    $doc.VoicesMovie.soundLoaded = function(id) {
      try {
        self.@com.allen_sauer.gwt.voices.client.VoicesMovie::deferFlashCallback(Lcom/google/gwt/core/client/JavaScriptObject;)(function() {
          @com.allen_sauer.gwt.voices.client.FlashSound::soundLoaded(I)(id);
        });
        return true;
      } catch(e) {
        // tell flash since throwing error would get lost
        return "Exception: " + e.message + " / " + e.description;
      }
    }
    
    $doc.VoicesMovie.soundCompleted = function(id) {
      try {
        self.@com.allen_sauer.gwt.voices.client.VoicesMovie::deferFlashCallback(Lcom/google/gwt/core/client/JavaScriptObject;)(function() {
          @com.allen_sauer.gwt.voices.client.FlashSound::soundCompleted(I)(id);
        });
        return true;
      } catch(e) {
        // tell flash since throwing error would get lost
        return "Exception: " + e.message + " / " + e.description;
      }
    }

  //    $doc.VoicesMovie.log = function(text) {
  //      @com.allen_sauer.gwt.log.client.Log::debug(Ljava/lang/String;)("FLASH: " + text);
  //    }
    
  }-*/;

  private void movieReady() {
    ready = true;
    for (Iterator iterator = unitializedSoundList.iterator(); iterator.hasNext();) {
      FlashSound flashSound = (FlashSound) iterator.next();
      doCreateSound(flashSound);
      iterator.remove();
    }
  }

  private native void removeFlashCallbackHooks()
  /*-{
    $doc.VoicesMovie = null;
  }-*/;
}

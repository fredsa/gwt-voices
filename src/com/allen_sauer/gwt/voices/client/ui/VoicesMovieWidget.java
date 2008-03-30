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
package com.allen_sauer.gwt.voices.client.ui;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;

import com.allen_sauer.gwt.voices.client.FlashSound;
import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.util.StringUtil;

import java.util.ArrayList;
import java.util.Iterator;

public class VoicesMovieWidget extends FlashMovieWidget {
  private static final String[] FLASH_SUPPORTED_MIME_TYPES = {Sound.MIME_TYPE_AUDIO_MPEG,};
  private static final String GWT_VOICES_SWF = "gwt-voices.swf";

  private int flashSupport = SoundController.MIME_TYPE_SUPPORT_UNKNOWN;
  private ArrayList<FlashSound> unitializedSoundList = new ArrayList<FlashSound>();

  public VoicesMovieWidget(String id) {
    super(id, GWT_VOICES_SWF);
    installFlashCallbackHooks();

    // Flash Player version check for ExternalInterface support
    if (isExternalInterfaceSupported()) {
      flashSupport = SoundController.MIME_TYPE_SUPPORTED_NOT_LOADED;
    } else {
      flashSupport = SoundController.MIME_TYPE_UNSUPPORTED;
      DeferredCommand.addCommand(new Command() {
        public void execute() {
          movieUnsupported();
        }
      });
    }
  }

  // JSNI Helper for GWT Issue 1651 (JSNI inherited method support in web mode)
  @Override
  public Element getElement() {
    return super.getElement();
  }

  public int getMimeTypeSupport(String mimeType) {
    switch (flashSupport) {
      case SoundController.MIME_TYPE_SUPPORTED:
      case SoundController.MIME_TYPE_SUPPORTED_NOT_LOADED:
        return StringUtil.contains(FLASH_SUPPORTED_MIME_TYPES, mimeType)
            ? SoundController.MIME_TYPE_SUPPORTED : SoundController.MIME_TYPE_UNSUPPORTED;
      case SoundController.MIME_TYPE_SUPPORT_UNKNOWN:
      case SoundController.MIME_TYPE_UNSUPPORTED:
        return flashSupport;
      default:
        throw new RuntimeException("Unhandled flash support value " + flashSupport);
    }
  }

  public void playSound(int id) {
    if (flashSupport == SoundController.MIME_TYPE_SUPPORTED) {
      callPlaySound(id);
    }
  }

  public void registerSound(FlashSound flashSound) {
    if (flashSupport == SoundController.MIME_TYPE_SUPPORTED) {
      doCreateSound(flashSound);
    } else {
      unitializedSoundList.add(flashSound);
    }
  }

  public void setBalance(int id, int balance) {
    if (flashSupport == SoundController.MIME_TYPE_SUPPORTED) {
      callSetBalance(id, balance);
    }
  }

  public void setVolume(int id, int volume) {
    if (flashSupport == SoundController.MIME_TYPE_SUPPORTED) {
      callSetVolume(id, volume);
    }
  }

  public void stopSound(int id) {
    if (flashSupport == SoundController.MIME_TYPE_SUPPORTED) {
      callStopSound(id);
    }
  }

  /**
   * Defer the actual work of a flash callback so that any exceptions can be
   * caught by the browser or the uncaught exception handler, rather than being
   * swallow by flash.
   *
   * @param func the JavaScript function to call
   */
  protected void deferFlashCallback(final JavaScriptObject func) {
    DeferredCommand.addCommand(new Command() {
      public native void callFunc(JavaScriptObject func, VoicesMovieWidget thiz)
      /*-{
        func.apply(thiz);
      }-*/;

      public void execute() {
        callFunc(func, VoicesMovieWidget.this);
      }
    });
  }

  @Override
  protected void onUnload() {
    super.onUnload();
    removeFlashCallbackHooks();
  }

  private native void callCreateSound(int id, String soundURL, boolean streaming)
  /*-{
    var elem = this.@com.allen_sauer.gwt.voices.client.ui.VoicesMovieWidget::getElement()();
    elem.createSound(id, soundURL, streaming);
  }-*/;

  private native void callPlaySound(int id)
  /*-{
    var elem = this.@com.allen_sauer.gwt.voices.client.ui.VoicesMovieWidget::getElement()();
    elem.playSound(id);
  }-*/;

  private native void callSetBalance(int id, int balance)
  /*-{
    var elem = this.@com.allen_sauer.gwt.voices.client.ui.VoicesMovieWidget::getElement()();
    elem.setBalance(id, balance);
  }-*/;

  private native void callSetVolume(int id, int volume)
  /*-{
    var elem = this.@com.allen_sauer.gwt.voices.client.ui.VoicesMovieWidget::getElement()();
    elem.setVolume(id, volume);
  }-*/;

  private native void callStopSound(int id)
  /*-{
    var elem = this.@com.allen_sauer.gwt.voices.client.ui.VoicesMovieWidget::getElement()();
    elem.stopSound(id);
  }-*/;

  private void doCreateSound(FlashSound flashSound) {
    callCreateSound(flashSound.getSoundNumber(), flashSound.getUrl(), false);
  }

  private native void installFlashCallbackHooks()
  /*-{
    if ($doc.VoicesMovie === undefined) {
      $doc.VoicesMovie = {};
    }
    var id = this.@com.allen_sauer.gwt.voices.client.ui.FlashMovieWidget::getId()();
    var self = this;
    $doc.VoicesMovie[id] = {};

    $doc.VoicesMovie[id].ready = function() {
      try {
        self.@com.allen_sauer.gwt.voices.client.ui.VoicesMovieWidget::deferFlashCallback(Lcom/google/gwt/core/client/JavaScriptObject;)(function() {
          var elem = this.@com.allen_sauer.gwt.voices.client.ui.VoicesMovieWidget::getElement()();
          this.@com.allen_sauer.gwt.voices.client.ui.VoicesMovieWidget::movieReady()();
          $doc.VoicesMovieReady = null;
        });
        return true;
      } catch(e) {
        // tell flash since throwing error would get lost
        return "Exception: " + e.message + " / " + e.description;
      }
    }

    $doc.VoicesMovie[id].soundLoaded = function(id) {
      try {
        self.@com.allen_sauer.gwt.voices.client.ui.VoicesMovieWidget::deferFlashCallback(Lcom/google/gwt/core/client/JavaScriptObject;)(function() {
          @com.allen_sauer.gwt.voices.client.FlashSound::soundLoaded(I)(id);
        });
        return true;
      } catch(e) {
        // tell flash since throwing error would get lost
        return "Exception: " + e.message + " / " + e.description;
      }
    }

    $doc.VoicesMovie[id].soundCompleted = function(id) {
      try {
        self.@com.allen_sauer.gwt.voices.client.ui.VoicesMovieWidget::deferFlashCallback(Lcom/google/gwt/core/client/JavaScriptObject;)(function() {
          @com.allen_sauer.gwt.voices.client.FlashSound::soundCompleted(I)(id);
        });
        return true;
      } catch(e) {
        // tell flash since throwing error would get lost
        return "Exception: " + e.message + " / " + e.description;
      }
    }

  //    $doc.VoicesMovie[id].log = function(text) {
  //      @com.allen_sauer.gwt.log.client.Log::debug(Ljava/lang/String;)("FLASH[" + id + "]: " + text);
  //    }
  }-*/;

  private void movieReady() {
    flashSupport = SoundController.MIME_TYPE_SUPPORTED;
    for (Iterator<FlashSound> iterator = unitializedSoundList.iterator(); iterator.hasNext();) {
      FlashSound flashSound = iterator.next();
      doCreateSound(flashSound);
      iterator.remove();
    }
  }

  private void movieUnsupported() {
    for (FlashSound flashSound : unitializedSoundList) {
      flashSound.setLoadState(Sound.LOAD_STATE_UNSUPPORTED);
      // Flash plug-in may become available later; do not call iterator.remove()
    }
  }

  private native void removeFlashCallbackHooks()
  /*-{
    $doc.VoicesMovie = null;
  }-*/;
}

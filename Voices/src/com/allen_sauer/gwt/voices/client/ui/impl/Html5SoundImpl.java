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

package com.allen_sauer.gwt.voices.client.ui.impl;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

import com.allen_sauer.gwt.voices.client.SoundController.MimeTypeSupport;

/**
 * Implementation supporting HTML 5 sound as described in
 * http://www.whatwg.org/specs/web-apps/current-work/multipage/video.html#audio
 */
public class Html5SoundImpl {
  // CHECKSTYLE_JAVADOC_OFF

  @SuppressWarnings("unused")
  private static JavaScriptObject audio;

  public static native MimeTypeSupport getMimeTypeSupport(String mimeType) /*-{
    if (!@com.allen_sauer.gwt.voices.client.ui.impl.Html5SoundImpl::audio) {
      @com.allen_sauer.gwt.voices.client.ui.impl.Html5SoundImpl::audio = document.createElement('audio');
    }
    if (!@com.allen_sauer.gwt.voices.client.ui.impl.Html5SoundImpl::audio.canPlayType) {
      return @com.allen_sauer.gwt.voices.client.SoundController.MimeTypeSupport::MIME_TYPE_NOT_SUPPORTED;
    }
    var support = @com.allen_sauer.gwt.voices.client.ui.impl.Html5SoundImpl::audio.canPlayType(mimeType).replace(/no/, '');
    if (support == 'probably') {
      return @com.allen_sauer.gwt.voices.client.SoundController.MimeTypeSupport::MIME_TYPE_SUPPORT_READY;
    }
    if (support == 'maybe') {
      return @com.allen_sauer.gwt.voices.client.SoundController.MimeTypeSupport::MIME_TYPE_SUPPORT_READY;
    }
    if (support == '') {
      return @com.allen_sauer.gwt.voices.client.SoundController.MimeTypeSupport::MIME_TYPE_NOT_SUPPORTED;
    }
    return @com.allen_sauer.gwt.voices.client.SoundController.MimeTypeSupport::MIME_TYPE_SUPPORT_UNKNOWN;
  }-*/;

  public static Element createElement(String url) {
    Element e = DOM.createElement("audio");
    e.setAttribute("src", url);
    e.setAttribute("preload", "true");
    return e;
  }

  public static int getVolume(Element e) {
    // volume attribute in the range 0.0 (silent) to 1.0 (loudest)
    return DOM.getElementPropertyInt(e, "volume") * 100;
  }

  public static native void play(Element e) /*-{
    e.play();
  }-*/;

  public static native void setBalance(Element e, int balance) /*-{
    // not yet supported
  }-*/;

  public static void setVolume(Element e, int volume) {
    // volume attribute in the range 0.0 (silent) to 1.0 (loudest)
    float v = volume / 100F;
    assert v >= 0.0F;
    assert v <= 1.0F;
    DOM.setElementProperty(e, "volume", Float.toString(v));
  }

  public static void setCurrentTime(Element e, int currentTime) {
    assert currentTime >= 0;
    DOM.setElementProperty(e, "currentTime", Integer.toString(currentTime));
  }

  public static native void pause(Element e) /*-{
    e.pause();
  }-*/;
}

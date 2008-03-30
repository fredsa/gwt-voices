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
package com.allen_sauer.gwt.voices.client.ui.impl;

import com.google.gwt.user.client.Element;

/**
 * {@link com.allen_sauer.gwt.voices.client.ui.NativeSoundWidget} default
 * cross-browser implementation.
 */
public abstract class NativeSoundImpl {
  public abstract Element createElement(String url);

  public abstract int getMimeTypeSupport(String mimeType);

  public native void play(Element soundControllerElement, Element elem)
  /*-{
    soundControllerElement.appendChild(elem);
  }-*/;

  public void preload(Element soundControllerElement, String mimeType, String url) {
    Element elem = createElement(url);
    setVolume(elem, 0);
    play(soundControllerElement, elem);
  }

  public abstract void setBalance(Element elem, int balancePercentage);

  public abstract void setVolume(Element elem, int volume);

  public native void stop(Element elem)
  /*-{
    var parent = elem.parentNode;
    if (parent != null) {
      parent.removeChild(elem);
    }
  }-*/;
}

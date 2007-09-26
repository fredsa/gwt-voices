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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;

public class SoundController {
  private final Element element;

  public SoundController() {
    element = DOM.createDiv();
    DOM.appendChild(RootPanel.getBodyElement(), getElement());

    // place off screen with fixed dimensions and overflow:hidden
    DOM.setStyleAttribute(element, "position", "absolute");
    DOM.setStyleAttribute(element, "left", "-500px");
    DOM.setStyleAttribute(element, "top", "-500px");
    DOM.setStyleAttribute(element, "width", "50px");
    DOM.setStyleAttribute(element, "height", "50px");
    DOM.setStyleAttribute(element, "overflow", "hidden");
  }

  public Sound createSound(String url) {
    url = url.toLowerCase();
    if (url.endsWith(".mp3")) {
      return new FlashSound(getElement(), url);
    } else {
      return new NativeSound(getElement(), url);
    }
  }

  public Element getElement() {
    return element;
  }
}

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
package com.allen_sauer.gwt.voices.client.util.impl;

import com.google.gwt.user.client.Element;

/**
 * {@link com.allen_sauer.gwt.voices.client.util.DOMUtil} default cross-browser
 * implementation.
 */
public abstract class DOMUtilImpl {
  public abstract Element createFlashMovieMaybeSetMovieURL(String id,
      String movieURL);

  public abstract Element createSoundElement(String url);

  public void maybeSetFlashMovieURL(Element elem, String movieURL) {
  }

  public native void playSoundElement(Element soundControllerElement,
      Element elem)
  /*-{
    soundControllerElement.appendChild(elem);
  }-*/;

  public abstract void setSoundElementBalance(Element elem,
      int balancePercentage);

  public abstract void setSoundElementVolume(Element elem, int volume);

  public native void stopSoundElement(Element elem)
  /*-{
    var parent = elem.parentNode;
    if (parent != null) {
      parent.removeChild(elem);
    }
  }-*/;
}

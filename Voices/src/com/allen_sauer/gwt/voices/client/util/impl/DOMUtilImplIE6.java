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
 * {@link com.allen_sauer.gwt.voices.client.util.DOMUtil} implementation for IE.
 */
public class DOMUtilImplIE6 extends DOMUtilImpl {
  public native Element createFlashMovieMaybeSetMovieURL(String id,
      String movieURL)
  /*-{
    var elem = $doc.createElement("object");
    elem.classid = "clsid:d27cdb6e-ae6d-11cf-96b8-444553540000";
    elem.codebase = "http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0";
    // elem.Quality = 1; // 0=Low, 1=High, 2=AutoLow, 3=AutoHigh
    // elem.ScaleMode = 2; //0=ShowAll, 1=NoBorder, 2=ExactFit
    elem.id = id;
    return elem;
  }-*/;

  public native Element createSoundElement(String url)
  /*-{
    var elem = $doc.createElement("bgsound");
    elem.src = url;
    // elem.loop = 1; // -1 = infinitely, 0 = one time, n = number of times
    return elem;
  }-*/;

  public native void maybeSetFlashMovieURL(Element elem, String movieURL)
  /*-{
    elem.Movie = movieURL;
  }-*/;

  /**
   * Best guess at conversion formula from standard -100 .. 100 range to -10000 ..
   * 10000 range used by IE.
   * 
   * @TODO location documentation for IE
   */
  public native void setSoundElementBalance(Element elem, int balance)
  /*-{
    if (balance == -100) {
      balance = -10000;
    } else if (balance == 100) {
      balance = 10000;
    } else if (balance < 0) {
      balance = 100 - 10000 / (100 + balance);
    } else {
      balance = 10000 / (100 - balance) - 100;
    }
    elem.balance = "" + balance; // -10000 .. 10000
  }-*/;

  /**
   * Best guess at conversion formula from standard 0 .. 100 range to -10000 ..
   * 0 range used by IE.
   * 
   * @TODO location documentation for IE
   */
  public native void setSoundElementVolume(Element elem, int volume)
  /*-{
    elem.volume = volume == 0 ? -10000 : (-10000 / volume); // -10000 .. 0
  }-*/;
}
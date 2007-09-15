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
    try {
      var elem = $doc.createElement("object");
      elem.classid = "clsid:d27cdb6e-ae6d-11cf-96b8-444553540000";
      elem.codebase = "http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0";
  //      elem.Quality = 1; // 0=Low, 1=High, 2=AutoLow, 3=AutoHigh
  //      elem.ScaleMode = 2; //0=ShowAll, 1=NoBorder, 2=ExactFit
      elem.id = id;
      return elem;
    } catch(e) {
      throw new Error("Exception creating flash movie:\n" + e);
    }
  }-*/;

  public native void maybeSetFlashMovieURL(Element elem, String movieURL)
  /*-{
    try {
      elem.Movie = movieURL;
    } catch(e) {
      throw new Error("Exception setting flash movie url:\n" + e);
    }
  }-*/;
}
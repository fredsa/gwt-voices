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
 * {@link com.allen_sauer.gwt.voices.client.util.DOMUtil} implementation for
 * standard browsers.
 */
public abstract class DOMUtilImplStandard extends DOMUtilImpl {
  public native Element createFlashMovieMaybeSetMovieURL(String id,
      String movieURL)
  /*-{
    try {
      var elem = document.createElement("object");
      elem.setAttribute("type", "application/x-shockwave-flash");
      elem.setAttribute("data", movieURL);
      return elem;
    } catch(e) {
      throw new Error("Exception creating flash movie:\n" + e);
    }
  }-*/;
}

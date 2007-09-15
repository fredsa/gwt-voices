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
package com.allen_sauer.gwt.voices.client.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;

import com.allen_sauer.gwt.voices.client.util.impl.DOMUtilImpl;

/**
 * Provides DOM utility methods.
 */
public class DOMUtil {
  private static DOMUtilImpl impl;

  static {
    impl = (DOMUtilImpl) GWT.create(DOMUtilImpl.class);
  }

  public static Element createFlashMovieMaybeSetMovieURL(String id,
      String movieURL) {
    return impl.createFlashMovieMaybeSetMovieURL(id, movieURL);
  }

  public static void maybeSetFlashMovieURL(Element elem, String movieURL) {
    impl.maybeSetFlashMovieURL(elem, movieURL);
  }
}

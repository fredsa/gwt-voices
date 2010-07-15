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
package com.allen_sauer.gwt.voices.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;

import com.allen_sauer.gwt.voices.client.ui.impl.FlashMovieImpl;

/**
 * Flash movie.
 */
public class FlashMovieWidget {
  // CHECKSTYLE_JAVADOC_OFF

  /**
   * Flash movie MIME type. Typical filename extension is <code>.swf</code>.
   */
  public static final String MIME_TYPE_APPLICATION_X_SHOCKWAVE_FLASH = "application/x-shockwave-flash";
  private static final int EXTERNAL_INTERFACE_MINIMUM_MAJOR_VERSION = 8;

  private static FlashMovieImpl impl;

  static {
    impl = (FlashMovieImpl) GWT.create(FlashMovieImpl.class);
  }

  public static int getMajorVersion() {
    return impl.getMajorVersion();
  }

  public static boolean isExternalInterfaceSupported() {
    return getMajorVersion() >= EXTERNAL_INTERFACE_MINIMUM_MAJOR_VERSION;
  }

  private Element element;

  public FlashMovieWidget(String id, String url) {
    element = impl.createElementMaybeSetURL(id, url);
  }


  public Element getElement() {
    return element;
  }

}

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

import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.voices.client.util.DOMUtil;

/**
 * Flash Movie Widget.
 */
public class FlashMovie extends Widget {
  private final String movieURL;
  private boolean wasLoaded = false;

  public FlashMovie(String id, String movieURL) {
    this.movieURL = movieURL;
    setElement(DOMUtil.createFlashMovieMaybeSetMovieURL(id, movieURL));
  }

  protected void onLoad() {
    super.onLoad();
    if (wasLoaded) {
      throw new IllegalStateException(
          "Reattachment forbidden due to ExternalInterface callback registration limitations in IE");
    }
    DOMUtil.maybeSetFlashMovieURL(getElement(), movieURL);
    wasLoaded = true;
  }
}

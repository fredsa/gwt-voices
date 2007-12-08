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
package com.allen_sauer.gwt.voices.demo.client;

import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.ui.NativeSoundWidget;
import com.allen_sauer.gwt.voices.client.ui.VoicesMovieWidget;
import com.allen_sauer.gwt.voices.client.util.DOMUtil;

public class SupportedMimeTypeSoundController extends SoundController {
  public String getNativeSoundNodeName(String url) {
    NativeSoundWidget nativeSoundWidget = new NativeSoundWidget(soundContainer.getElement(), url);
    return DOMUtil.getNodeName(nativeSoundWidget.getElement());
  }

  public VoicesMovieWidget getVoicesMovie() {
    return super.getVoicesMovie();
  }
}

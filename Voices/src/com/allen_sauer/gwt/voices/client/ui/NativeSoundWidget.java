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
package com.allen_sauer.gwt.voices.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.voices.client.ui.impl.NativeSoundImpl;


/**
 * Flash movie widget.
 */
public class NativeSoundWidget extends Widget {
  protected static NativeSoundImpl impl;

  static {
    impl = (NativeSoundImpl) GWT.create(NativeSoundImpl.class);
  }

  public static int getMimeTypeSupport(String mimeType) {
    return impl.getMimeTypeSupport(mimeType);
  }

  private final Element soundControllerElement;
  private final String url;
  private boolean wasLoaded = false;

  public NativeSoundWidget(Element soundControllerElement, String mimeType, String url) {
    this.soundControllerElement = soundControllerElement;
    this.url = url;
    impl.preload(soundControllerElement, mimeType, url);
    setElement(impl.createElement(url));
  }

  public void play() {
    impl.play(soundControllerElement, getElement());
  }

  public void setBalance(int balance) {
    impl.setBalance(getElement(), balance);
  }

  public void setVolume(int volume) {
    impl.setVolume(getElement(), volume);
  }

  public void stop() {
    impl.stop(getElement());
  }
}

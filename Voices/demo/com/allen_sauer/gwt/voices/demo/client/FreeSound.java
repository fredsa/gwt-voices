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

import com.allen_sauer.gwt.voices.client.Sound;

public class FreeSound {
  private final String actualURL;
  private final String freeSoundAuthor;
  private final String freesoundAuthorURL;
  private final String freesoundFileURL;
  private final String mimeType;
  private final String originalFreesoundFilename;
  private Sound sound;

  public FreeSound(String mimeType, String actualURL, String originalFreesoundFilename, String freesoundFileURL,
      String freeSoundAuthor, String freesoundAuthorURL) {
    this.mimeType = mimeType;
    this.originalFreesoundFilename = originalFreesoundFilename;
    this.actualURL = actualURL;
    this.freesoundFileURL = freesoundFileURL;
    this.freeSoundAuthor = freeSoundAuthor;
    this.freesoundAuthorURL = freesoundAuthorURL;
  }

  public String getActualURL() {
    return actualURL;
  }

  public String getFreeSoundAuthor() {
    return freeSoundAuthor;
  }

  public String getFreesoundAuthorURL() {
    return freesoundAuthorURL;
  }

  public String getFreesoundFileURL() {
    return freesoundFileURL;
  }

  public String getMimeType() {
    return mimeType;
  }

  public Sound getSound() {
    return sound;
  }

  public void setSound(Sound sound) {
    this.sound = sound;
  }

  public String toHTMLString() {
    return "<a href='" + getFreesoundFileURL() + "'>" + originalFreesoundFilename + "</a> by <a href='" + getFreesoundAuthorURL()
        + "'>" + getFreeSoundAuthor() + "</a>";
  }

  public String toString() {
    return originalFreesoundFilename;
  }
}

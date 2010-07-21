/*
 * Copyright 2009 Fred Sauer
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

// CHECKSTYLE_JAVADOC_OFF
public class FreesoundProjectSound extends ThirdPartySound {
  private final String actualURL;
  private final String freesoundProjectAuthorURL;
  private final String freesoundProjectFileURL;
  private final String freesoundProjectSoundAuthor;
  private final String originalFreesoundProjectFilename;
  private final String audioFormatDescription;

  public FreesoundProjectSound(String mimeType, String actualURL,
      String originalFreesoundProjectFilename, String freesoundProjectFileURL,
      String freesoundProjectSoundAuthor, String freesoundProjectAuthorURL,
      String audioFormatDescription) {
    super(mimeType);
    this.originalFreesoundProjectFilename = originalFreesoundProjectFilename;
    this.actualURL = actualURL;
    this.freesoundProjectFileURL = freesoundProjectFileURL;
    this.freesoundProjectSoundAuthor = freesoundProjectSoundAuthor;
    this.freesoundProjectAuthorURL = freesoundProjectAuthorURL;
    this.audioFormatDescription = audioFormatDescription;
  }

  @Override
  public String getActualURL() {
    return actualURL;
  }

  public String getFreeSoundAuthor() {
    return freesoundProjectSoundAuthor;
  }

  public String getFreesoundAuthorURL() {
    return freesoundProjectAuthorURL;
  }

  public String getFreesoundFileURL() {
    return freesoundProjectFileURL;
  }

  @Override
  public String toHTMLString() {
    return "<a href='" + getFreesoundFileURL() + "'>" + originalFreesoundProjectFilename
        + "</a> by <a href='" + getFreesoundAuthorURL() + "'>" + getFreeSoundAuthor() + "</a>; "
        + audioFormatDescription;
  }

  @Override
  public String toString() {
    return originalFreesoundProjectFilename;
  }
}

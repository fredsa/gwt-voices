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
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.handler.SoundHandler;

public class DemoSound {
  private static SoundController soundController = new SoundController();
  private final String author;
  private final String authorURL;
  private final String baseFilename;
  private final String originalFileURL;
  private final String orignalFileName;
  private Sound sound;

  public DemoSound(String baseFilename, String author, String authorURL,
      String orignalFileName, String originalFileURL) {
    sound = soundController.createSound("freesoundproject/" + baseFilename
        + ".mp3");
    this.baseFilename = baseFilename;
    this.author = author;
    this.authorURL = authorURL;
    this.orignalFileName = orignalFileName;
    this.originalFileURL = originalFileURL;
  }

  public void addSoundHandler(SoundHandler soundHandler) {
    sound.addSoundHandler(soundHandler);
  }

  public String getAuthor() {
    return author;
  }

  public String getAuthorURL() {
    return authorURL;
  }

  public String getBaseFilename() {
    return baseFilename;
  }

  public String getOriginalFileURL() {
    return originalFileURL;
  }

  public String getOrignalFileName() {
    return orignalFileName;
  }

  public void play() {
    sound.play();
  }

  public String toHTMLString() {
    return "<a href='" + getOriginalFileURL() + "'>" + getOrignalFileName()
        + "</a> by <a href='" + getAuthorURL() + "'>" + getAuthor() + "</a>";
  }

  public String toString() {
    return getOrignalFileName();
  }
}

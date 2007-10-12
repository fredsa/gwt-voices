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

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.ui.NativeSoundWidget;
import com.allen_sauer.gwt.voices.client.ui.VoicesMovieWidget;

public class SupportedMimeTypeSummary extends Composite {
  private static final String CSS_DEMO_SUPPORTED_MIME_TYPE_SUMMARY_TABLE = "demo-SupportedMimeTypeSummary-table";

  private static native String getUserAgent()
  /*-{
    return navigator.userAgent;
  }-*/;

  private VerticalPanel containerPanel;

  public SupportedMimeTypeSummary() {
    containerPanel = new VerticalPanel();
    initWidget(containerPanel);
  }

  protected void onLoad() {
    super.onLoad();
    containerPanel.add(new HTML("Please wait. Initializing sound detection..."));

    DeferredCommand.addCommand(new Command() {
      public void execute() {
        addSupportMatrix();
      }
    });
  }

  private void addSupportMatrix() {
    containerPanel.clear();
    
    containerPanel.add(new HTML("Your user agent is:"));
    HTML userAgentHTML = new HTML(getUserAgent());
    userAgentHTML.addStyleName("demo-user-agent");
    containerPanel.add(userAgentHTML);
    containerPanel.add(new HTML("This browser/platform,"
        + " and its installed plugins,"
        + " provide the following sound support via gwt-voices:"));

    FlexTable flexTable = new FlexTable();
    flexTable.addStyleName(CSS_DEMO_SUPPORTED_MIME_TYPE_SUMMARY_TABLE);
    containerPanel.add(flexTable);
    flexTable.setWidget(0, 0, new HTML("MIME Type"));
    flexTable.setWidget(0, 1, new HTML("Flash based support"));
    flexTable.setWidget(0, 2, new HTML(
        "Native browser <i>or</i> Plugin based support"));
    flexTable.getRowFormatter().addStyleName(0, "header");

    SupportedMimeTypeSoundController soundController = new SupportedMimeTypeSoundController();
    VoicesMovieWidget movieWidget = soundController.getVoicesMovie();

    String[] mimeTypes = {
        Sound.MIME_TYPE_AUDIO_BASIC, Sound.MIME_TYPE_AUDIO_MPEG,
        Sound.MIME_TYPE_AUDIO_X_AIFF, Sound.MIME_TYPE_AUDIO_X_MIDI,
        Sound.MIME_TYPE_AUDIO_X_WAV,};

    for (int i = 0; i < mimeTypes.length; i++) {
      String mimeType = mimeTypes[i];

      // Native/Plugin based support
      int nativeMimeTypeSupport = NativeSoundWidget.getMimeTypeSupport(mimeType);
      String nativeMimeTypeSupportText = mimeTypeSupportToString(nativeMimeTypeSupport);
      if (nativeMimeTypeSupport == SoundController.MIME_TYPE_SUPPORTED) {
        nativeMimeTypeSupportText += " via <code>"
            + soundController.getNativeSoundNodeName("empty.dat") + "</code>";
      }

      // Flash based support
      int flashMimeTypeSupport = movieWidget.getMimeTypeSupport(mimeType);
      String flashMimeTypeSupportText = mimeTypeSupportToString(flashMimeTypeSupport);

      flexTable.setWidget(i + 1, 0, new HTML("<code>" + mimeType + "</code>"));
      flexTable.setWidget(i + 1, 1, new HTML(flashMimeTypeSupportText));
      flexTable.setWidget(i + 1, 2, new HTML(nativeMimeTypeSupportText));
      flexTable.getRowFormatter().addStyleName(i + 1,
          i % 2 == 0 ? "odd" : "even");
    }
  }

  private String mimeTypeSupportToString(int mimeTypeSupport) {
    String text;
    String color;
    switch (mimeTypeSupport) {
      case SoundController.MIME_TYPE_SUPPORTED:
        text = "Supported";
        color = "#00BB00";
        break;
      case SoundController.MIME_TYPE_UNSUPPORTED:
        text = "Unsupported";
        color = "#BB0000";
        break;
      case SoundController.MIME_TYPE_SUPPORT_UNKNOWN:
        text = "Support Unknown";
        color = "#FF8040";
        break;
      case SoundController.MIME_TYPE_SUPPORTED_NOT_LOADED:
        text = "Supported, but not (yet) Loaded";
        color = "#0000BB";
        break;
      default:
        throw new IllegalArgumentException("unknown MIME type support "
            + mimeTypeSupport);
    }
    return "<span style='color: " + color + ";'>" + text + "</span>";
  }
}

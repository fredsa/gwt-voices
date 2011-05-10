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
package com.allen_sauer.gwt.voices.demo.client.ui;

import static com.allen_sauer.gwt.voices.client.SoundController.MimeTypeSupport.MIME_TYPE_SUPPORT_READY;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.allen_sauer.gwt.voices.client.Html5Sound;
import com.allen_sauer.gwt.voices.client.NativeSound;
import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.SoundController.MimeTypeSupport;
import com.allen_sauer.gwt.voices.client.ui.VoicesMovie;
import com.allen_sauer.gwt.voices.demo.client.DemoClientBundle;
import com.allen_sauer.gwt.voices.demo.client.VoicesDemo;

// CHECKSTYLE_JAVADOC_OFF
public class SupportedMimeTypeSummary extends Composite {

  private static native String getUserAgent() /*-{
    return navigator.userAgent;
  }-*/;

  public SupportedMimeTypeSummary() {
    VerticalPanel containerPanel = new VerticalPanel();
    containerPanel.clear();

    containerPanel.add(new HTML("Your user agent is:"));
    HTML userAgentHTML = new HTML(getUserAgent());
    userAgentHTML.addStyleName(DemoClientBundle.INSTANCE.css().demoUserAgent());
    containerPanel.add(userAgentHTML);
    containerPanel.add(new HTML("This browser/platform," + " and its installed plugins,"
        + " provide the following sound support via gwt-voices:"));

    FlexTable flexTable = new FlexTable();
    flexTable.addStyleName(DemoClientBundle.INSTANCE.css().demoSupportedMimeTypeSummaryTable());
    containerPanel.add(flexTable);
    flexTable.setWidget(0, 0, new HTML("MIME Type"));
    flexTable.setWidget(0, 1, new HTML("HTML5 audio"));
    flexTable.setWidget(0, 2, new HTML("Native browser <i>or</i> Plugin based support"));
    flexTable.setWidget(0, 3, new HTML("Flash based support"));
    flexTable.getRowFormatter().addStyleName(0, DemoClientBundle.INSTANCE.css().header());

    SoundController soundController = new SoundController();

    int i = 0;
    for (String mimeType : VoicesDemo.MIME_TYPES) {
      i++;

      // Native/Plugin based support
      MimeTypeSupport nativeMimeTypeSupport = NativeSound.getMimeTypeSupport(mimeType);
      String nativeMimeTypeSupportText = mimeTypeSupportToString(nativeMimeTypeSupport);
      Sound dummySound = soundController.createSound(Sound.MIME_TYPE_AUDIO_BASIC, "empty.au");
      if (nativeMimeTypeSupport == MIME_TYPE_SUPPORT_READY) {
        nativeMimeTypeSupportText += " via <code>" + dummySound.getSoundType() + "</code>";
      }

      // Flash based support
      VoicesMovie movieWidget = new VoicesMovie("gwt-voices-dummy");
      MimeTypeSupport flashMimeTypeSupport = movieWidget.getMimeTypeSupport(mimeType);
      String flashMimeTypeSupportText = mimeTypeSupportToString(flashMimeTypeSupport);

      // HTML5 audio
      MimeTypeSupport html5MimeTypeSupport = Html5Sound.getMimeTypeSupport(mimeType);
      String html5MimeTypeSupportText = mimeTypeSupportToString(html5MimeTypeSupport);

      // Place results in the table
      flexTable.setWidget(i + 1, 0, new HTML("<code>" + mimeType + "</code>"));
      flexTable.setWidget(i + 1, 1, new HTML(html5MimeTypeSupportText));
      flexTable.setWidget(i + 1, 2, new HTML(nativeMimeTypeSupportText));
      flexTable.setWidget(i + 1, 3, new HTML(flashMimeTypeSupportText));
      flexTable.getRowFormatter().addStyleName(
          i + 1,
          i % 2 == 0 ? DemoClientBundle.INSTANCE.css().odd()
              : DemoClientBundle.INSTANCE.css().even());
    }

    initWidget(containerPanel);
  }

  private native void eval(String t) /*-{
    $wnd.eval(t);
  }-*/;

  private String mimeTypeSupportToString(MimeTypeSupport mimeTypeSupport) {
    String text;
    String color;
    switch (mimeTypeSupport) {
      case MIME_TYPE_SUPPORT_READY:
        text = "Supported";
        color = "#00BB00";
        break;
      case MIME_TYPE_NOT_SUPPORTED:
        text = "Unsupported";
        color = "#BB0000";
        break;
      case MIME_TYPE_SUPPORT_UNKNOWN:
        text = "Support Unknown";
        color = "#FF8040";
        break;
      case MIME_TYPE_SUPPORT_NOT_READY:
        text = "Supported, but not (yet) Loaded";
        color = "#0000BB";
        break;
      default:
        throw new IllegalArgumentException("unknown MIME type support " + mimeTypeSupport);
    }
    return "<span style='color: " + color + ";'>" + text + "</span>";
  }
}

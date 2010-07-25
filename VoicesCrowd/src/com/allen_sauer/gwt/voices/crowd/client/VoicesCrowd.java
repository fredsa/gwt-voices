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
package com.allen_sauer.gwt.voices.crowd.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import com.allen_sauer.gwt.voices.crowd.shared.MimeType;
import com.allen_sauer.gwt.voices.crowd.shared.TestResultSummary;
import com.allen_sauer.gwt.voices.crowd.shared.TestResults;
import com.allen_sauer.gwt.voices.crowd.shared.UserAgent;

import java.util.List;

public class VoicesCrowd implements EntryPoint {

  public static native String canPlayType(String mimeType) /*-{
    var audio = document.createElement('audio');
    if (!audio.canPlayType) {
      return "";
    }
    return audio.canPlayType(mimeType);
  }-*/;

  private final ResultsServiceAsync service = GWT.create(ResultsService.class);

  private boolean embed = Window.Location.getParameter("embed") != null;
  private boolean debug = Window.Location.getParameter("debug") != null;
  private RootPanel rootPanel;

  public void onModuleLoad() {
    rootPanel = RootPanel.get("demo-main-panel");
    myUserAgent = new UserAgent(Window.Navigator.getUserAgent());
    log("<br><b>Test results...</b>");
    String[] results = new String[TestResults.MIME_TYPES.length];
    for (int i = 0; i < TestResults.MIME_TYPES.length; i++) {
      String mimeType = TestResults.MIME_TYPES[i].toString();
      String canPlayType = canPlayType(mimeType);
      results[i] = canPlayType;
      log(mimeType + ":" + canPlayType);
    }
    storeResults(new TestResults(results));
  }

  private UserAgent myUserAgent;

  private void log(String text) {
    if (debug) {
      rootPanel.add(new HTML(text));
    }
  }

  private void getAndDisplaySummaryResults() {
    log("<br><b>Retrieving summary results...</b>");
    service.getSummary(new AsyncCallback<List<TestResultSummary>>() {

      public void onFailure(Throwable caught) {
        log("<b style='color:red;'>Failed to retrieve results.</b>");
        removeLoadingMessage();
      }

      public void onSuccess(List<TestResultSummary> list) {
        log("<b style='color:green;'>Results received.</b>");
        renderSummary(list);
        removeLoadingMessage();
      }

    });
  }

  private void removeLoadingMessage() {
    DOM.getElementById("demo-loading").removeFromParent();
  }

  private void renderSummary(List<TestResultSummary> list) {
    // Build HTML table
    StringBuffer html = new StringBuffer();

    if (!embed) {
      html.append("<div style='font-weight: bold; font-size: 1.2em;'>").append(
          "<a href='http://code.google.com/p/gwt-voices/'>gwt-voices</a>").append(
          " - Sound for your Google Web Toolkit projects.</div>").append(
          "<div style='font-style: italic; margin-bottom: 1em;'>by Fred Sauer</div>");

      html.append("<h3>Your user agent</h3>");
      html.append("<div style='margin-left: 1em;'>").append(myUserAgent.toString()).append(
          "</div>");
      html.append("<h3 style='margin-top: 3em;'>HTML5 MIME Type support by User-Agent</h3>");
    }
    html.append("<table>");

    // Header row
    html.append("<tr>");
    html.append(
        "<td style='text-align: center; padding: 0.2em 0.2em; font-family: monospace; font-weight: bold; background-color: #ccc;'>User-Agent</td>");
    html.append(
        "<td style='text-align: center; padding: 0.2em 0.2em; font-family: monospace; font-weight: bold; background-color: #ccc;'>GWT user.agent</td>");
    for (MimeType mimeType : TestResults.MIME_TYPES) {
      html.append(
          "<td style='text-align: center; padding: 0.2em 0.2em; font-family: monospace; font-weight: bold; background-color: #ccc;'>");
      html.append(mimeType.toString());
      html.append("</td>");
    }
    html.append(
        "<td style='font-weight: bold; text-align: center; background-color: #ccc;'>Count</td>");

    html.append("</tr>");

    for (TestResultSummary summary : list) {
      UserAgent ua = new UserAgent(summary.getUserAgent());
      String[] results = summary.getTestResults().getResults();
      html.append("<tr>");
      html.append("<td style='padding: 0.2em 0.2em; background-color: ").append(
          (ua.toString().equals(Window.Navigator.getUserAgent()) ? "yellow" : "#ccc")).append(
          ";'>").append(ua.toString()).append("</td>").append(
          "<td style='padding: 0.2em 0.2em; text-align: center; background-color: ").append(
          (ua.toString().equals(Window.Navigator.getUserAgent()) ? "yellow" : "#ccc")).append(
          ";'>").append(summary.getGwtUserAgent()).append("</td>");
      for (int i = 0; i < TestResults.MIME_TYPES.length; i++) {
        String mimeType = TestResults.MIME_TYPES[i].toString();
        String canPlayType = results[i];
        String color = toColor(canPlayType);
        html.append(
            "<td style='text-align: center; font-family: monospace; padding: 0.2em 0.2em; background-color: ");
        html.append(color);
        html.append(";'>");
        html.append(canPlayType == null ? "(null)" : "'" + canPlayType + "'");
        html.append("</td>");
      }
      html.append("<td style='text-align: center; background-color: #ccc;'>").append(
          summary.getCount()).append("</td>");

      html.append("</tr>");
    }

    html.append("</table>");
    rootPanel.add(new HTML(html.toString()));
  }

  private String toColor(String canPlayType) {
    if ("probably".equals(canPlayType)) {
      return "green";
    } else if ("maybe".equals(canPlayType)) {
      return "orange";
    } else {
      return "red";
    }
  }

  private void storeResults(TestResults results) {
    log("<br><b>Storing our test results...</b>");
    // TODO Use GwtUserAgentProvider instead, once GWT issue 5158 is fixed
    // See http://code.google.com/p/google-web-toolkit/issues/detail?id=5158
    // GwtUserAgentProvider gwtUserAgentProvider = GWT.create(GwtUserAgentProvider.class);
    UserAgentProvider userAgent = GWT.create(UserAgentProvider.class);

    service.storeResults(
        myUserAgent, userAgent.getUserAgent(), results, new AsyncCallback<Boolean>() {
          public void onFailure(Throwable caught) {
            log("<b style='color:red;'>Failed to send our test results.</b>");
            getAndDisplaySummaryResults();
          }

          public void onSuccess(Boolean result) {
            if (result.booleanValue()) {
              log("<b style='color:green;'>Results sent and accepted.</b>");
            } else {
              log("<b style='color:black;'>Results sent and ignored.</b>");
            }
            getAndDisplaySummaryResults();
          }
        });
  }

}

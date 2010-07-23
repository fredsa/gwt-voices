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

  public void onModuleLoad() {
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
    // RootPanel.get().add(new HTML(text));
  }

  private void getAndDisplaySummaryResults() {
    log("<br><b>Retrieving summary results...</b>");
    service.getSummary(new AsyncCallback<List<TestResultSummary>>() {

      public void onFailure(Throwable caught) {
        log("<b style='color:red;'>Failed to retrieve results.</b>");
      }

      public void onSuccess(List<TestResultSummary> list) {
        log("<b style='color:green;'>Results received.</b>");
        renderSummary(list);
      }
    });
  }

  private void renderSummary(List<TestResultSummary> list) {
    // Build HTML table
    StringBuffer html = new StringBuffer();
    html.append(myUserAgent.toString());
    html.append("<table>");

    // Header row
    html.append("<tr>");
    html.append("<td/>");
    for (MimeType mimeType : TestResults.MIME_TYPES) {
      html
          .append(
              "<td style='text-align: center; padding: 0.2em 0.2em; font-family: monospace; font-weight: bold; background-color: #ccc;'>");
      html.append(mimeType.toString());
      html.append("</td>");
    }
    html.append("<td>Count</td>");

    html.append("</tr>");

    for (TestResultSummary summary : list) {
      UserAgent ua = new UserAgent(summary.getUserAgent());
      String[] results = summary.getTestResults().getResults();
      html.append("<tr>");
      html
          .append("<td style='padding: 0.2em 0.2em; background-color: ")
          .append(
              (ua.toString().equals(Window.Navigator.getUserAgent()) ? "yellow" : "#ccc"))
          .append(";'>")
          .append(ua.toString())
          .append("</td>");
      for (int i = 0; i < TestResults.MIME_TYPES.length; i++) {
        String mimeType = TestResults.MIME_TYPES[i].toString();
        String canPlayType = results[i];
        String color = toColor(canPlayType);
        html
            .append(
                "<td style='text-align: center; font-family: monospace; padding: 0.2em 0.2em; background-color: ");
        html.append(color);
        html.append(";'>");
        html.append(canPlayType == null ? "(null)" : "'" + canPlayType + "'");
        html.append("</td>");
      }
      html.append("<td>").append(summary.getCount()).append("</td>");

      html.append("</tr>");
    }

    html.append("</table>");
    RootPanel.get().add(new HTML(html.toString()));
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
    service.storeResults(myUserAgent, results, new AsyncCallback<Boolean>() {
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

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

import com.allen_sauer.gwt.voices.crowd.shared.MimeType;
import com.allen_sauer.gwt.voices.crowd.shared.TestResultSummary;
import com.allen_sauer.gwt.voices.crowd.shared.TestResults;
import com.allen_sauer.gwt.voices.crowd.shared.UserAgent;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.HashSet;
import java.util.List;

public class VoicesCrowd implements EntryPoint {

  public static native String canPlayType(String mimeType) /*-{
    var audio = document.createElement('audio');
    if (!audio.canPlayType) {
    return "";
    }
    return audio.canPlayType(mimeType);
  }-*/;

  private boolean debug = Window.Location.getParameter("debug") != null;
  private boolean detail = Window.Location.getParameter("detail") != null;
  private boolean embed = Window.Location.getParameter("embed") != null;

  private TestResultSummary myTestResultSummary;
  private UserAgent myUserAgent;
  private RootPanel rootPanel;

  private final ResultsServiceAsync service = GWT.create(ResultsService.class);

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

  private String formatPrettyUserAgentOrNull(String prettyUserAgent) {
    return prettyUserAgent != null ? prettyUserAgent : "&lt;unknown&gt;";
  }

  private void getAndDisplaySummaryResults() {
    log("<br><b>Retrieving summary results...</b>");
    service.getSummary(new AsyncCallback<List<TestResultSummary>>() {

      public void onFailure(Throwable caught) {
        logAlways(
            "<b style='color:red;'>Failed to retrieve results. Check server logs for details.</b>");
        removeLoadingMessage();
      }

      public void onSuccess(List<TestResultSummary> list) {
        log("<b style='color:green;'>Results received.</b>");
        renderSummary(list, detail);
        removeLoadingMessage();
      }

    });
  }

  private HashSet<Tuple<String>> getMatchingTuples(TestResults testResults,
      List<TestResultSummary> list, boolean includeUserAgentDetail) {
    HashSet<Tuple<String>> tuples = new HashSet<Tuple<String>>();

    for (TestResultSummary summary : list) {
      TestResults tr = summary.getTestResults();
      Tuple<String> tuple;
      tuple = makeTuple(summary, includeUserAgentDetail);

      if (testResults.equals(tr)) {
        tuples.add(tuple);
      }
    }

    return tuples;
  }

  private HashSet<TestResults> getUniqueTestResults(List<TestResultSummary> list) {
    HashSet<TestResults> testResultsSet;
    testResultsSet = new HashSet<TestResults>();
    for (TestResultSummary summary : list) {
      testResultsSet.add(summary.getTestResults());
    }
    return testResultsSet;
  }

  private void log(String text) {
    if (debug) {
      logAlways(text);
    }
  }

  private void logAlways(String text) {
    rootPanel.add(new HTML(text));
  }

  private void makeHeaderRow(StringBuffer html, boolean includeUserAgentDetail) {
    // Header row
    html.append("<tr>");

    // user agent headings
    String countText = "#";
    String gwtUserAgentText = "GWT user.agent";
    String originalUAText = "$wnd.navigator.userAgent";
    String prettyUserAgentText = "Browser";

    Tuple<String> tuple = includeUserAgentDetail ? new Tuple<String>(countText, gwtUserAgentText, originalUAText,
        prettyUserAgentText) : new Tuple<String>(prettyUserAgentText);

    for (int i = 0; i < tuple.getElements().length; i++) {
      html.append("<td style='text-align: center; background-color: #ccc; font-weight: bold;'>"
          + tuple.getElements()[i] + "</td>");
    }

    // MIME type headings
    for (MimeType mimeType : TestResults.MIME_TYPES) {
      html.append("<td style='text-align: center; padding: 0.2em 0.2em; font-family: monospace; font-weight: bold; background-color: #ccc;'>");
      html.append(mimeType.toString());
      html.append("</td>");
    }

    html.append("</tr>");
  }

  private void makeResultCells(StringBuffer html, TestResults testResults, int rowspan) {
    // result table cells
    String[] results = testResults.getResults();
    for (int i = 0; i < TestResults.MIME_TYPES.length; i++) {
      String canPlayType = results[i];
      String color = toColor(canPlayType);
      html.append("<td style='text-align: center; font-family: monospace; padding: 0.2em 0.2em; background-color: ");
      html.append(color);
      html.append(";' rowspan='" + rowspan + "'>");
      html.append(canPlayType == null ? "(null)" : "'" + canPlayType + "'");
      html.append("</td>");
    }
  }

  private Tuple<String> makeTuple(TestResultSummary summary, boolean includeUserAgentDetail) {
    Tuple<String> tuple;
    String prettyUA = formatPrettyUserAgentOrNull(summary.getPrettyUserAgent());
    String gwtUA = summary.getGwtUserAgent();
    if (includeUserAgentDetail) {
      String originalUA = summary.getUserAgent().toString();
      tuple = new Tuple<String>("" + summary.getCount(), gwtUA, originalUA, prettyUA);
    } else {
      tuple = new Tuple<String>(prettyUA);
    }
    return tuple;
  }

  private void makeUserAgentCells(StringBuffer html, Tuple<String> tuple, boolean highlightRow) {
    String color = highlightRow ? "yellow" : "#ccc";

    for (String elem : tuple.getElements()) {
      html.append("<td style='padding: 0.2em 0.2em; background-color: ").append(color).append(
          "; white-space: nowrap;'>").append(elem).append("</td>");
    }
  }

  private void removeLoadingMessage() {
    DOM.getElementById("demo-loading").removeFromParent();
  }

  private void renderSummary(List<TestResultSummary> list, boolean includeUserAgentDetail) {
    HashSet<TestResults> testResultsSet = getUniqueTestResults(list);

    // Build HTML table
    StringBuffer html = new StringBuffer();

    if (!embed) {
      html.append("<div style='font-weight: bold; font-size: 1.2em;'>").append(
          "<a href='http://code.google.com/p/gwt-voices/'>gwt-voices</a>").append(
          " - Sound for your Google Web Toolkit projects.</div>").append(
          "<div style='font-style: italic; margin-bottom: 1em;'>by Fred Sauer</div>");

      html.append("<h3>Your user agent</h3>");
      html.append("<div style='margin-left: 1.5em;'>").append(myUserAgent.toString()).append(
          "</div>");

      html.append("<h3>Your browser</h3>");
      String prettyUserAgent = myTestResultSummary.getPrettyUserAgent();
      html.append("<div style='margin-left: 1.5em;'>").append(
          formatPrettyUserAgentOrNull(prettyUserAgent)).append("</div>");

      html.append("<h3 style='margin-top: 3em;'>HTML5 MIME Type support by User-Agent</h3>");
    }
    UrlBuilder urlBuilder = Window.Location.createUrlBuilder();
    urlBuilder = detail ? urlBuilder.removeParameter("detail") : urlBuilder.setParameter("detail",
        "1");
    String text = (detail ? "Hide" : "Show full") + " user agent values";
    html.append("<a href='" + urlBuilder.buildString() + "'>" + text + "</a>");

    html.append("<table>");

    for (TestResults testResults : testResultsSet) {
      makeHeaderRow(html, includeUserAgentDetail);
      HashSet<Tuple<String>> tuples = getMatchingTuples(testResults, list, includeUserAgentDetail);

      int count = 0;
      for (Tuple<String> tuple : tuples) {
        count++;
        html.append("<tr>");
        boolean highlightRow = makeTuple(myTestResultSummary, includeUserAgentDetail).equals(tuple);
        makeUserAgentCells(html, tuple, highlightRow);

        if (count == 1) {
          makeResultCells(html, testResults, tuples.size());
        }
        html.append("</tr>");
      }
    }

    html.append("</table>");
    rootPanel.add(new HTML(html.toString()));
  }

  private void storeResults(TestResults testResults) {
    log("<br><b>Storing our test results...</b>");
    // TODO Use GwtUserAgentProvider instead, once GWT issue 5158 is fixed
    // See http://code.google.com/p/google-web-toolkit/issues/detail?id=5158
    // GwtUserAgentProvider gwtUserAgentProvider = GWT.create(GwtUserAgentProvider.class);
    UserAgentProvider userAgent = GWT.create(UserAgentProvider.class);

    service.storeResults(myUserAgent, userAgent.getUserAgent(), testResults,
        new AsyncCallback<TestResultSummary>() {

          public void onFailure(Throwable caught) {
            log("<b style='color:red;'>Failed to send our test results.</b>");
            getAndDisplaySummaryResults();
          }

          public void onSuccess(TestResultSummary testResultSummary) {
            myTestResultSummary = testResultSummary;
            if (testResultSummary != null) {
              log("<b style='color:green;'>Results sent and accepted.</b>");
            } else {
              log("<b style='color:orange;'>Results sent and ignored.</b>");
            }
            getAndDisplaySummaryResults();
          }
        });
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

}

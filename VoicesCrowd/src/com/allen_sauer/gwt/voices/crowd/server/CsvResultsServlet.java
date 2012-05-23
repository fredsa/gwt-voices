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
package com.allen_sauer.gwt.voices.crowd.server;

import com.allen_sauer.gwt.voices.crowd.shared.TestResultSummary;
import com.allen_sauer.gwt.voices.crowd.shared.TestResults;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class CsvResultsServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    // Print CSV results
    resp.setHeader("Content-Type", "text/plain");
    Objectify ofy = ObjectifyService.begin();
    List<TestResultSummary> summaryList = ofy.query(TestResultSummary.class).chunkSize(5000).list();

    for (TestResultSummary summary : summaryList) {
      TestResults testResults = summary.getTestResults();
      PrintWriter writer = resp.getWriter();
      StringBuffer buffer = new StringBuffer();

      buffer.append('"').append(summary.getPrettyUserAgent()).append('"');
      buffer.append(',');
      buffer.append('"').append(summary.getGwtUserAgent()).append('"');
      buffer.append(',');
      buffer.append('"').append(summary.getUserAgent()).append('"');
      buffer.append(',');
      buffer.append('"').append(testResults.toString()).append('"');

      writer.println(buffer);
    }
  }

}

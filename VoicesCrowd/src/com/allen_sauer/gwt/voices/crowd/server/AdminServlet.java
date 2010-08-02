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

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import com.allen_sauer.gwt.voices.crowd.shared.TestResults;
import com.allen_sauer.gwt.voices.crowd.shared.UserAgent;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminServlet extends HttpServlet {

  private static void incrementTestResultCount(
      String userAgentString, String gwtUserAgent, String resultsString) throws IOException {
    Util.incrementTestResultCount(
        new UserAgent(userAgentString), gwtUserAgent, new TestResults(resultsString));
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String ourUrl = req.getRequestURL().toString() + "?" + req.getQueryString();
    UserService service = UserServiceFactory.getUserService();
    if (!service.isUserLoggedIn()) {
      resp.sendRedirect(service.createLoginURL(ourUrl));
    } else if (!service.isUserAdmin()) {
      resp.setHeader("Content-Type", "text/html");
      resp.getWriter().println(
          "You are <b>not</b> an admin.<br><br><br><a href='" + service.createLogoutURL(ourUrl)
              + "'>Logout here</a>, then log back in.");
    } else {
      if (req.getParameter("load_test_data") != null) {
        loadTestData();
        resp.setHeader("Content-Type", "text/plain");
        resp.getWriter().println("Test data loaded.");
      } else {
        resp.setHeader("Content-Type", "text/html");
        resp.getWriter().println(
            "Welcome, administrator " + service.getCurrentUser() + ".<br><br><br><a href='"
                + service.createLogoutURL(ourUrl) + "'>Logout here</a>.");
      }
    }
    return;
  }

  private void loadTestData() throws IOException {
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/532.0 (KHTML, like Gecko) Chrome/3.0.195.38 Safari/532.0", "safari", "||maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.1; pt-BR; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8",
        "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; VB_gameztar; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; hotvideobar_1_1_178805136491_124_9; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.5.30729; .NET CLR 3.0.30729; AskTbBT3/5.8.0.12304)", "ie8", "|||||||||||||||||||||");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; es-ES; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8",
        "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.1.10) Gecko/20100506 SUSE/3.5.10-0.1.1 Firefox/3.5.10", "gecko1_8", "||||||maybe|maybe||maybe|maybe||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_8; en-us) AppleWebKit/533.16 (KHTML, like Gecko) Version/5.0 Safari/533.16", "safari", "maybe|maybe|||||maybe|||probably|||probably|||probably||||||");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_4; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.466.4 Safari/534.3", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8 ( .NET CLR 3.5.30729)", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_3; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.473.0 Safari/534.3", "safari", "||||||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.125 Safari/533.4", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.14 Safari/534.3", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; GTB6.5; .NET CLR 2.0.50727; .NET CLR 1.1.4322; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)", "ie8", "|||||||||||||||||||||");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8",
        "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_4; en-US) AppleWebKit/534.4 (KHTML, like Gecko) Chrome/6.0.479.0 Safari/534.4", "safari", "||||||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.8) Gecko/20100723 Ubuntu/8.04 (hardy) Firefox/3.6.8", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/533.16 (KHTML, like Gecko) Version/5.0 Safari/533.16", "safari", "|||||||||||||||||||||");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.1.10) Gecko/20100504 Firefox/3.5.10 GTB7.1 ( .NET CLR 3.5.30729)", "gecko1_8", "||||||maybe|maybe||maybe|maybe||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; InfoPath.1; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 1.0.3705)", "ie6", "|||||||||||||||||||||");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux x86_64; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.11 Safari/534.3", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; .NET CLR 1.0.3705; .NET CLR 1.1.4322; Media Center PC 4.0; .NET CLR 2.0.50727; OfficeLiveConnector.1.3; OfficeLivePatch.0.0; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET4.0C; .NET4.0E)", "ie8", "|||||||||||||||||||||");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.11) Gecko/20100701 Firefox/3.5.11", "gecko1_8", "||||||maybe|maybe||maybe|maybe||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.5; en-US; rv:1.9.2.3) Gecko/20100401 Firefox/3.6.3 GTB7.1", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; .NET CLR 2.0.50727; .NET CLR 1.1.4322; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; InfoPath.2; MS-RTC LM 8)", "ie6", "|||||||||||||||||||||");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.125 Safari/533.4", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; ru; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8",
        "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; .NET CLR 2.0.50727)", "ie6",
        "|||||||||||||||||||||");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/532.5 (KHTML, like Gecko) Chrome/4.0.249.43 Safari/532.5", "safari", "||maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.2.7) Gecko/20100715 Ubuntu/10.04 (lucid) Firefox/3.6.7", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.2.3) Gecko/20100401 Firefox/3.6.3 (.NET CLR 3.5.30729)", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_4; en-us) AppleWebKit/533.16 (KHTML, like Gecko) Version/5.0 Safari/533.16", "safari", "maybe|maybe|maybe|probably|||maybe|||probably|||probably|||probably||||||");
    incrementTestResultCount(
        "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; Foxy/1; (R1 1.6); .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.1)", "ie8", "|||||||||||||||||||||");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux x86_64; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.0 Safari/534.3", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux x86_64; es-ES; rv:1.9.2.4) Gecko/20100624 CentOS/3.6-8.el5.centos Firefox/3.6.4", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.0; fr; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8 ( .NET CLR 3.5.30729)", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.7) Gecko/20100716 Ubuntu/8.04 (hardy) Firefox/3.6.7", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.99 Safari/533.4", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.99 Safari/533.4", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_4; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.99 Safari/533.4", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.2.8) Gecko/20100723 Ubuntu/10.04 (lucid) Firefox/3.6.8 GTB7.1", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_8; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.55 Safari/533.4", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; en-US; rv:1.9.2.6) Gecko/20100625 Firefox/3.6.6 GTB7.1", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.2.6) Gecko/20100626 SUSE/3.6.6-1.2 Firefox/3.6.6", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; GTB6.5; .NET CLR 2.0.50727; .NET CLR 1.1.4322; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)", "ie8", "|||||||||||||||||||||");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.5; en-US; rv:1.9.1.11) Gecko/20100701 Firefox/3.5.11", "gecko1_8", "||||||maybe|maybe||maybe|maybe||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.1.10) Gecko/20100504 Firefox/3.5.10 GTB7.1 (.NET CLR 3.5.30729)", "gecko1_8", "||||||maybe|maybe||maybe|maybe||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/534.4 (KHTML, like Gecko) Chrome/6.0.477.0 Safari/534.4", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.125 Safari/533.4", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.0; fr; rv:1.9.1.11) Gecko/20100701 Firefox/3.5.11 (.NET CLR 3.5.30729)", "gecko1_8", "||||||maybe|maybe||maybe|maybe||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; pl; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8 ( .NET CLR 3.5.30729)", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.99 Safari/533.4", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_8; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.99 Safari/533.4", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.1.11) Gecko/20100701 Firefox/3.5.11", "gecko1_8", "||||||maybe|maybe||maybe|maybe||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.4 (KHTML, like Gecko) Chrome/6.0.478.0 Safari/534.4", "safari", "||||||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-GB; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8 ( .NET CLR 3.5.30729; .NET4.0C)", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.125 Safari/533.4", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US) AppleWebKit/533.16 (KHTML, like Gecko) Version/5.0 Safari/533.16", "safari", "maybe|maybe|maybe|probably|maybe|maybe|maybe|maybe||probably|probably||probably|probably||probably|probably|||||");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_4; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.4 Safari/534.3", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_0 like Mac OS X; en-us) AppleWebKit/532.9 (KHTML, like Gecko) Version/4.0.5 Mobile/8A293 Safari/6531.22.7", "safari", "probably|probably|probably|maybe|probably||probably|probably||maybe|maybe||maybe|maybe||maybe|maybe|||||");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.6) Gecko/20100625 Firefox/3.6.6",
        "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_4; de-de) AppleWebKit/533.16 (KHTML, like Gecko) Version/5.0 Safari/533.16", "safari", "maybe|maybe|maybe|probably|||maybe|||probably|||probably|||probably||||||");
    incrementTestResultCount("Opera/9.80 (X11; Linux x86_64; U; de) Presto/2.6.30 Version/10.60",
        "opera",
        "||||||maybe|maybe|||||probably|probably||probably|probably||maybe|probably|probably|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.3) Gecko/20100401 Firefox/3.6.3",
        "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/534.1 (KHTML, like Gecko) Chrome/6.0.444.0 Safari/534.1", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8 GTB7.1", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; en-US; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.99 Safari/533.4", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; GTB5; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; InfoPath.2; .NET CLR 3.5.30729; .NET CLR 3.0.30618; msn OptimizedIE8;ENUS; AskTbBLT/5.8.0.12304)", "ie8", "|||||||||||||||||||||");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_3; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.99 Safari/533.4", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_4; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.125 Safari/533.4", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux x86_64; de; rv:1.9.2.8) Gecko/20100723 Ubuntu/10.04 (lucid) Firefox/3.6.8", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.6) Gecko/20100625 Firefox/3.6.6 ( .NET CLR 3.5.30729)", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.1.3) Gecko/20091020 Ubuntu/9.10 (karmic) Firefox/3.5.3", "gecko1_8", "||||||maybe|maybe||maybe|maybe||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux i686 (x86_64); en-US; rv:1.9.1.11) Gecko/20100701 Firefox/3.5.11", "gecko1_8", "||||||maybe|maybe||maybe|maybe||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.1.8) Gecko/20100214 Ubuntu/9.10 (karmic) Firefox/3.5.8 GTB7.1", "gecko1_8", "||||||maybe|maybe||maybe|maybe||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; de; rv:1.9.2.7) Gecko/20100713 Firefox/3.6.7", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.1; ru; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8",
        "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.86 Safari/533.4", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount("Opera/9.80 (Windows NT 5.1; U; nl) Presto/2.6.30 Version/10.60",
        "opera", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.7) Gecko/20100716 Ubuntu/10.04 (lucid) Firefox/3.6.7", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.1; fr; rv:1.9.2.6) Gecko/20100625 Firefox/3.6.6",
        "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; GTB6.5; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.1)", "ie8", "|||||||||||||||||||||");
    incrementTestResultCount(
        "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)", "ie8", "|||||||||||||||||||||");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.1; de; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8 (.NET CLR 3.5.30729)", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.5; en-US; rv:1.9.2.6) Gecko/20100625 Firefox/3.6.6", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux x86_64; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.125 Safari/533.4", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_4; ru-ru) AppleWebKit/533.16 (KHTML, like Gecko) Version/5.0 Safari/533.16", "safari", "maybe|maybe|maybe|probably|||maybe|||probably|||probably|||probably||||||");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.1.3) Gecko/20091020 Ubuntu/9.10 (karmic) Firefox/3.5.3", "gecko1_8", "||||||maybe|maybe||maybe|maybe||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; es-ES; rv:1.9.2.8) Gecko/20100722 AskTbDGY/3.8.0.12304 Firefox/3.6.8 ( .NET CLR 3.5.30729)", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; de; rv:1.9.2.7) Gecko/20100713 Firefox/3.6.7",
        "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Linux; U; Android 2.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1", "safari", "|||||||||||||||||||||");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux x86_64; en-US) AppleWebKit/533.2 (KHTML, like Gecko) Chrome/5.0.342.9 Safari/533.2", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.2.7) Gecko/20100722 Gentoo Firefox/3.6.7",
        "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; GTB6.5; .NET CLR 2.0.50727; .NET CLR 1.1.4322; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)", "ie6", "|||||||||||||||||||||");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_8; en-us) AppleWebKit/534.3+ (KHTML, like Gecko) Version/5.0 Safari/533.16", "safari", "maybe|maybe|maybe|probably|||maybe|||probably|||probably|||probably||||||");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_8; en-US) AppleWebKit/534.0 (KHTML, like Gecko) Chrome/6.0.408.1 Safari/534.0", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; it; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount("Opera/9.80 (Windows NT 6.0; U; en) Presto/2.6.30 Version/10.60",
        "opera", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_4; en-us) AppleWebKit/533.16 (KHTML, like Gecko) Version/5.0 Safari/533.16", "safari", "maybe|maybe|maybe|probably|||maybe|||probably|||probably|||probably|||maybe|probably|probably|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.125 Safari/533.4", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-GB; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8 (.NET CLR 3.5.30729)", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; fr; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8 ( .NET CLR 3.5.30729)", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_4; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.11 Safari/534.3", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.0 Safari/534.3", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.2.8) Gecko/20100723 Ubuntu/10.04 (lucid) Firefox/3.6.8", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8",
        "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_8; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.125 Safari/533.4", "safari", "|maybe|maybe|probably|||||||||||||||maybe|maybe|maybe|probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 6.0; de; rv:1.9.1.11) Gecko/20100701 Firefox/3.5.11 ( .NET CLR 3.5.30729)", "gecko1_8", "||||||maybe|maybe||maybe|maybe||probably|probably|||||maybe|||probably");
    incrementTestResultCount(
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; de; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8 (.NET CLR 3.5.30729)", "gecko1_8", "||||||maybe|maybe|||||probably|probably|||||maybe|||probably");
  }

}

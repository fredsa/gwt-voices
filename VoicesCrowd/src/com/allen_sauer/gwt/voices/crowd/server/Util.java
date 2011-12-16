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

import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailService.Message;
import com.google.appengine.api.mail.MailServiceFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import com.allen_sauer.gwt.voices.crowd.shared.TestResultSummary;
import com.allen_sauer.gwt.voices.crowd.shared.TestResults;
import com.allen_sauer.gwt.voices.crowd.shared.UserAgent;
import com.allen_sauer.gwt.voices.crowd.shared.UserAgentSummary;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class Util {

  private static final Logger logger = Logger.getLogger(Util.class.getName());

  private static final int BUFFER_SIZE = 4096;

  public static TestResultSummary incrementTestResultCount(PersistenceManager pm,
      UserAgent userAgent, String gwtUserAgent, TestResults testResults) throws IOException {
    logger.log(Level.INFO, "incrementTestResultCount(pm, " + userAgent + ", " + gwtUserAgent + ", "
        + testResults + ")");
    UserAgentSummary userAgentSummary = lookupPrettyUserAgent(pm, userAgent.toString(),
        gwtUserAgent);
    logger.log(Level.INFO, "userAgentSummary=" + userAgentSummary);

    Objectify ofy = ObjectifyService.begin();
    com.googlecode.objectify.Query<TestResultSummary> q = ofy.query(TestResultSummary.class);
    q.filter("userAgent", userAgent.toString());
    q.filter("gwtUserAgent", gwtUserAgent);
    q.filter("results", testResults.toString());
    List<TestResultSummary> summaryList = q.list();
    logger.log(Level.INFO, "summaryList.size()=" + summaryList.size());

    TestResultSummary summary;

    if (summaryList.isEmpty()) {
      summary = new TestResultSummary(userAgent, userAgentSummary.getPrettyUserAgent(),
          gwtUserAgent, testResults);
      String subject = "new user-agent";
      String msg = "TestResultSummary=" + summary;
      sendEmail(subject, msg);
    } else {
      summary = summaryList.get(0);

      if (summaryList.size() > 1) {
        // merge rows in case race condition caused > 1 row to be inserted
        TestResultSummary anotherSummary = summaryList.get(1);
        summary.incrementCount(anotherSummary.getCount());
        ofy.delete(anotherSummary);
      }

      // count the current test results
      summary.incrementCount(1);

      if (summary.getPrettyUserAgent() == null) {
        summary.setPrettyUserAgent(lookupPrettyUserAgent(pm, userAgent.toString(), gwtUserAgent).getPrettyUserAgent());
      }
    }

    ofy.put(summary);
    return summary;
  }

  public static void sendEmail(String subject, String messageText) {
    try {
      MailService ms = MailServiceFactory.getMailService();
      Message message = new Message();
      message.setSender("Fred Sauer <fredsa@gmail.com>");
      message.setSubject("{gwt-voices} " + subject);
      message.setTextBody(messageText);
      ms.sendToAdmins(message);
    } catch (Exception e) {
      logger.log(Level.SEVERE, "failed to send email", e);
    }
  }

  /**
   * Lookup a human readable user agent.
   * 
   * @param pm persistence manager
   * @param userAgentString raw user agent string
   * @param gwtUserAgent GWT {code user.agent} property value
   * @return human readable user agent
   * @throws IOException maybe
   */
  public static UserAgentSummary lookupPrettyUserAgent(PersistenceManager pm,
      String userAgentString, String gwtUserAgent) throws IOException {
    MemcacheService mc = MemcacheServiceFactory.getMemcacheService();

    UserAgentSummary userAgentSummary = null;
    // Get info out of memcache
    try {
      userAgentSummary = (UserAgentSummary) mc.get(userAgentString);
    } catch (RuntimeException e) {
      logger.log(Level.WARNING,
          "Exception getting value from memcache; possible serialVersionUID issue", e);
    }

    if (userAgentSummary != null) {
      return userAgentSummary;
    } else {

      // Next, try the datastore
      userAgentSummary = lookupUserAgentInDatastore(pm, userAgentString);

      // Next, browserscope it
      if (userAgentSummary == null) {
        try {
          userAgentSummary = lookupUserAgentInBrowserScope(userAgentString, gwtUserAgent);
        } catch (Exception e) {
          logger.log(Level.SEVERE, "Unable to lookup new user agent in BrowserScope: "
              + userAgentString, e);
          userAgentSummary = new UserAgentSummary(userAgentString, null, gwtUserAgent);
        }
      }

      // Store result in memcache
      if (userAgentSummary != null) {
        if (userAgentSummary.getPrettyUserAgent() != null) {
          pm.makePersistent(userAgentSummary);
        }
        mc.put(userAgentString, userAgentSummary);
      }
    }
    return userAgentSummary;
  }

  /**
   * Lookup a human readable user agent in the datastore.
   * 
   * @param pm persistence manager
   * @param userAgentString raw user agent string
   * @return human readable user agent or {@code null}
   */
  public static UserAgentSummary lookupUserAgentInDatastore(PersistenceManager pm,
      String userAgentString) {
    Query query = pm.newQuery(UserAgentSummary.class, "userAgentString == userAgentStringParam");
    query.declareParameters("String userAgentStringParam");
    @SuppressWarnings("unchecked")
    List<UserAgentSummary> uaList = (List<UserAgentSummary>) query.execute(userAgentString);
    if (!uaList.isEmpty()) {
      return uaList.get(0);
    }
    return null;
  }

  /**
   * Lookup a human readable user agent via browserscope.
   * 
   * @param userAgentString raw user agent string
   * @param gwtUserAgent GWT {code user.agent} property value
   * @return human readable user agent
   * @throws MalformedURLException maybe
   * @throws IOException maybe
   * @throws UnsupportedEncodingException maybe
   */
  public static UserAgentSummary lookupUserAgentInBrowserScope(String userAgentString,
      String gwtUserAgent) throws MalformedURLException, IOException, UnsupportedEncodingException {
    URL url = new URL("http://www.browserscope.org/");
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestProperty("User-Agent", userAgentString);

    ByteArrayInputStream content = (ByteArrayInputStream) connection.getContent();

    byte[] buffer = new byte[BUFFER_SIZE];
    ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
    try {
      while (true) {
        int byteCount = content.read(buffer);
        if (byteCount == -1) {
          break;
        }
        out.write(buffer, 0, byteCount);
      }
      String pageContent = out.toString("UTF-8");
      Pattern pattern = Pattern.compile("var userAgentPretty = '(.*?)';");
      Matcher matcher = pattern.matcher(pageContent);
      if (matcher.find()) {
        String prettyUserAgent = matcher.group(1);
        return new UserAgentSummary(userAgentString, prettyUserAgent, gwtUserAgent);
      } else {
        return null;
      }
    } finally {
      if (content != null) {
        content.close();
      }
    }
  }

}

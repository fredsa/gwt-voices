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

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.utils.SystemProperty;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import com.allen_sauer.gwt.voices.crowd.client.ResultsService;
import com.allen_sauer.gwt.voices.crowd.shared.TestResultSummary;
import com.allen_sauer.gwt.voices.crowd.shared.TestResults;
import com.allen_sauer.gwt.voices.crowd.shared.UserAgent;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("serial")
public class ResultsServiceImpl extends RemoteServiceServlet implements ResultsService {

  private static final Logger logger = Logger.getLogger(ResultsServiceImpl.class.getName());

  public HashMap<UserAgent, TestResults> getResults() {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      return getResultsImpl(pm);
    } catch (Exception ex) {
      Logger.getAnonymousLogger().log(Level.SEVERE, "Unexpected exception retrieving results", ex);
      return null;
    } finally {
      pm.close();
    }
  }

  public List<TestResultSummary> getSummary() {
    try {
      Objectify ofy = ObjectifyService.begin();
      return ofy.query(TestResultSummary.class).chunkSize(1000).list();
    } catch (Throwable ex) {
      Logger.getAnonymousLogger().log(Level.SEVERE, "Unexpected exception retrieving summary data",
          ex);
      Util.sendEmail("getSummary() threw an exception", ex.toString());
      throw new RuntimeException(ex);
    }
  }

  public TestResultSummary storeResults(UserAgent userAgent, String gwtUserAgent,
      TestResults results) {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      return storeResultsImpl(pm, userAgent, gwtUserAgent, results);
    } catch (Throwable ex) {
      Logger.getAnonymousLogger().log(Level.SEVERE, "Unexpected exception storing results", ex);
      Util.sendEmail("storeResults() threw an exception", ex.toString());
      throw new RuntimeException(ex);
    } finally {
      pm.close();
    }
  }

  private int getExpiration() {
    if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development) {
      // 5 seconds
      return 5;
    } else {
      // 5 minutes
      return 300;
    }
  }

  private HashMap<UserAgent, TestResults> getResultsImpl(PersistenceManager pm) {
    HashMap<UserAgent, TestResults> map = new HashMap<UserAgent, TestResults>();
    Objectify ofy = ObjectifyService.begin();
    List<TestResultSummary> summaryList = ofy.query(TestResultSummary.class).chunkSize(1000).list();
    for (TestResultSummary summary : summaryList) {
      TestResults testResults = summary.getTestResults();
      map.put(new UserAgent(summary.getUserAgent()), testResults);
    }
    return map;
  }

  private TestResultSummary storeResultsImpl(PersistenceManager pm, UserAgent userAgent,
      String gwtUserAgent, TestResults testResults) throws IOException {
    MemcacheService mc = MemcacheServiceFactory.getMemcacheService();

    HttpServletRequest request = getThreadLocalRequest();
    String addr = request.getRemoteAddr();
    String memcacheThrottleKey = addr + "/" + userAgent;
    TestResultSummary s = null;
    try {
      s = (TestResultSummary) mc.get(memcacheThrottleKey);
    } catch (RuntimeException e) {
      logger.log(Level.WARNING,
          "Exception getting value from memcache; possible serialVersionUID issue", e);
    }
    if (s != null) {
      return s;
    }
    TestResultSummary testResultSummary = Util.incrementTestResultCount(pm, userAgent,
        gwtUserAgent, testResults);
    mc.put(memcacheThrottleKey, testResultSummary, Expiration.byDeltaSeconds(getExpiration()));
    return testResultSummary;
  }
}

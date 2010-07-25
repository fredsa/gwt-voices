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

import com.allen_sauer.gwt.voices.crowd.client.ResultsService;
import com.allen_sauer.gwt.voices.crowd.shared.TestResultSummary;
import com.allen_sauer.gwt.voices.crowd.shared.TestResults;
import com.allen_sauer.gwt.voices.crowd.shared.UserAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("serial")
public class ResultsServiceImpl extends RemoteServiceServlet implements ResultsService {

  public boolean storeResults(UserAgent userAgent, String gwtUserAgent, TestResults results) {
    try {
      return storeResultsImpl(userAgent, gwtUserAgent, results);
    } catch (Exception ex) {
      Logger.getAnonymousLogger().log(Level.SEVERE, "Unexpected exception storing results", ex);
      return false;
    }
  }

  private boolean storeResultsImpl(
      UserAgent userAgent, String gwtUserAgent, TestResults testResults) {
    MemcacheService ms = MemcacheServiceFactory.getMemcacheService();

    HttpServletRequest request = getThreadLocalRequest();
    String addr = request.getRemoteAddr();
    String memcacheThrottleKey = addr + "/" + userAgent;
    if (ms.contains(memcacheThrottleKey)) {
      return false;
    }
    ms.put(memcacheThrottleKey, null, Expiration.byDeltaSeconds(getExpiration()));
    incrementTestResultCount(userAgent, gwtUserAgent, testResults);
    return true;
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

  private void incrementTestResultCount(
      UserAgent userAgent, String gwtUserAgent, TestResults testResults) {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      Query query = pm.newQuery(TestResultSummary.class);
      query.setFilter(
          "userAgent == userAgentParam && gwtUserAgent == gwtUserAgentParam && results == resultsParam");
      query.declareParameters(
          "String userAgentParam, String gwtUserAgentParam, String resultsParam");
      List<TestResultSummary> summaryList = (List<TestResultSummary>) query.execute(
          userAgent.toString(), gwtUserAgent, testResults.toString());
      TestResultSummary summary;

      if (summaryList.isEmpty()) {
        summary = new TestResultSummary(userAgent, gwtUserAgent, testResults);
      } else {
        summary = summaryList.get(0);

        if (summaryList.size() > 1) {
          // merge rows in case race condition caused > 1 row to be inserted
          TestResultSummary anotherSummary = summaryList.get(1);
          summary.incrementCount(anotherSummary.getCount());
          pm.deletePersistent(anotherSummary);
        }

        // count the current test results
        summary.incrementCount(1);
      }
      pm.makePersistent(summary);
    } finally {
      pm.close();
    }
  }

  public HashMap<UserAgent, TestResults> getResults() {
    try {
      return getResultsImpl();
    } catch (Exception ex) {
      Logger.getAnonymousLogger().log(Level.SEVERE, "Unexpected exception retrieving results", ex);
      return null;
    }
  }

  private HashMap<UserAgent, TestResults> getResultsImpl() {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      HashMap<UserAgent, TestResults> map = new HashMap<UserAgent, TestResults>();
      List<TestResultSummary> summaryList = (List<TestResultSummary>) pm.newQuery(
          TestResultSummary.class).execute();
      for (TestResultSummary summary : summaryList) {
        TestResults testResults = summary.getTestResults();
        map.put(new UserAgent(summary.getUserAgent()), testResults);
      }
      return map;
    } finally {
      pm.close();
    }
  }

  public List<TestResultSummary> getSummary() {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      List<TestResultSummary> results = (List<TestResultSummary>) pm.newQuery(
          TestResultSummary.class).execute();
      return new ArrayList<TestResultSummary>(results);
    } finally {
      pm.close();
    }
  }

}

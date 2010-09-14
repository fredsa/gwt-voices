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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("serial")
public class ResultsServiceImpl extends RemoteServiceServlet implements ResultsService {

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
    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      List<TestResultSummary> results = (List<TestResultSummary>) pm.newQuery(
          TestResultSummary.class).execute();
      return new ArrayList<TestResultSummary>(results);
    } finally {
      pm.close();
    }
  }

  public boolean storeResults(UserAgent userAgent, String gwtUserAgent, TestResults results) {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      return storeResultsImpl(pm, userAgent, gwtUserAgent, results);
    } catch (Exception ex) {
      Logger.getAnonymousLogger().log(Level.SEVERE, "Unexpected exception storing results", ex);
      return false;
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
    List<TestResultSummary> summaryList = (List<TestResultSummary>) pm.newQuery(
        TestResultSummary.class).execute();
    for (TestResultSummary summary : summaryList) {
      TestResults testResults = summary.getTestResults();
      map.put(new UserAgent(summary.getUserAgent()), testResults);
    }
    return map;
  }

  private boolean storeResultsImpl(
      PersistenceManager pm, UserAgent userAgent, String gwtUserAgent, TestResults testResults)
      throws IOException {
    MemcacheService mc = MemcacheServiceFactory.getMemcacheService();

    HttpServletRequest request = getThreadLocalRequest();
    String addr = request.getRemoteAddr();
    String memcacheThrottleKey = addr + "/" + userAgent;
    if (mc.contains(memcacheThrottleKey)) {
      return false;
    }
    mc.put(memcacheThrottleKey, null, Expiration.byDeltaSeconds(getExpiration()));
    Util.incrementTestResultCount(pm, userAgent, gwtUserAgent, testResults);
    return true;
  }

}

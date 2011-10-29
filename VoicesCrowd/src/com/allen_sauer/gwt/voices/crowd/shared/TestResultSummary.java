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
package com.allen_sauer.gwt.voices.crowd.shared;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@SuppressWarnings("serial")
@PersistenceCapable
public class TestResultSummary implements Serializable {
  protected TestResultSummary() {
  }

  @Persistent
  private String userAgent;

  @Persistent
  private String results;

  @Persistent
  private int count = 1;

  @SuppressWarnings("unused")
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Long key;

  @Persistent
  private String gwtUserAgent;

  @Persistent
  private String prettyUserAgent;

  public TestResultSummary(
      String userAgent, String prettyUserAgent, String gwtUserAgent, String results) {
    this.userAgent = userAgent;
    this.setPrettyUserAgent(prettyUserAgent);
    this.gwtUserAgent = gwtUserAgent;
    this.results = results;
  }

  public TestResultSummary(
      UserAgent userAgent, String prettyUserAgent, String gwtUserAgent, TestResults testResults) {
    this.setPrettyUserAgent(prettyUserAgent);
    this.gwtUserAgent = gwtUserAgent;
    this.userAgent = userAgent.toString();
    this.results = testResults.toString();
  }

  public int getCount() {
    return count;
  }

  public void incrementCount(int increment) {
    count += increment;
  }

  public String getUserAgent() {
    return userAgent;
  }

  public TestResults getTestResults() {
    return new TestResults(results);
  }

  public String getGwtUserAgent() {
    return gwtUserAgent;
  }

  public String getPrettyUserAgent() {
    return prettyUserAgent;
  }

  public void setPrettyUserAgent(String prettyUserAgent) {
    this.prettyUserAgent = prettyUserAgent;
  }

  @Override
  public String toString() {
    return "TestResultSummary(count=" + count + "; prettyUserAgent=" + prettyUserAgent
        + "; gwtUserAgent=" + gwtUserAgent + "; userAgent=" + userAgent + "; results=" + results
        + ")";
  }
}

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

@PersistenceCapable
public class UserAgentSummary implements Serializable {
  @Persistent
  private String gwtUserAgent;

  @Persistent
  private String prettyUserAgent;

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private String userAgentString;

  public UserAgentSummary(String userAgentString, String prettyUserAgent, String gwtUserAgent) {
    this.userAgentString = userAgentString;
    this.prettyUserAgent = prettyUserAgent;
    this.gwtUserAgent = gwtUserAgent;
  }

  protected UserAgentSummary() {
  }

  public String getGwtUserAgent() {
    return gwtUserAgent;
  }

  public String getPrettyUserAgent() {
    return prettyUserAgent;
  }

  public String getUserAgentString() {
    return userAgentString;
  }

  @Override
  public String toString() {
    return "UserAgentSummary(prettyUserAgent=" + prettyUserAgent + ", gwtUserAgent=" + gwtUserAgent
        + ", userAgentString=" + userAgentString + ")";
  }
}

/*
 * Copyright 2011 Fred Sauer
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

import com.google.appengine.tools.appstats.AppstatsFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class VoicesAppstatsFilter extends AppstatsFilter {
  /**
  * Appstats base path, which must match the init-param expected by {@link AppstatsFilter}].
  */
  private static final String BASE_PATH = "basePath";
  private String basePath;

  @Override
  public synchronized void init(FilterConfig config) {
    super.init(config);
    basePath = config.getInitParameter(BASE_PATH);
    if (basePath == null) {
      throw new RuntimeException(VoicesAppstatsFilter.class.getName() + " init-param '" + BASE_PATH
          + "' is required");
    }
    
    // Remove trailing slash
    basePath = basePath.replaceFirst("/$", "");
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filters)
      throws IOException, ServletException {
    HttpServletRequest hreq = (HttpServletRequest) req;
    if (!hreq.getRequestURI().startsWith(basePath)) {
      // allow AppstatsFilter to capture the request
      super.doFilter(req, resp, filters);
    } else {
      // skip AppstatsFilter for Appstats' own UI
      filters.doFilter(req, resp);
    }
  }
}

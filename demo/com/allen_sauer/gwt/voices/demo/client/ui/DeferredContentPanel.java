/*
 * Copyright 2008 Fred Sauer
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
package com.allen_sauer.gwt.voices.demo.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

// CHECKSTYLE_JAVADOC_OFF
public abstract class DeferredContentPanel extends Composite {
  public DeferredContentPanel() {
    SimplePanel simplePanel = new SimplePanel();
    initWidget(simplePanel);
    simplePanel.setWidget(new HTML(
        "<div class='demo-loading'><img src='images/preview61_cycle_eight_16-black-on-white.gif'>"
            + "Loading. Please wait...</div>"));
  }

  public abstract Panel initContent();
}

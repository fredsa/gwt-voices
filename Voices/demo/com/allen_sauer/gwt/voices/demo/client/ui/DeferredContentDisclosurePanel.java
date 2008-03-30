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

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosureEvent;
import com.google.gwt.user.client.ui.DisclosureHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Panel;

// CHECKSTYLE_JAVADOC_OFF
public class DeferredContentDisclosurePanel extends Composite {
  private static final String CSS_DEMO_CONTENT = "demo-content";

  public DeferredContentDisclosurePanel(String html, final DeferredContentPanel deferredContentPanel) {
    final DisclosurePanel realDisclosurePanel = new DisclosurePanel(html);
    realDisclosurePanel.setContent(deferredContentPanel);

    realDisclosurePanel.addEventHandler(new DisclosureHandler() {
      public void onClose(DisclosureEvent event) {
      }

      public void onOpen(DisclosureEvent event) {
        DeferredCommand.addCommand(new Command() {
          public void execute() {
            Panel panel = deferredContentPanel.initContent();
            panel.addStyleName(CSS_DEMO_CONTENT);
            realDisclosurePanel.setContent(panel);
            removeEventHandler();
          }
        });
      }

      private void removeEventHandler() {
        realDisclosurePanel.removeEventHandler(this);
      }
    });
    initWidget(realDisclosurePanel);
  }
}

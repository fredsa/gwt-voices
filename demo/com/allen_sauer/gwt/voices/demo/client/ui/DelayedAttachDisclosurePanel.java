/*
 * Copyright 2007 Fred Sauer
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
import com.google.gwt.user.client.ui.Widget;

public class DelayedAttachDisclosurePanel extends Composite {
  public DelayedAttachDisclosurePanel(String html, final Widget content) {
    final DisclosurePanel realDisclosurePanel = new DisclosurePanel(html);
    realDisclosurePanel.addEventHandler(new DisclosureHandler() {
      public void onClose(DisclosureEvent event) {
      }

      public void onOpen(DisclosureEvent event) {
        realDisclosurePanel.add(content);
        DeferredCommand.addCommand(new Command() {
          public void execute() {
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

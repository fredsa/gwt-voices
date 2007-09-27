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
package com.allen_sauer.gwt.voices.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import com.allen_sauer.gwt.log.client.LogUncaughtExceptionHandler;
import com.allen_sauer.gwt.voices.demo.client.embed.EmbedDemo;
import com.allen_sauer.gwt.voices.demo.client.flash.FlashDemo;
import com.allen_sauer.gwt.voices.demo.client.ui.DelayedAttachDisclosurePanel;

public class VoicesDemo implements EntryPoint {
  private static final String DEMO_EVENT_TEXT_AREA = "demo-event-text-area";
  private static final String DEMO_PANELS = "demo-panels";

  public void onModuleLoad() {
    // set uncaught exception handler
    GWT.setUncaughtExceptionHandler(new LogUncaughtExceptionHandler());

    // use deferred command to catch initialization exceptions
    DeferredCommand.addCommand(new Command() {
      public void execute() {
        onModuleLoad2();
      }
    });
  }

  public void onModuleLoad2() {
    // text area to log sound events as they are triggered
    final HTML eventTextArea = new HTML();
    RootPanel.get(DEMO_EVENT_TEXT_AREA).add(eventTextArea);

    DemoSoundHandler demoSoundHandler = new DemoSoundHandler(eventTextArea);

    DelayedAttachDisclosurePanel flashDisclosurePanel = new DelayedAttachDisclosurePanel(
        "Flash Bridge Demo", new FlashDemo(demoSoundHandler));
    DelayedAttachDisclosurePanel embedDisclosurePanel = new DelayedAttachDisclosurePanel(
        "Embed Demo (using BGSOUND/OBJECT)", new EmbedDemo(demoSoundHandler));

    DOM.setInnerHTML(RootPanel.get(DEMO_PANELS).getElement(), "");
    RootPanel.get(DEMO_PANELS).add(flashDisclosurePanel);
    RootPanel.get(DEMO_PANELS).add(embedDisclosurePanel);
  }
}

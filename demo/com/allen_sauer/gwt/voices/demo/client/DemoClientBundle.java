package com.allen_sauer.gwt.voices.demo.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.Strict;
import com.google.gwt.user.client.ui.DisclosurePanel;

public interface DemoClientBundle extends ClientBundle {

  interface DemoCssResource extends CssResource {

    /**
     * 
     */
    String content();

    @ClassName("demo-content")
    String demoContent();

    @ClassName("demo-event-text-area")
    String demoEventTextArea();

    @ClassName("demo-main-panel")
    String demoMainPanel();

    @ClassName("demo-note")
    String demoNote();

    @ClassName("demo-SupportedMimeTypeSummary-table")
    String demoSupportedMimeTypeSummaryTable();

    @ClassName("demo-user-agent")
    String demoUserAgent();

    /**
     * Defined as a constant in {@link DisclosurePanel}
     */
    String even();

    /**
     * Defined as a constant in {@link DisclosurePanel}
     */
    String header();

    /**
     * Defined as a constant in {@link DisclosurePanel}
     */
    String odd();
  }

  DemoClientBundle INSTANCE = GWT.create(DemoClientBundle.class);

  @Strict
  @Source("VoicesDemo.css")
  DemoCssResource css();
}

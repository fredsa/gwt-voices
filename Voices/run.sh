#!/bin/bash
#
set -ue

classpath="$GWT_HOME/gwt-dev.jar:$GWT_HOME/gwt-user.jar:$GWT_HOME/gwt-codeserver.jar:$GWT_HOME/validation-api-1.0.0.GA.jar:$GWT_HOME/validation-api-1.0.0.GA-sources.jar"
java -cp "$classpath" \
  com.google.gwt.dev.codeserver.CodeServer \
  -src src \
  -src demo \
  com.allen_sauer.gwt.voices.demo.VoicesDemo \
  $*

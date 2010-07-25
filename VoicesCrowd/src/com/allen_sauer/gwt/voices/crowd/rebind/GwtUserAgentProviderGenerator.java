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
package com.allen_sauer.gwt.voices.crowd.rebind;

import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.SelectionProperty;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import java.io.PrintWriter;

/**
 * Generator for GwtUserAgentProvider interface.
 */
public class GwtUserAgentProviderGenerator extends Generator {
  // CHECKSTYLE_JAVADOC_OFF
  private static final String PROPERTY_USER_AGENT = "user.agent";

  @Override
  public String generate(TreeLogger logger, GeneratorContext context, String typeName)
      throws UnableToCompleteException {
    TypeOracle typeOracle = context.getTypeOracle();

    JClassType userType;
    try {
      userType = typeOracle.getType(typeName);
    } catch (NotFoundException e) {
      logger.log(TreeLogger.ERROR, "OOPS", e);
      throw new UnableToCompleteException();
    }
    String packageName = userType.getPackage().getName();
    String className = userType.getName();

    JClassType remoteService = typeOracle.findType(typeName);
    if (remoteService == null) {
      logger.log(TreeLogger.ERROR, "Unable to find metadata for type '" + typeName + "'", null);
      throw new UnableToCompleteException();
    }

    if (remoteService.isInterface() == null) {
      logger.log(TreeLogger.ERROR, remoteService.getQualifiedSourceName() + " is not an interface",
          null);
      throw new UnableToCompleteException();
    }
    ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(
        packageName, className + "Impl");
    composerFactory.addImplementedInterface(remoteService.getQualifiedSourceName());

    PrintWriter pw = context.tryCreate(logger, packageName, className + "Impl");
    if (pw != null) {
      SourceWriter sw = composerFactory.createSourceWriter(context, pw);

      PropertyOracle propertyOracle = context.getPropertyOracle();
      String userAgent;
      try {
        SelectionProperty userAgentProperty =
            propertyOracle.getSelectionProperty(logger, PROPERTY_USER_AGENT);
        
        // ALWAYS RETURNS 'gecko'
        userAgent = userAgentProperty.getCurrentValue();
      } catch (BadPropertyValueException e) {
        logger.log(TreeLogger.ERROR, "Unable to find value for '" + PROPERTY_USER_AGENT + "'", e);
        throw new UnableToCompleteException();
      }

      sw.println();
      sw.println("public String getGwtUserAgent() {");
      sw.indent();

      if (userAgent == null) {
        sw.println("return null;");
      } else {
        sw.println("return \"" + userAgent + "\";");
      }

      sw.outdent();
      sw.println("}\n");

      sw.commit(logger);
    }
    return composerFactory.getCreatedClassName();
  }
}
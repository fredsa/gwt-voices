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
package com.allen_sauer.gwt.voices.client.util.impl;

import com.google.gwt.user.client.Element;

/**
 * {@link com.allen_sauer.gwt.voices.client.util.DOMUtil} implementation for
 * Opera.
 */
public class DOMUtilImplOpera extends DOMUtilImplStandard {
  public native Element createSoundElement(String url)
  /*-{
    var elem = $doc.createElement("object");
    elem.setAttribute("data", url);
    elem.setAttribute("autostart", "true");
    elem.setAttribute("hidden", "false");
    return elem;
  }-*/;

  public native void setSoundElementVolume(Element elem, int volume)
  /*-{
    var children = elem.childNodes;
    for (var i = 0; i < children.length; i++) {
      if (children[i].name == "volume") {
        children[i].value = "" + volume;
        return;
      }
    }
    var param = $doc.createElement("param");
    param.setAttribute("name", "volume");
    param.setAttribute("value", "" + volume);
    elem.appendChild(param);
  }-*/;
}

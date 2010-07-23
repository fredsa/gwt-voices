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

public class MimeType implements Serializable, Comparable<MimeType> {

  private String mimeTypeString;

  protected MimeType() {
  }

  public MimeType(String mimeTypeString) {
    this.mimeTypeString = mimeTypeString;
  }

  @Override
  public String toString() {
    return mimeTypeString;
  }

  @Override
  public int hashCode() {
    return mimeTypeString.hashCode();
  }

  @Override
  public int compareTo(MimeType o) {
    return mimeTypeString.compareTo(o.mimeTypeString);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof MimeType) {
      return mimeTypeString.equals(((MimeType) obj).mimeTypeString);
    }
    return false;
  }
}

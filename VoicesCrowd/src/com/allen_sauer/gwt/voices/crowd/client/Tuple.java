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
package com.allen_sauer.gwt.voices.crowd.client;

public class Tuple<E> {
  private static boolean bothNullOrEquals(Object a, Object b) {
    if (a == null) {
      return b == null;
    } else {
      return a.equals(b);
    }
  }

  private final E[] elements;

  Tuple(E... elements) {
    this.elements = elements;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj instanceof Tuple) {
      Tuple o = (Tuple) obj;
      for (int i = 0; i < elements.length; i++) {
        if (!bothNullOrEquals(elements[i], o.elements[i])) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  public E[] getElements() {
    return elements;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  @Override
  public String toString() {
    StringBuffer t = new StringBuffer();
    for (E e : elements) {
      if (t.length() != 0) {
        t.append(",");
      }
      t.append(e);
    }
    t.insert(0, "(");
    t.append(")");
    return t.toString();
  }
}

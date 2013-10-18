/**
 * Copyright (C) 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jbpm.formModeler.core.processing.fieldHandlers;

import org.jbpm.formModeler.core.processing.DefaultFieldHandler;
import org.jbpm.formModeler.api.model.Field;

import javax.inject.Named;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler for input text
 */
@Named("org.jbpm.formModeler.core.processing.fieldHandlers.InputTextFieldHandler")
public class InputTextFieldHandler extends DefaultFieldHandler {

    private String pageToIncludeForRendering = "/formModeler/fieldHandlers/InputText/input.jsp";
    private String pageToIncludeForDisplaying = "/formModeler/fieldHandlers/InputText/show.jsp";
    private String pageToIncludeForSearching = "/formModeler/fieldHandlers/InputText/search.jsp";

    /**
     * Determine the list of class types this field can generate. That is, normally,
     * a field can generate multiple outputs (an input text can generate Strings,
     * Integers, ...)
     *
     * @return the set of class types that can be generated by this handler.
     */
    public String[] getCompatibleClassNames() {
        return new String[]{String.class.getName()};
    }

    /**
     * Read a parameter value (normally from a request), and translate it to
     * an object with desired class (that must be one of the returned by this handler)
     *
     * @return a object with desired class
     * @throws Exception
     */
    public Object getValue(Field field, String inputName, Map parametersMap, Map filesMap, String desiredClassName, Object previousValue) throws Exception {
        String[] paramValue = (String[]) parametersMap.get(inputName);
        if (paramValue == null || paramValue.length == 0) return null;
        String expr = "";

        if (field != null)
            expr = field.getFieldPattern();

        if (!"".equals(paramValue[0]) && expr != null && !"".equals(expr) && !paramValue[0].matches(expr))
                throw new IllegalArgumentException("Parameter does not match the pattern");
        return paramValue[0];
    }

    /**
     * Determine the value as a parameter map for a given input value. This is like the inverse operation of getValue()
     *
     * @param objectValue Object value to represent
     * @param pattern     Pattern to apply if any
     * @return a Map representing the parameter values expected inside a request that would cause the form
     *         to generate given object value as a result.
     */
    public Map getParamValue(String inputName, Object objectValue, String pattern) {
        if (objectValue == null) return Collections.EMPTY_MAP;
        Map m = new HashMap();
        m.put(inputName, new String[]{objectValue.toString()});
        return m;
    }


    /**
     * When rendering a form, if field is handled by this handler, determine the
     * page that renders the displaying of the value
     *
     * @return a page to include
     */
    public String getPageToIncludeForDisplaying() {
        return pageToIncludeForDisplaying;
    }

    public boolean isEmpty(Object value) {
        return value == null || "".equals(value);
    }

    public void setPageToIncludeForDisplaying(String pageToIncludeForDisplaying) {
        this.pageToIncludeForDisplaying = pageToIncludeForDisplaying;
    }

    /**
     * When rendering a form, if field is handled by this handler, determine the
     * page that renders the input(s)
     *
     * @return a page to include
     */
    public String getPageToIncludeForRendering() {
        return pageToIncludeForRendering;
    }

    public void setPageToIncludeForRendering(String pageToIncludeForRendering) {
        this.pageToIncludeForRendering = pageToIncludeForRendering;
    }

    public String getPageToIncludeForSearching() {
        return pageToIncludeForSearching;
    }

    public void setPageToIncludeForSearching(String pageToIncludeForSearching) {
        this.pageToIncludeForSearching = pageToIncludeForSearching;
    }
}

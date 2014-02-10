/*
 * Copyright (c) 2012, Liushangkun520@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lianjita.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author shangkun.liusk
 */
public class ValidateResult {

    private List<String>        mErrors      = new ArrayList<String>();

    private Map<String, String> mFieldErrors = new HashMap<String, String>();

    public ValidateResult() {
    }

    /**
     * add error.
     * 
     * @param msg error message.
     */
    public void addError(String msg) {
        mErrors.add(msg);
    }

    /**
     * add field error.
     * 
     * @param fieldName field name
     * @param msg error message.
     */
    public void addFieldError(String fieldName, String msg) {
        mFieldErrors.put(fieldName, msg);
    }

    /**
     * get errors.
     * 
     * @return error list.
     */
    public List<String> getErrors() {
        return mErrors;
    }

    /**
     * get field errors.
     * 
     * @return map < fieldName, error message >.
     */
    public Map<String, String> getFieldErrors() {
        return mFieldErrors;
    }

    /**
     * has errors.
     * 
     * @return has errors.
     */
    public boolean hasError() {
        return !(mErrors.isEmpty() && mFieldErrors.isEmpty());
    }

    /**
     * to string.
     * 
     * @return string.
     */
    public String toString() {
        if (!hasError())
            return "No error.";

        StringBuilder sb = new StringBuilder();
        for (Iterator<String> it = mErrors.iterator(); it.hasNext();)
            sb.append(it.next()).append("\n");

        for (Iterator<Map.Entry<String, String>> it = mFieldErrors.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, String> entry = it.next();
            sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}

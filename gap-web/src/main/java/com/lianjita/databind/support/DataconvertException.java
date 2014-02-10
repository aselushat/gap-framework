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
package com.lianjita.databind.support;

/**
 * @author shangkun.liusk
 */
public class DataconvertException extends IllegalArgumentException {

    private static final long serialVersionUID = 5587647576963800281L;

    private String            field;
    private Object            value;
    private String            type;

    public DataconvertException(String field, Object value, Class<?> type) {
        super(String.format("can't convert '%s' to %s for field '%s'", value, type, field));

        this.field = field;
        this.value = value;
        this.type = type.getName();
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

}

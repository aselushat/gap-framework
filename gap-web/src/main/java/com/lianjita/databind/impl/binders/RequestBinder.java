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
package com.lianjita.databind.impl.binders;

import javax.servlet.http.HttpServletRequest;

import com.lianjita.databind.DataBindContext;
import com.lianjita.databind.Databinder;

/**
 * HttpServletRequest的类型绑定
 * 
 * @author shangkun.liusk
 */
public class RequestBinder implements Databinder {

    public Object bind(DataBindContext context) throws IllegalArgumentException {

        HttpServletRequest request = context.getData(HttpServletRequest.class);

        if (request == null) {
            throw new IllegalArgumentException("No httpServletRequest in context");
        }

        return request;
    }
}

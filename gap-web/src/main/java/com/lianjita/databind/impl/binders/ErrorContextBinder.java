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

import com.lianjita.databind.DataBindContext;
import com.lianjita.databind.Databinder;
import com.lianjita.web.RequestContext;

/**
 * @author shangkun.liusk
 */
public class ErrorContextBinder implements Databinder {

    public Object bind(DataBindContext context) throws IllegalArgumentException {

        RequestContext requestContext = context.getData(RequestContext.class);

        if (requestContext == null) {
            throw new IllegalArgumentException("No RequestContext in context");
        }

        return requestContext.currentErrorContext();
    }

}

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

import org.apache.commons.lang.StringUtils;

import com.lianjita.databind.DataBindContext;
import com.lianjita.databind.Databinder;
import com.lianjita.databind.annotation.PathParam;
import com.lianjita.databind.support.BeanPropertiesBindUtil;
import com.lianjita.databind.support.ParameterMetaInfo;
import com.lianjita.web.RequestContext;

/**
 * @author shangkun.liusk
 */
public class PathParameterBinder implements Databinder {

    public Object bind(DataBindContext context) throws IllegalArgumentException {

        ParameterMetaInfo parameterMetaInfo = context.getParameterMetaInfo();
        PathParam annotation = parameterMetaInfo.getAnnotation(PathParam.class);

        RequestContext requestContext = context.getData(RequestContext.class);
        if (requestContext == null) {
            throw new IllegalArgumentException("No RPCRequestContext in context");
        }

        Class<?> parameterType = parameterMetaInfo.getParameterType();
        String name = annotation.name();

        if (parameterType.getName().equals(Object.class.getName())) {
            throw new RuntimeException(String.format("no exact type assigned for parameter '%s'", name));

        }

        if (!BeanPropertiesBindUtil.isSimpleBindType(parameterType)) {
            throw new RuntimeException(String.format("'%s' is not a primitive type", parameterType.getName()));
        }

        String value = StringUtils.trimToNull(requestContext.getPathParameters().get(name));

        if (value == null) {
            value = annotation.defaultValue();
        }

        if (value != null) {
            return context.convertDataIfNecessary(value, parameterType);
        }

        return null;
    }

}

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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lianjita.databind.DataBindContext;
import com.lianjita.databind.Databinder;
import com.lianjita.databind.annotation.RequestParams;
import com.lianjita.databind.support.BeanPropertiesBindUtil;
import com.lianjita.databind.support.ParameterMetaInfo;

/**
 * 基于@RequestParams的参数绑定，只支持非基本类型的POJO对象，不支持数组、集合类型
 * 
 * @author shangkun.liusk
 */
public class RequestPOJOBinder implements Databinder {

    public Object bind(DataBindContext context) throws IllegalArgumentException {

        ParameterMetaInfo parameterMetaInfo = context.getParameterMetaInfo();
        RequestParams annotation = parameterMetaInfo.getAnnotation(RequestParams.class);
        if (annotation == null) {
            throw new IllegalArgumentException("No @RequestParams annotation found");
        }

        HttpServletRequest request = context.getData(HttpServletRequest.class);
        if (request == null) {
            throw new IllegalArgumentException("No httpServletRequest in context");
        }

        Class<?> parameterType = parameterMetaInfo.getParameterType();

        if (BeanPropertiesBindUtil.isSimpleBindType(parameterType)) {
            throw new RuntimeException(String.format("'%s' is a primitive type", parameterMetaInfo.getComponentType()
                    .getName()));
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> parameterMap = request.getParameterMap();
        if (Map.class.isAssignableFrom(parameterType)) {
            return parameterMap;
        }

        Object result = null;
        try {
            result = parameterType.newInstance();
            BeanPropertiesBindUtil.bindProperties(result, parameterMap, context.getDataConverterContext());
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) e;
            }

            throw new IllegalArgumentException("bind pojo failed !", e);
        }

        return result;
    }

}

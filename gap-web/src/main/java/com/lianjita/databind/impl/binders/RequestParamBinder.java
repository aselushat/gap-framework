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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.lianjita.databind.DataBindContext;
import com.lianjita.databind.Databinder;
import com.lianjita.databind.annotation.RequestParam;
import com.lianjita.databind.support.BeanPropertiesBindUtil;
import com.lianjita.databind.support.DataconvertException;
import com.lianjita.databind.support.ParameterMetaInfo;

/**
 * 基于@RequestParam的参数绑定。只支持基本数据类型（以及自定义PropertyEditor的实现），且支持基本类型的数组与集合。
 * 
 * @author shangkun.liusk
 */
public class RequestParamBinder implements Databinder {

    public Object bind(DataBindContext context) throws IllegalArgumentException {

        ParameterMetaInfo parameterMetaInfo = context.getParameterMetaInfo();
        RequestParam annotation = parameterMetaInfo.getAnnotation(RequestParam.class);
        if (annotation == null) {
            throw new IllegalArgumentException("No @RequestParam annotation found");
        }

        HttpServletRequest request = context.getData(HttpServletRequest.class);
        if (request == null) {
            throw new IllegalArgumentException("No httpServletRequest in context");
        }

        Class<?> parameterType = parameterMetaInfo.getParameterType();
        Class<?> componentType = parameterMetaInfo.getComponentType();
        String name = annotation.name();

        boolean isArray = parameterType.isArray();
        if (isArray || Collection.class.isAssignableFrom(parameterType)) {

            if (componentType.getName().equals(Object.class.getName())) {
                throw new RuntimeException(String.format("no exact generic type assigned for parameter '%s'", name));
            }

            if (!BeanPropertiesBindUtil.isSimpleBindType(componentType)) {
                throw new RuntimeException(String.format("'%s' is not a primitive type", parameterMetaInfo
                        .getComponentType().getName()));
            }

            String[] parameterValues = request.getParameterValues(name);

            if (parameterValues == null) {
                parameterValues = annotation.defaultValues();
            }

            if (parameterValues == null || parameterValues.length == 0) {
                return null;
            }

            List<Object> list = new ArrayList<Object>();
            Object array = Array.newInstance(componentType, parameterValues.length);

            Object value;
            for (int i = 0; i < parameterValues.length; i++) {
                value = context.convertDataIfNecessary(parameterValues[i], componentType);

                if (isArray) {
                    Array.set(array, i, value);
                } else {
                    list.add(value);
                }
            }

            if (isArray) {
                return array;
            }

            return list;
        }

        if (!BeanPropertiesBindUtil.isSimpleBindType(parameterType)) {
            throw new RuntimeException(String.format("'%s' is not a primitive type", parameterType.getName()));
        }

        String value = StringUtils.trimToNull(request.getParameter(name));

        if (value == null) {
            value = annotation.defaultValue();
        }

        if (value != null) {
            try {
                return context.convertDataIfNecessary(value, parameterType);
            } catch (Exception e) {
                throw new DataconvertException(name, BeanPropertiesBindUtil.errorValue2String(value), parameterType);
            }
        }

        return null;
    }

}

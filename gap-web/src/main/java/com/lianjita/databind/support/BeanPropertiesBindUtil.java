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

import java.beans.PropertyDescriptor;
import java.net.URI;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;

/**
 * @author shangkun.liusk
 */
public class BeanPropertiesBindUtil {

    public static void bindProperties(Object object, Map<String, Object> parameters,
                                      DataConverterContext dataConverterContext) {
        if (object == null) {
            return;
        }

        BeanWrapper bean = new BeanWrapperImpl(object);

        // 附加自定义的PropertyEditor
        dataConverterContext.attachPropertyEditors(bean);

        for (String key : parameters.keySet()) {
            String propertyName = key;
            if (bean.isWritableProperty(propertyName)) {
                PropertyDescriptor propertyDescriptor = bean.getPropertyDescriptor(propertyName);
                Object value = parameters.get(key);
                try {
                    bean.setPropertyValue(propertyName, value);
                } catch (Exception e) {
                    throw new DataconvertException(propertyName, errorValue2String(value),
                            propertyDescriptor.getPropertyType());
                }
            }

        }
    }

    public static String errorValue2String(Object value) {
        String pValue = value.toString();

        if (value != null && value.getClass().isArray()) {
            Object[] values = (Object[]) value;
            if (values.length > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < values.length; i++) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(values[i]);
                }
                pValue = sb.toString();
            } else {
                pValue = "";
            }
        }
        return pValue;
    }

    public static boolean isSimpleBindType(Class<?> type) {
        return (ClassUtils.isPrimitiveOrWrapper(type) || String.class.isAssignableFrom(type)
                || Date.class.isAssignableFrom(type) || URI.class.isAssignableFrom(type) || Resource.class
                    .isAssignableFrom(type));
    }

}

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

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * @author shangkun.liusk
 */
public class TypeConverterUtil {

    public static TypeConverter findAndAttachTypeConverter(BeanFactory beanFactory) {

        TypeConverter typeConverter = null;

        if (beanFactory instanceof ConfigurableBeanFactory) {
            ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;

            Collection<PropertyEditorRegistrar> propertyEditorRegistrars = getPropertyEditorRegistrars(beanFactory);
            if (propertyEditorRegistrars != null) {
                // Attach当前Spring上下文中的PropertyEditor (不会添加重复的PropertyEditorRegistrar)
                for (PropertyEditorRegistrar propertyEditorRegistrar : propertyEditorRegistrars) {
                    configurableBeanFactory.addPropertyEditorRegistrar(propertyEditorRegistrar);
                }
            }

            typeConverter = configurableBeanFactory.getTypeConverter();

        } else {
            typeConverter = new SimpleTypeConverter();
        }

        return typeConverter;
    }

    public static Collection<PropertyEditorRegistrar> getPropertyEditorRegistrars(BeanFactory beanFactory) {

        Collection<PropertyEditorRegistrar> propertyEditorRegistrars = null;

        if (beanFactory instanceof ListableBeanFactory) {
            Map<String, PropertyEditorRegistrar> propertyEditorRegistrarMap = ((ListableBeanFactory) beanFactory)
                    .getBeansOfType(PropertyEditorRegistrar.class);

            propertyEditorRegistrars = propertyEditorRegistrarMap.values();

        }

        return propertyEditorRegistrars;
    }
}

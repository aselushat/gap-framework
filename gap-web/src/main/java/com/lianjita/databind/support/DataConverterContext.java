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

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.TypeConverter;

public class DataConverterContext {

    private TypeConverter                       typeConverter;
    private Collection<PropertyEditorRegistrar> propertyEditorRegistrars;

    public DataConverterContext(TypeConverter typeConverter,
                                Collection<PropertyEditorRegistrar> propertyEditorRegistrars) {
        this.typeConverter = typeConverter;
        this.propertyEditorRegistrars = propertyEditorRegistrars;
    }

    public TypeConverter getTypeConverter() {
        return typeConverter;
    }

    public Collection<PropertyEditorRegistrar> getPropertyEditorRegistrars() {
        return propertyEditorRegistrars;
    }

    public void attachPropertyEditors(BeanWrapper beanWrapper) {
        for (PropertyEditorRegistrar propertyEditorRegistrar : this.propertyEditorRegistrars) {
            propertyEditorRegistrar.registerCustomEditors(beanWrapper);
        }
    }
}
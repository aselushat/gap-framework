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
package com.lianjita.databind.impl;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lianjita.databind.DataBinderFactory;
import com.lianjita.databind.Databinder;
import com.lianjita.databind.DatabinderRegistry;
import com.lianjita.databind.annotation.PathParam;
import com.lianjita.databind.annotation.RequestParam;
import com.lianjita.databind.annotation.RequestParams;
import com.lianjita.databind.impl.binders.ErrorContextBinder;
import com.lianjita.databind.impl.binders.PathParameterBinder;
import com.lianjita.databind.impl.binders.RequestBinder;
import com.lianjita.databind.impl.binders.RequestPOJOBinder;
import com.lianjita.databind.impl.binders.RequestParamBinder;
import com.lianjita.validation.ErrorContext;

/**
 * @author shangkun.liusk
 */
public class DatabinderRegistryImpl implements DatabinderRegistry {

    private Map<Class<?>, Databinder> binder4Type       = new HashMap<Class<?>, Databinder>();
    private Map<Class<?>, Databinder> binder4Annotation = new HashMap<Class<?>, Databinder>();

    public DatabinderRegistryImpl() {

        // Default Databinders

        this.registByType(HttpServletRequest.class, new RequestBinder());
        this.registByType(ErrorContext.class, new ErrorContextBinder());

        this.registByAnnotation(RequestParam.class, new RequestParamBinder());
        this.registByAnnotation(RequestParams.class, new RequestPOJOBinder());
        this.registByAnnotation(PathParam.class, new PathParameterBinder());
    }

    public void setBinder4Type(Map<Class<?>, Databinder> _binder4Type) {
        if (_binder4Type != null) {
            this.binder4Type.putAll(_binder4Type);
        }
    }

    public void setBinder4Annotation(Map<Class<?>, Databinder> _binder4Annotation) {
        if (_binder4Annotation != null) {
            this.binder4Annotation.putAll(_binder4Annotation);
        }
    }

    public void setBinderFactories(List<DataBinderFactory> dataBinderFactories) {
        if (dataBinderFactories != null) {
            for (DataBinderFactory factory : dataBinderFactories) {
                Map<Class<?>, Databinder> _binder4Annotation = factory.getBinder4Annotation();
                if (_binder4Annotation != null) {
                    this.binder4Annotation.putAll(_binder4Annotation);
                }

                Map<Class<?>, Databinder> _binder4Type = factory.getBinder4Type();
                if (_binder4Type != null) {
                    this.binder4Type.putAll(_binder4Type);
                }
            }
        }
    }

    public Databinder findByType(Class<?> type) {
        return this.binder4Type.get(type);
    }

    public Databinder findByAnnotation(Annotation annotation) {
        return this.binder4Annotation.get(annotation.annotationType());
    }

    public DatabinderRegistry registByType(Class<?> type, Databinder databinder) {
        this.binder4Type.put(type, databinder);
        return this;
    }

    public <T extends Annotation> DatabinderRegistry registByAnnotation(Class<T> annotationType, Databinder databinder) {
        this.binder4Annotation.put(annotationType, databinder);
        return this;
    }

}

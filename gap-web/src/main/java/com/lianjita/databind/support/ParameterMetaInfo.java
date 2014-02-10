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

import java.lang.annotation.Annotation;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Map;

/**
 * @author shangkun.liusk
 */
public class ParameterMetaInfo {

    private Class<?>     parameterType;

    private Class<?>     componentType = Object.class;

    private Annotation[] paramAnnotations;

    public ParameterMetaInfo(Class<?> parameterType, Type genericType, Annotation[] paramAnnotations) {

        this.paramAnnotations = paramAnnotations;

        if (Map.class.isAssignableFrom(parameterType) || java.util.Hashtable.class.isAssignableFrom(parameterType)) {
            this.parameterType = parameterType;
        } else {

            this.parameterType = parameterType;

            Type type = Object.class;

            if (this.parameterType.isArray()) {
                type = this.parameterType.getComponentType();
            }

            if (genericType instanceof ParameterizedType) {
                ParameterizedType _type = (ParameterizedType) genericType;
                Type[] actualTypeArguments = _type.getActualTypeArguments();
                type = actualTypeArguments[0];

            } else if (genericType instanceof GenericArrayType) {
                type = ((GenericArrayType) genericType).getGenericComponentType();
            }

            if (type instanceof WildcardType) {
                type = ((WildcardType) type).getUpperBounds()[0];
            }

            this.componentType = (Class<?>) type;

        }

    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public Class<?> getComponentType() {
        return componentType;
    }

    public Annotation[] getParamAnnotations() {
        return paramAnnotations;
    }

    /**
     * 查找指定类型的Annotation
     * 
     * @param <T>
     * @param annotationType
     * @return 如果不存在则返回NULL
     */
    public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
        T annotation = null;

        for (Annotation a : paramAnnotations) {
            if (annotationType.isInstance(a)) {
                annotation = annotationType.cast(a);
                break;
            }
        }

        return annotation;
    }

}

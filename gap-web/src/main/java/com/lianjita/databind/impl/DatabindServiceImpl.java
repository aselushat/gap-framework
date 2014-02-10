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
import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;

import com.lianjita.databind.DatabindService;
import com.lianjita.databind.Databinder;
import com.lianjita.databind.DatabinderRegistry;
import com.lianjita.databind.support.DataConverterContext;
import com.lianjita.databind.support.MethodMetaInfo;
import com.lianjita.databind.support.TypeConverterUtil;

/**
 * @author shangkun.liusk
 */
public class DatabindServiceImpl implements DatabindService, InitializingBean, BeanFactoryAware {

    private DatabinderRegistry   registry;

    private BeanFactory          beanFactory;

    private DataConverterContext dataConverterContext;

    @Override
    public Object[] bindMethod(Method method, Object... dataSources) throws IllegalArgumentException {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        MethodMetaInfo methodMetaInfo = new MethodMetaInfo(method);

        Object[] result = new Object[parameterTypes.length];
        int i = 0;

        for (Class<?> pType : parameterTypes) {

            Databinder binder = this.registry.findByType(pType);
            if (binder == null) {
                for (Annotation annotation : parameterAnnotations[i]) {
                    binder = this.registry.findByAnnotation(annotation);
                    if (binder != null) {
                        break;
                    }
                }
            }

            if (binder != null) {
                result[i] = binder.bind(new BindContextImpl(methodMetaInfo.getParameterMetaInfo(i),
                        this.dataConverterContext, dataSources));
            } else {
                //TODO 抛出或者给出默认值
                result[i] = null;
            }

            i++;
        }

        return result;
    }

    public void setRegistry(DatabinderRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public DatabinderRegistry getRegistry() {
        return registry;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public DataConverterContext getDataConverterContext() {
        return dataConverterContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        this.dataConverterContext = new DataConverterContext(TypeConverterUtil.findAndAttachTypeConverter(beanFactory),
                TypeConverterUtil.getPropertyEditorRegistrars(beanFactory));

        if (this.registry == null) {
            this.registry = new DatabinderRegistryImpl();
        }
    }

}

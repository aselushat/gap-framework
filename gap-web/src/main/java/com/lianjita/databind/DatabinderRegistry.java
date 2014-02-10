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
package com.lianjita.databind;

import java.lang.annotation.Annotation;

/**
 * @author shangkun.liusk
 */
public interface DatabinderRegistry {

    /**
     * 根据类型注册Databinder
     * 
     * @param type
     * @param databinder
     * @return
     */
    public DatabinderRegistry registByType(Class<?> type, Databinder databinder);

    /**
     * 根据Annotation类型注册Databinder
     * 
     * @param type
     * @param databinder
     * @return
     */
    public <T extends Annotation> DatabinderRegistry registByAnnotation(Class<T> annotationType, Databinder databinder);

    /**
     * 根据数据类型查找指定的Databinder
     * 
     * @param type
     * @return 不存在，返回NULL
     */
    public Databinder findByType(Class<?> type);

    /**
     * 根据Annotation实例查找指定的Databinder
     * 
     * @param annotation
     * @return 不存在，返回NULL
     */
    public Databinder findByAnnotation(Annotation annotation);
}

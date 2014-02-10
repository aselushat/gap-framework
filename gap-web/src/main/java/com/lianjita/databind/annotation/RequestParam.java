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
package com.lianjita.databind.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标识一个参数，使之从request parameters中取得值。
 * <p>
 * 用法如下：
 * </p>
 * <ol>
 * <li>仅指定参数名称：<code>@RequestParam("name")</code>。</li>
 * <li>指定参数名称，以及单个默认值：
 * <code>@RequestParam(name="name", defaultValue="123")</code>。</li>
 * <li>指定参数名称，以及一组默认值：
 * <code>@RequestParam(name="name", defaultValues={"1", "2", "3"})</code>。</li>
 * </ol>
 * 
 * @author shangkun.liusk
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
public @interface RequestParam {

    /**
     * 用于标识param的名称。
     * <p>
     * 此参数用于有多个参数的形式：
     * <code>@RequestParam(name="paramName", defaultValue="123")</code>。
     * </p>
     */
    String name();

    /**
     * 指定参数的默认值。
     */
    String defaultValue() default "";

    /**
     * 指定参数的默认值数组。
     */
    String[] defaultValues() default {};
}

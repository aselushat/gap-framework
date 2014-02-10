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
package com.lianjita.validation;

import java.lang.reflect.Method;

/**
 * @author shangkun.liusk
 */
public interface ValidateService {

    /**
     * validate object.
     * 
     * @param name validate name.
     * @param value value.
     * @return result.
     * @throws IllegalArgumentException.
     */
    public ValidateResult validate(String name, Object value) throws IllegalArgumentException;

    /**
     * validate arguments.
     * 
     * @param method method.
     * @param arguments arguments.
     * @return result.
     * @throws IllegalArgumentException.
     */
    public ValidateResult validate(Method method, Object[] arguments) throws IllegalArgumentException;

}

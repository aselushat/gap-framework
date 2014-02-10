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

import java.lang.reflect.Method;

/**
 * @author shangkun.liusk
 */
public interface DatabindService {

    /**
     * 根据方法签名以及数据源，返回当前方法的参数数组
     * 
     * @param method 方法对象
     * @param dataSource 参数的所需要的数据源
     * @return 返回当前方法的参数数组
     * @throws IllegalArgumentException
     */
    public Object[] bindMethod(Method method, Object... dataSources) throws IllegalArgumentException;
}

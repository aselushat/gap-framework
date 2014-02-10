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

import com.lianjita.databind.support.DataConverterContext;
import com.lianjita.databind.support.ParameterMetaInfo;

public interface DataBindContext {

    /**
     * 返回当前系统的数据转换上下文
     * 
     * @return
     */
    public DataConverterContext getDataConverterContext();

    /**
     * 返回当前参数的元信息描述
     * 
     * @return
     */
    public ParameterMetaInfo getParameterMetaInfo();

    /**
     * 根据类型返回数据源中存在的数据
     * 
     * @param type
     * @return
     */
    public <T> T getData(Class<T> type);

    /**
     * 根据需要，做数据类型转换 （如果数据类型与原始值是一样的，则不转换，并直接返回）
     * 
     * @param value 原始值
     * @param type 期望转换后的类型
     * @return
     */
    public Object convertDataIfNecessary(Object value, Class<?> type);

}

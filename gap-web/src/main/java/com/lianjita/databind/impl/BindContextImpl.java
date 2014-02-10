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

import com.lianjita.databind.DataBindContext;
import com.lianjita.databind.support.DataConverterContext;
import com.lianjita.databind.support.ParameterMetaInfo;

/**
 * @author shangkun.liusk
 */
public class BindContextImpl implements DataBindContext {

    private Object[]             dataSources;

    private DataConverterContext dataConverterContext;
    private ParameterMetaInfo    parameterMetaInfo;

    public BindContextImpl(ParameterMetaInfo parameterMetaInfo, DataConverterContext dataConverterContext,
                           Object... dataSources) {
        this.parameterMetaInfo = parameterMetaInfo;
        this.dataConverterContext = dataConverterContext;
        this.dataSources = dataSources;
    }

    @Override
    public DataConverterContext getDataConverterContext() {
        return this.dataConverterContext;
    }

    public <T> T getData(Class<T> type) {

        for (Object obj : this.dataSources) {
            if (type.isInstance(obj)) {
                return type.cast(obj);
            }
        }

        return null;
    }

    @Override
    public Object convertDataIfNecessary(Object value, Class<?> type) {
        return this.dataConverterContext.getTypeConverter().convertIfNecessary(value, type);
    }

    @Override
    public ParameterMetaInfo getParameterMetaInfo() {
        return this.parameterMetaInfo;
    }

}

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
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author shangkun.liusk
 */
public class MethodMetaInfo {

    private List<ParameterMetaInfo> parameterMetaInfos = new ArrayList<ParameterMetaInfo>();

    public MethodMetaInfo(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Type[] genericParameterTypes = method.getGenericParameterTypes();

        Annotation[][] paramAnnotations = method.getParameterAnnotations();

        for (int i = 0; i < parameterTypes.length; i++) {
            this.parameterMetaInfos.add(new ParameterMetaInfo(parameterTypes[i], genericParameterTypes[i],
                    paramAnnotations[i]));
        }
    }

    public Collection<ParameterMetaInfo> getParameterMetaInfos() {
        return parameterMetaInfos;
    }

    public ParameterMetaInfo getParameterMetaInfo(int index) {
        return this.parameterMetaInfos.get(index);
    }
}

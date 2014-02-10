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
package com.lianjita.web.impl;

import java.lang.reflect.Method;
import java.util.Map;

import com.lianjita.databind.DatabindService;
import com.lianjita.validation.ErrorContext;
import com.lianjita.validation.ErrorContextUtil;
import com.lianjita.validation.ErrorItem;
import com.lianjita.validation.ValidateResult;
import com.lianjita.validation.ValidateService;
import com.lianjita.web.Handler;
import com.lianjita.web.RequestContext;
import com.lianjita.web.annotation.RequestMapping;

/**
 * @author shangkun.liusk
 */
public class ControllerImpl implements Handler {

    private ValidateService validateService;

    private DatabindService databindService;

    private Object          controllerInstance;

    private Method          method;

    public ControllerImpl(ValidateService validateService, DatabindService databindService, Object controllerInstance,
                          Method method) {

        this.validateService = validateService;
        this.databindService = databindService;
        this.controllerInstance = controllerInstance;
        this.method = method;
    }

    @Override
    public Object execute(RequestContext requestContext, ErrorContext errorContext) {

        if (requestContext instanceof DefaultRequestContext) {

            DefaultRequestContext context = (DefaultRequestContext) requestContext;

            this.setupRequestContext(context);
        }

        Object result = null;

        try {
            // 参数绑定
            Object[] args = this.databindService.bindMethod(method, requestContext.getRequest(),
                    requestContext.getResponse(), requestContext, errorContext);

            // 参数验证
            if (args != null && args.length > 0) {

                ValidateResult validateResult = this.validateService.validate(method, args);
                if (validateResult.hasError()) {
                    // 验证失败，往ErrorContext中写入验证错误信息
                    for (Map.Entry<String, String> entry : validateResult.getFieldErrors().entrySet()) {
                        errorContext.addError(ErrorItem.create(entry.getKey(), null, entry.getValue()));
                    }
                }
            }

            if (errorContext.hasError()) { // 不执行业务方法，直接返回null
                return null;
            }

            ErrorContextUtil.setCurrentErrorContext(errorContext);
            result = method.invoke(this.controllerInstance, args);

        } catch (Exception ex) {
            throw new RuntimeException(String.format("invoke Method '%s' failed !", requestContext.getMethod()
                    .getName()), ex);
        } finally {
            ErrorContextUtil.setCurrentErrorContext(null);
        }

        return result;
    }

    protected void setupRequestContext(DefaultRequestContext context) {

        context.setMethod(this.method);

        RequestMapping RequestMapping = this.method.getAnnotation(RequestMapping.class);
        if (RequestMapping != null) {
            context.setCharset(RequestMapping.charset());
            context.setResponseContentType(RequestMapping.responseContentType());
        }

    }

}

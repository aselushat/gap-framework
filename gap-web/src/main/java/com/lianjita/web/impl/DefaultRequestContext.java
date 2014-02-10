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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lianjita.validation.ErrorContext;
import com.lianjita.web.RequestContext;

public class DefaultRequestContext implements RequestContext {

    private String              url;

    private String              urlPattern;

    private HttpServletRequest  request;

    private HttpServletResponse response;

    private String              charset;

    private Map<String, String> pathParameters;

    private ErrorContext        errorContext;

    private String              responseContentType;

    private Method              method;

    public DefaultRequestContext(HttpServletRequest request, HttpServletResponse response, ErrorContext errorContext) {
        this.request = request;
        this.response = response;
        this.url = request.getRequestURI();
        String contextPath = request.getContextPath();
        int length = contextPath.length();
        if (length > 0) { // 截取contextPath
            this.url = this.url.substring(length);
        }
        int index = this.url.lastIndexOf(".");
        if (index != -1) {
            this.url = this.url.substring(0, index);
        }

        this.errorContext = errorContext;

    }

    public DefaultRequestContext addPathParameters(Map<String, String> parameters) {

        if (parameters != null) {

            if (this.pathParameters == null) {
                this.pathParameters = new HashMap<String, String>();
            }

            this.pathParameters.putAll(parameters);
        }

        return this;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setResponseContentType(String responseContentType) {
        this.responseContentType = responseContentType;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUrlPattern() {
        return this.urlPattern;
    }

    @Override
    public HttpServletRequest getRequest() {
        return this.request;
    }

    @Override
    public HttpServletResponse getResponse() {
        return this.response;
    }

    @Override
    public String getCharset() {
        return this.charset;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public String getResponseContentType() {
        return this.responseContentType;
    }

    @Override
    public Map<String, String> getPathParameters() {
        return this.pathParameters;
    }

    @Override
    public ErrorContext currentErrorContext() {
        return this.errorContext;
    }

}

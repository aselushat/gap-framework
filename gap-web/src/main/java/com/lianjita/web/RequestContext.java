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
package com.lianjita.web;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lianjita.validation.ErrorContext;

public interface RequestContext {

    /**
     * 返回当前请求的路径
     * 
     * @return
     */
    public String getUrl();

    /**
     * 返回当前请求所对应的url映射规则
     * 
     * @return
     */
    public String getUrlPattern();

    /**
     * 返回当前请求相对应的Request对象
     * 
     * @return
     */
    public HttpServletRequest getRequest();

    /**
     * 返回当前请求相对应的Reponse对象
     * 
     * @return
     */
    public HttpServletResponse getResponse();

    /**
     * 返回编码
     * 
     * @return
     */
    public String getCharset();

    /**
     * 返回执行请求的方法
     * 
     * @return
     */
    public Method getMethod();

    /**
     * 返回response类型
     * 
     * @return
     */
    public String getResponseContentType();

    /**
     * 返回url上的参数
     * 
     * @return
     */
    public Map<String, String> getPathParameters();

    /**
     * 返回上下文验证环境
     * 
     * @return
     */
    public ErrorContext currentErrorContext();
}

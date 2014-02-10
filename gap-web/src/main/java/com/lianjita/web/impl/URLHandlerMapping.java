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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.lianjita.databind.DatabindService;
import com.lianjita.validation.ValidateService;
import com.lianjita.web.Handler;
import com.lianjita.web.HandlerMapping;
import com.lianjita.web.RequestContext;
import com.lianjita.web.annotation.RequestMapping;

/**
 * 根据url映射handler
 * 
 * @author shangkun.liusk
 */
public class URLHandlerMapping implements HandlerMapping, InitializingBean {

    private PathMatcher          pathMatcher         = new AntPathMatcher();

    private Map<String, Handler> controllers         = new HashMap<String, Handler>();

    private Map<String, String>  relationURLPatterns = new HashMap<String, String>();

    @Autowired
    private ValidateService      validateService;

    @Autowired
    private DatabindService      databindService;

    @Override
    public Handler findHandler(RequestContext requestContext) {

        String url = requestContext.getUrl();

        Handler controller = null;

        List<String> matchingPatterns = new ArrayList<String>();

        for (String registeredPattern : this.controllers.keySet()) {

            if (this.pathMatcher.match(registeredPattern, url)) {
                matchingPatterns.add(registeredPattern);
            }
        }

        String bestPatternMatch = null;
        Comparator<String> patternComparator = this.pathMatcher.getPatternComparator(url);

        if (!matchingPatterns.isEmpty()) {

            Collections.sort(matchingPatterns, patternComparator);

            bestPatternMatch = matchingPatterns.get(0);
        }

        if (bestPatternMatch != null) {

            controller = this.controllers.get(bestPatternMatch);

            if (controller == null) {
                return null;
            }

            // 从最好匹配的规则中抽取参数
            Map<String, String> uriTemplateVariables = new LinkedHashMap<String, String>();
            uriTemplateVariables.putAll(this.pathMatcher.extractUriTemplateVariables(bestPatternMatch, url));

            // 尝试获取带通配符的规则所对应的原始映射规则（参见registerController方法中的“通配符的映射”部分）
            String relationPattern = this.relationURLPatterns.get(bestPatternMatch);
            if (relationPattern != null) {
                uriTemplateVariables.putAll(this.pathMatcher.extractUriTemplateVariables(relationPattern, url));
            }

            // 往context中写入PathParameter
            if (requestContext instanceof DefaultRequestContext) {

                DefaultRequestContext context = (DefaultRequestContext) requestContext;

                context.addPathParameters(uriTemplateVariables);

                // 设置当前请求所对应的urlPattern
                context.setUrlPattern(relationPattern != null ? relationPattern : bestPatternMatch);
            }

        }

        return controller;
    }

    @Override
    public void registerHandler(Object... beans) {
        for (Object bean : beans) {

            // 通过AopUtils来获取当前实例的真实class
            Class<?> clazz = AopUtils.getTargetClass(bean);

            // 获取serviceName
            String[] serviceNames = this.getServiceNamesFromBean(clazz);

            // 扫描所有的方法，处理带@RequestMapping的方法
            Method[] methods = clazz.getMethods();

            for (Method method : methods) {

                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                if (annotation == null) {
                    // 忽略没有@ResourceMapping声明的方法
                    continue;
                }

                // 获取用户设置的path。
                // 注：path的生成规则
                //    1、如果用户在@ResourceMapping中设置了path，则最终生成的URL为：      contextPath+/namespace + /@WebResource + /@ResourceMapping
                //    2、如果用户没有在@ResourceMapping中设置paths，则最终生成的URL为：contextPath+/namespace + /@WebResource + /methodName

                for (String serviceName : serviceNames) {

                    String[] paths = annotation.value();

                    if (paths == null || paths.length <= 0) {
                        paths = new String[] { method.getName() };
                    }

                    for (String path : paths) {

                        if (path.charAt(0) == '/') {
                            path = String.format("/%s%s", serviceName, path);
                        } else {
                            path = String.format("/%s/%s", serviceName, path);
                        }

                        // 添加当前Path与Controller的映射关系
                        this.controllers.put(path, new ControllerImpl(this.validateService, this.databindService, bean,
                                method));

                        // 通配符的映射，使用映射规则更通用
                        if (path.indexOf("{") != -1) {

                            // 如：/users/{category}/{age} ，会产生 /users/{category}/{age} 与 /users/**/** 两个映射
                            // 从而访问的URI可以为以下几种：
                            //   (1) /users/v1/v2   --->>    category=v1;age=v2
                            //   (2) /users/v1      --->>    category=v1;age=null
                            //   (3) /users         --->>    category=null;age=null

                            char[] chars = path.toCharArray();
                            StringBuilder stb = new StringBuilder();
                            char c;
                            boolean start = false;
                            for (int i = 0; i < chars.length; i++) {
                                c = chars[i];
                                if (c == '{') {
                                    start = true;
                                    stb.append("**");
                                }

                                if (!start) {
                                    stb.append(c);
                                }

                                if (c == '}') {
                                    start = false;
                                }
                            }

                            path = stb.toString();

                            this.controllers.put(path, new ControllerImpl(this.validateService, this.databindService,
                                    bean, method));

                            // 将当前的通配符Pattern与原始的pattern进行关联 
                            this.relationURLPatterns.put(path, path);

                        }
                    }

                }

            }
        }
    }

    private String[] getServiceNamesFromBean(Class<? extends Object> clazz) {

        RequestMapping annotation = clazz.getAnnotation(RequestMapping.class);

        if (annotation != null) {
            return annotation.value();
        }

        String classSimpleName = clazz.getSimpleName();

        return new String[] { Character.toLowerCase(classSimpleName.charAt(0)) + classSimpleName.substring(1) };

    }

    @Override
    public void afterPropertiesSet() throws Exception {

        if (this.validateService == null) {
            throw new IllegalArgumentException("no ValidateService instance in this context");
        }

        if (this.databindService == null) {
            throw new IllegalArgumentException("no DatabindService instance in this context");
        }
    }

}

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
package com.lianjita.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SourceFilteringListener;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.lianjita.web.HandlerMapping;

/**
 * @author shangkun.liusk
 */
public class DispatcherServlet extends HttpServlet {

    private static final long     serialVersionUID         = 177273139464054242L;

    protected final Log           logger                   = LogFactory.getLog(getClass());

    protected boolean             isDebugEnable            = logger.isDebugEnabled();

    private Class<?>              contextClass             = XmlWebApplicationContext.class;

    public static final String    SERVLET_CONTEXT_PREFIX   = DispatcherServlet.class.getName() + ".CONTEXT.";

    public static final String    DEFAULT_NAMESPACE_SUFFIX = "-servlet";

    private String                contextConfigLocation;

    private WebApplicationContext webApplicationContext;

    @Override
    public final void init() throws ServletException {
        if (isDebugEnable) {
            logger.debug("Initializing servlet '" + getServletName() + "'");
        }

        initServletBean();

        if (isDebugEnable) {
            logger.debug("Servlet '" + getServletName() + "' configured successfully");
        }
    }

    protected void initServletBean() {
        getServletContext().log("Initializing DispatcherServlet '" + getServletName() + "'");
        if (this.logger.isInfoEnabled()) {
            this.logger.info("DispatcherServlet '" + getServletName() + "': initialization started");
        }

        long startTime = System.currentTimeMillis();

        try {
            this.webApplicationContext = createWebApplicationContext();
        } catch (RuntimeException ex) {
            this.logger.error("Context initialization failed", ex);
            throw ex;
        }

        if (this.logger.isInfoEnabled()) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            this.logger.info("FrameworkServlet '" + getServletName() + "': initialization completed in " + elapsedTime
                    + " ms");
        }
    }

    protected WebApplicationContext createWebApplicationContext() {

        Class<?> contextClass = getContextClass();

        if (!ConfigurableWebApplicationContext.class.isAssignableFrom(contextClass)) {
            throw new ApplicationContextException("Fatal initialization error in servlet with name '"
                    + getServletName() + "': custom WebApplicationContext class [" + contextClass.getName()
                    + "] is not of type ConfigurableWebApplicationContext");
        }

        ConfigurableWebApplicationContext wac = (ConfigurableWebApplicationContext) BeanUtils
                .instantiateClass(contextClass);

        wac.setConfigLocations(new String[] { getContextConfigLocation() });

        configureAndRefreshWebApplicationContext(wac);

        return wac;
    }

    protected void configureAndRefreshWebApplicationContext(ConfigurableWebApplicationContext wac) {
        wac.setServletContext(getServletContext());
        wac.setServletConfig(getServletConfig());
        wac.setNamespace(getNamespace());

        wac.addApplicationListener(new SourceFilteringListener(wac, new ContextRefreshListener()));
        wac.refresh();
    }

    public WebApplicationContext getWebApplicationContext() {
        return this.webApplicationContext;
    }

    public void setContextConfigLocation(String contextConfigLocation) {
        this.contextConfigLocation = contextConfigLocation;
    }

    public String getContextConfigLocation() {
        return this.contextConfigLocation;
    }

    public String getNamespace() {
        return getServletName() + DEFAULT_NAMESPACE_SUFFIX;
    }

    public void setContextClass(Class<?> contextClass) {
        this.contextClass = contextClass;
    }

    public Class<?> getContextClass() {
        return this.contextClass;
    }

    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        processRequest(request, response);
    }

    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        processRequest(request, response);
    }

    @Override
    protected final void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        processRequest(request, response);
    }

    @Override
    protected final void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        processRequest(request, response);
    }

    protected final void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    public void onApplicationEvent(ContextRefreshedEvent event) {
        onRefresh(event.getApplicationContext());
    }

    public void onRefresh(ApplicationContext context) {
        // 注册系统组件
        Map<String, HandlerMapping> mappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(context,
                HandlerMapping.class);
        if (mappings.isEmpty()) {
            throw new IllegalArgumentException("no HandlerMapping instance in context");
        }
        for (HandlerMapping mapping : mappings.values()) {
            for (String beanName : context.getBeanDefinitionNames()) {
                mapping.registerHandler(context.getBean(beanName));
            }
        }

    }

    /**
     * ApplicationListener endpoint that receives events from this servlet's
     * WebApplicationContext only, delegating to <code>onApplicationEvent</code>
     * on the FrameworkServlet instance.
     */
    private class ContextRefreshListener implements ApplicationListener<ContextRefreshedEvent> {

        public void onApplicationEvent(ContextRefreshedEvent event) {
            DispatcherServlet.this.onApplicationEvent(event);
        }
    }

}

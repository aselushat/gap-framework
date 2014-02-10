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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.junit.Before;
import org.springframework.mock.web.MockRequestDispatcher;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

import com.lianjita.AbstractTest;
import com.lianjita.web.servlet.DispatcherServlet;

/**
 * baseTest using as web ApplicationContext
 * 
 * @author shangkun.liusk
 */
public abstract class AbstractWebTest extends AbstractTest {

    protected ServletContext    servletContext;
    protected ServletConfig     servletConfig;
    protected DispatcherServlet dispatcherServlet;

    private static final String contextConfigLocation = "dispatcher-servlet.xml";

    @Before
    public void before() throws Exception {

        this.servletContext = new MockServletContext() {

            @Override
            public RequestDispatcher getNamedDispatcher(String path) {
                return new MockRequestDispatcher("");
            }

        };

        this.servletConfig = new MockServletConfig(servletContext, "dispatcher");

        dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setContextConfigLocation(contextConfigLocation);
        dispatcherServlet.init(servletConfig);
    }

}

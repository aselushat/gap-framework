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
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.lianjita.AbstractTest;
import com.lianjita.validation.DefaultErrorContext;
import com.lianjita.validation.ErrorContext;
import com.lianjita.web.RequestContext;
import com.lianjita.web.impl.DefaultRequestContext;

import foo.MyClass;
import foo.MyPOJO;

/**
 * @author shangkun.liusk
 */
public class DatabindServiceTest extends AbstractTest {

    private DatabindService service;
    private Method          nobind;
    private Method          bindRequestParamMethod;
    private Method          bindRequestParamssMethod;
    private Method          bindErrorContext;

    @Before
    public void beforeClass() throws Exception {
        service = (DatabindService) getBean("databindService");

        Assert.assertNotNull(service);

        for (Method method : MyClass.class.getMethods()) {

            if ("bindRequestParam".equals(method.getName())) {
                bindRequestParamMethod = method;
            } else if ("bindRequestParams".equals(method.getName())) {
                bindRequestParamssMethod = method;
            } else if ("nobind".equals(method.getName())) {
                nobind = method;
            } else if ("bindErrorContext".equals(method.getName())) {
                bindErrorContext = method;
            }
        }

        Assert.assertNotNull(bindRequestParamMethod);
        Assert.assertNotNull(bindRequestParamssMethod);
    }

    @Test
    public void testNoparambind() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object[] result = service.bindMethod(nobind, request);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.length, 0);
    }

    @Test
    public void bindRequestParam() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("name", "nonda");
        request.addParameter("age", "1");

        request.addParameter("addresses", "广州");
        request.addParameter("addresses", "杭州");

        Object[] result = service.bindMethod(bindRequestParamMethod, request);

        Assert.assertEquals("nonda", result[0]);
        Assert.assertEquals(1, result[1]);

        @SuppressWarnings("unchecked")
        List<String> addresses = (List<String>) result[2];
        Assert.assertEquals(2, addresses.size());

        Assert.assertTrue(addresses.contains("广州"));

        String[] addresseArray = (String[]) result[3];
        Assert.assertEquals(2, addresseArray.length);
        Assert.assertEquals("杭州", addresseArray[1]);
    }

    @Test
    public void bindRequestParams() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("name", "nonda");
        request.addParameter("age", "1");

        Object[] result = service.bindMethod(bindRequestParamssMethod, request);

        MyPOJO pojo = (MyPOJO) result[0];
        Assert.assertEquals("nonda", pojo.getName());
        Assert.assertEquals(1, pojo.getAge());
    }

    @Test
    public void bindErrorContext() {
        ErrorContext errorContext = new DefaultErrorContext();
        RequestContext requestContext = new DefaultRequestContext(new MockHttpServletRequest(),
                new MockHttpServletResponse(), errorContext);
        Object[] result = service.bindMethod(bindErrorContext, requestContext);
        ErrorContext ec = (ErrorContext) result[0];
        Assert.assertNotNull(ec);
        Assert.assertEquals(ec, errorContext);

    }

}

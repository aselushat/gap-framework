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

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.lianjita.validation.DefaultErrorContext;
import com.lianjita.validation.ErrorContext;
import com.lianjita.web.impl.DefaultRequestContext;

/**
 * @author shangkun.liusk
 */
public class HandlerMappingTest extends AbstractWebTest {

    @Test
    public void testMapping() {
        HandlerMapping mapping = (HandlerMapping) this.getBean("handlerMapping");
        for (String beanName : this.applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanName);
            mapping.registerHandler(this.getBean(beanName));
        }

        MockHttpServletRequest request = new MockHttpServletRequest("get", "/test/index");
        MockHttpServletResponse response = new MockHttpServletResponse();
        ErrorContext errorContext = new DefaultErrorContext();
        RequestContext requestContext = new DefaultRequestContext(request, response, errorContext);

        Handler controller = mapping.findHandler(requestContext);
        Assert.assertNotNull(controller);
        Object result = controller.execute(requestContext, errorContext);
        Assert.assertTrue(result instanceof String);
        Assert.assertEquals("index", (String) result);

        request.setParameter("name", "lianjita.com");
        result = controller.execute(requestContext, errorContext);
        Assert.assertTrue(result instanceof String);
        Assert.assertEquals("lianjita.com", (String) result);

    }
}

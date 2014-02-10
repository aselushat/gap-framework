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
package com.lianjita.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lianjita.model.User;
import com.lianjita.web.Constant;

@Controller
@RequestMapping(value = { "/auth" })
public class Auth {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, Map<String, Object> context) {
        return "auth/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLogin(String username, String password, String done, Map<String, Object> context) {

        context.put("username", username);
        context.put("password", password);

        return "auth/login";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        return "auth/logout";
    }

    @RequestMapping(value = "/regist", method = RequestMethod.GET)
    public String regist() {
        return "auth/regist";
    }

    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public String doRegist(User user, Map<String, Object> context) {
        return Constant.REDIRECT + "/";
    }
}

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
/**
 * 
 */
package com.fanrenmusic.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * @author lsk
 */
@Controller
@RequestMapping("/")
public class LoginController {

    private UserService userService = UserServiceFactory.getUserService();

    @RequestMapping(value = { "/login" })
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = userService.getCurrentUser();
        if (user != null) {
            response.setContentType("text/plain");
            response.getWriter().println("Hello, " + user.getNickname());
        } else {
            response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
        }
    }

    @RequestMapping(value = { "/logout" })
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (this.userService.isUserLoggedIn()) {
            
        }

    }
}

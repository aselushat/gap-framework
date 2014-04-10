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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fanrenmusic.module.Video;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * admin controller.
 * 
 * @author lsk
 */
@Controller
@RequestMapping(value = { "/admin" })
public class AdminController {

    @Autowired
    private ObjectifyFactory objectifyFactory;

    @RequestMapping(value = { "/video/list" })
    public String listVideo(Map<String, Object> context) {
        context.put("obj", this.objectifyFactory);
        this.objectifyFactory.begin().load().key(Key.create(""));
        return "admin/video/list";
    }

    @RequestMapping(value = { "/video/get" }, method = { RequestMethod.GET })
    public String getVideo(Map<String, Object> context, @RequestParam(required = true) Long videoId) {
        Video video = ofy().load().key(Key.create(Video.class, videoId)).now();
        context.put("video", video);
        return "admin/video/get";
    }

    @RequestMapping(value = { "/video/add" }, method = { RequestMethod.GET })
    public String _addVideo(Map<String, Object> context, HttpServletRequest request) {
        return "admin/video/add";
    }

    @RequestMapping(value = { "/video/add" }, method = { RequestMethod.POST })
    public String addVideo(Map<String, Object> context, HttpServletRequest request) {
        return "";
    }
}

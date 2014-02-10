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
package foo.controller;

import com.lianjita.databind.annotation.RequestParam;
import com.lianjita.web.annotation.RequestMapping;

/**
 * TestCaseController
 * 
 * @author shangkun.liusk
 */
@RequestMapping(value = { "/test" })
public class TestController {

    @RequestMapping(value = { "/index" })
    public String index(@RequestParam(name = "name", defaultValue = "index") String name) {
        return name;
    }
}

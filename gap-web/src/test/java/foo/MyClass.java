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
package foo;

import java.util.List;

import com.lianjita.databind.annotation.RequestParam;
import com.lianjita.databind.annotation.RequestParams;
import com.lianjita.validation.ErrorContext;

public class MyClass {

    public void nobind() {
    }

    public void bindRequestParam(@RequestParam(name = "name") String name, @RequestParam(name = "age") int age,
                                 @RequestParam(name = "addresses") List<String> addresses,
                                 @RequestParam(name = "addresses") String[] addresseArray) {
    }

    public void bindRequestParams(@RequestParams MyPOJO pojo) {

    }

    public void bindErrorContext(ErrorContext errorContext) {

    }
}

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
package com.fanrenmusic.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

/**
 * Layout ViewResuolver.
 * 
 * @author lsk.
 */
public class VelocityMultipleLayoutViewResolver extends VelocityViewResolver {

    private Map<String, String> mappings = new HashMap<String, String>();

    private String              layoutKey;

    private String              screenContentKey;

    protected Class<?> requiredViewClass() {
        return VelocityLayoutView.class;
    }

    public void setLayoutKey(final String layoutKey) {
        this.layoutKey = layoutKey;
    }

    public void setScreenContentKey(final String screenContentKey) {
        this.screenContentKey = screenContentKey;
    }

    public void setMappings(Map<String, String> mappings) {
        this.mappings = mappings;
    }

    protected AbstractUrlBasedView buildView(final String viewName) throws Exception {

        VelocityLayoutView view = (VelocityLayoutView) super.buildView(viewName);

        if (this.layoutKey != null)
            view.setLayoutKey(this.layoutKey);

        if (this.screenContentKey != null)
            view.setScreenContentKey(this.screenContentKey);

        if (!this.mappings.isEmpty()) {
            for (Map.Entry<String, String> entry : this.mappings.entrySet()) {

                String mappingRegexp = StringUtils.replace(entry.getKey(), "*", ".*");

                if (viewName.startsWith(mappingRegexp)) {

                    view.setLayoutUrl(entry.getValue());

                    return view;
                }
            }
        }

        return view;
    }

}

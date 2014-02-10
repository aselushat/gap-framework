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
package com.lianjita.web.support.factory;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author shangkun.liusk
 */
public class ControllerScanBeanDefinitionParser implements BeanDefinitionParser {

    private static final String     COMPONENT_PACKAGE          = "package";

    private static final String     DEFAULT_RESOURCE_PATTERN   = "**/*.class";

    private String                  resourcePattern            = DEFAULT_RESOURCE_PATTERN;

    private ResourcePatternResolver resourcePatternResolver    = new PathMatchingResourcePatternResolver();

    private MetadataReaderFactory   metadataReaderFactory      = new CachingMetadataReaderFactory(
                                                                       this.resourcePatternResolver);

    private static final String     CONFIG_LOCATION_DELIMITERS = ",; \t\n";

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        String[] packages = StringUtils.tokenizeToStringArray(element.getAttribute(COMPONENT_PACKAGE),
                CONFIG_LOCATION_DELIMITERS);

        BeanDefinitionRegistry registry = parserContext.getRegistry();

        try {
            Set<BeanDefinition> beanDefinitions = doScan(packages);

            for (BeanDefinition bd : beanDefinitions) {
                registry.registerBeanDefinition(bd.getBeanClassName(), bd);
            }

        } catch (IOException e) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", e);
        }

        return null;

    }

    protected Set<BeanDefinition> doScan(String... packages) throws IOException {

        Assert.noNullElements(packages, "At least one base package must be specified");

        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<BeanDefinition>();

        for (String basePackage : packages) {

            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                    + ClassUtils.convertClassNameToResourcePath(basePackage) + "/" + this.resourcePattern;

            Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);

            for (Resource resource : resources) {

                MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);

                AnnotatedGenericBeanDefinition bd = new AnnotatedGenericBeanDefinition(
                        metadataReader.getAnnotationMetadata());

                beanDefinitions.add(bd);
            }

        }
        return beanDefinitions;
    }
}

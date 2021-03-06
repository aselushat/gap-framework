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
package com.lianjita;

import org.junit.After;
import org.junit.Before;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

/**
 * Local Datastore TestCase with High Replication Datastore(HRD)
 * 
 * @author shangkun.liusk
 */
public abstract class AbstractLocalHighRepDatastoreTest {

    private final LocalDatastoreServiceTestConfig config;

    private final LocalServiceTestHelper          helper;

    public AbstractLocalHighRepDatastoreTest() {
        config = new LocalDatastoreServiceTestConfig();
        config.setDefaultHighRepJobPolicyUnappliedJobPercentage(getDefaultHighRepJobPolicyUnappliedJobPercentage());
        helper = new LocalServiceTestHelper(config);
    }

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }
   
    protected abstract float getDefaultHighRepJobPolicyUnappliedJobPercentage();
}

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
package com.lianjita.dao;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;

import org.junit.Assert;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.lianjita.AbstractLocalDatastoreTest;

/**
 * @author shangkun.liusk
 */
public class LocalDatastoreTest extends AbstractLocalDatastoreTest {

    @Test
    public void testInsert1() {
        doTest();
    }

    @Test
    public void testInsert2() {
        doTest();
    }

    // run this test twice to prove we're not leaking any state across tests
    private void doTest() {
        DatastoreService ds = getDatastoreService();
        Assert.assertEquals(0, ds.prepare(new Query("yam")).countEntities(withLimit(10)));
        ds.put(new Entity("yam"));
        ds.put(new Entity("yam"));
        Assert.assertEquals(2, ds.prepare(new Query("yam")).countEntities(withLimit(10)));
    }
}

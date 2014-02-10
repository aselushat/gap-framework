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

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.lianjita.AbstractLocalHighRepDatastoreTest;
import com.lianjita.dao.impl.UserDaoImpl;
import com.lianjita.model.User;
import com.lianjita.util.MD5Util;

/**
 * @author shangkun.liusk
 */
public class UserDaoTest extends AbstractLocalHighRepDatastoreTest {

    private UserDao userDao = new UserDaoImpl();

    @Test
    public void testAddUser() throws EntityNotFoundException {
        User user = createUser();
        Assert.assertNotNull(user.getUserId());

        User lsk = userDao.getUserById(user.getUserId());
        Assert.assertNotNull(lsk);
        Assert.assertEquals(lsk.getUsername(), "lsk");
        Assert.assertEquals(lsk.getPassword(), MD5Util.encode("123456"));
        Assert.assertEquals(lsk.getEmail(), "123@hotmail.com");
    }

    @Test
    public void testGetUserByEmail() {
        User user = createUser();
        User lsk = userDao.getUserByEmail("123@hotmail.com");
        Assert.assertNotNull(lsk);
        Assert.assertEquals(user, lsk);
    }

    @Test
    public void testGetUserById() {
        User user = createUser();
        Assert.assertNotNull(user.getUserId());
        User lsk = userDao.getUserById(user.getUserId());
        Assert.assertNotNull(lsk);
        Assert.assertEquals(user, lsk);
    }

    @Test
    public void testGetUserByNameAndPassword() {
        User user = createUser();
        Assert.assertNotNull(user.getUserId());
        User lsk = userDao.getUserByEmailAndPassword("123@hotmail.com", "123456");
        Assert.assertNotNull(lsk);
        Assert.assertEquals(user, lsk);
    }

    @Test
    public void testDeleteUser() {
        User user = createUser();
        Long userId = user.getUserId();
        Assert.assertNotNull(userId);
        userDao.deleteUser(user);
        User deleted = userDao.getUserById(userId);
        Assert.assertNull(deleted);
        deleted = userDao.getUserByEmail("lsk");
        Assert.assertNull(deleted);
        deleted = userDao.getUserByEmailAndPassword("lsk", "123456");
        Assert.assertNull(deleted);
    }

    @Test
    public void testUpdateUser() {
        User user = createUser();
        Long userId = user.getUserId();
        Assert.assertNotNull(userId);
        user.setPassword("654321");
        userDao.updateUser(user);
        User updated = userDao.getUserById(userId);
        Assert.assertNotNull(updated);
        Assert.assertEquals(updated.getPassword(), "654321");
        Assert.assertEquals(updated.getUserId(), user.getUserId());
        Assert.assertEquals(updated.getUsername(), user.getUsername());
        Objectify ofy = ObjectifyService.begin();
        int count = ofy.query(User.class).filter("username", "lsk").count();
        Assert.assertEquals(count, 1);

        int allUsers = ofy.query(User.class).count();
        Assert.assertEquals(allUsers, 1);
    }

    private User createUser() {
        User user = new User();
        user.setUsername("lsk");
        user.setPassword("123456");
        user.setEmail("123@hotmail.com");
        userDao.addUser(user);
        return user;
    }

    @Test
    public void testListUsers() {
        for (int i = 0; i < 22; i++) {
            User user = new User();
            user.setUsername("lsk" + i);
            user.setPassword("123456");
            userDao.addUser(user);
        }

        List<User> users = userDao.getUsers();
        Assert.assertNotNull(users);
        Assert.assertEquals(users.size(), 22);
    }

    @Override
    protected float getDefaultHighRepJobPolicyUnappliedJobPercentage() {
        return 1;
    }

}

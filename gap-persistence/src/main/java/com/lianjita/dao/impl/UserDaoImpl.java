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
package com.lianjita.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import com.lianjita.dao.UserDao;
import com.lianjita.model.User;
import com.lianjita.util.MD5Util;

/**
 * @author shangkun.liusk
 */
public class UserDaoImpl extends DAOBase implements UserDao {

    private final Logger logger = Logger.getLogger(getClass());

    static {
        ObjectifyService.register(User.class);
    }

    @Override
    public User getUserById(Long userId) {

        try {
            return ofy().get(User.class, userId);
        } catch (NotFoundException ex) {
            return null;
        }

    }

    @Override
    public User getUserByEmail(String email) {

        User user = ofy().query(User.class).filter("email", email).get();

        return user;

    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) {

        String md5password = MD5Util.encode(password);

        User user = ofy().query(User.class).filter("email", email).filter("password", md5password).get();

        return user;
    }

    @Override
    public void addUser(User user) {

        user.setPassword(MD5Util.encode(user.getPassword()));

        ofy().put(user);

    }

    @Override
    public void deleteUser(User user) {
        ofy().delete(user);
    }

    @Override
    public void updateUser(User user) {

        if (user == null || user.getUserId() == null) {
            logger.warn("try to update a null user or user has non userId");
            return;
        }

        ofy().put(user);

    }

    @Override
    public List<User> getUsers() {

        return ofy().query(User.class).list();

    }
};

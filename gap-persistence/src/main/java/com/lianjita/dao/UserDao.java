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

import com.lianjita.model.User;

/**
 * @author shangkun.liusk
 */
public interface UserDao {

    public User getUserById(Long userId);

    public User getUserByEmail(String email);

    public User getUserByEmailAndPassword(String email, String password);

    /**
     * 尚未分页
     * 
     * @return
     */
    public List<User> getUsers();

    public void addUser(User user);

    public void deleteUser(User user);

    public void updateUser(User user);

}

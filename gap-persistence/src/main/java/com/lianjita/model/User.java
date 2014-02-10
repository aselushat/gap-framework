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
package com.lianjita.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author shangkun.liusk
 */
@Entity
public class User implements java.io.Serializable {

    private static final long            serialVersionUID      = -1829927488509827384L;

    public static final transient String SESSION_ATTRIBUTE_KEY = User.class.getName();

    @Id
    private Long                         userId;

    private String                       username;

    private String                       email;

    private String                       password;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof User)) {
            return false;
        }

        User user = (User) obj;

        if (user.getUserId() == null || this.getUserId() == null) {
            return false;
        }

        return this.getUserId().equals(user.getUserId());
    }
}

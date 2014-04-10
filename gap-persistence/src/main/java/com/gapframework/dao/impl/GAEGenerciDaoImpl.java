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
/**
 * 
 */
package com.gapframework.dao.impl;

import java.util.List;

import com.gapframework.dao.GenericDao;

/**
 * @author lsk
 */
public class GAEGenerciDaoImpl<PK, E> implements GenericDao<PK, E> {

    @Override
    public PK create(E entity) {
        return null;
    }

    @Override
    public boolean update(E entity) {
        return false;
    }

    @Override
    public boolean delete(PK pk) {
        return false;
    }

    @Override
    public E load(PK pk) {
        return null;
    }

    @Override
    public List<E> find(Object parameters) {
        return null;
    }

    @Override
    public int count(Object parameters) {
        return 0;
    }

}

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
package com.gapframework.dao;

import java.util.List;

/**
 * @author lsk.
 */
public interface GenericDao<PK, E> {

    /**
     * 添加实体
     * 
     * @param entity 要添加的实体对象
     * @return
     */
    public PK create(E entity);

    /**
     * 保存实体
     * 
     * @param entity 要保存的实体对象
     * @return
     */
    public boolean update(E entity);

    /**
     * 根据主键删除实体
     * 
     * @param pk 主键
     * @return
     */
    public boolean delete(PK pk);

    /**
     * 根据主键返回指定的实体对象
     * 
     * @param pk 主键
     * @return
     */
    public E load(PK pk);

    /**
     * 根据sql mapping中定义的sql名称，以及传入的参数来查找实体集
     * 
     * @param parameters 参数
     * @return
     */
    public List<E> find(Object parameters);

    /**
     * 根据sql mapping中定义的sql名称，以及传入的参数来查找统计集
     * 
     * @param parameters 参数
     * @return
     */
    public int count(Object parameters);

}

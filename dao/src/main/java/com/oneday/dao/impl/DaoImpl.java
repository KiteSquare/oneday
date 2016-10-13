package com.oneday.dao.impl;

import com.oneday.dao.Dao;
import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/6 17:14
 */
@SuppressWarnings("unused")
abstract class DaoImpl<T, PK extends Serializable> implements Dao<T, PK> {

    public static final String STATEMENT_ADD = "add";
    public static final String STATEMENT_GET_BY_ID = "getById";
    public static final String STATEMENT_GET_BY_WHERE = "getByWhere";
    public static final String STATEMENT_GET = "get";
    public static final String STATEMENT_UPDATE = "update";
    public static final String STATEMENT_DELETE_BY_ID = "deleteById";
    public static final String STATEMENT_DELETE = "delete";
    public static final String STATEMENT_EXECUTE = "execute";
    public static final String STATEMENT_COUNT = "count";
    public static final String STATEMENT_SIZE = "size";

    @Resource(name = "sqlSessionTemplate")
    protected SqlSessionTemplate sqlSessionTemplate;
    @Resource(name = "batchSqlSessionTemplate")
    protected SqlSessionTemplate batchSqlSessionTemplate;

    public DaoImpl(){
        super();
    }


    public int add(T po) {
        return sqlSessionTemplate.insert(getNameSpace(STATEMENT_ADD), po);
    }

    public T get(PK id) {
        return  sqlSessionTemplate.selectOne(getNameSpace(STATEMENT_GET_BY_ID), id);
    }

    public List<T> get(T po) {
        return  sqlSessionTemplate.selectOne(getNameSpace(STATEMENT_GET_BY_WHERE), po);
    }

    public List<T> list(T po) {
        return sqlSessionTemplate.selectList(getNameSpace(STATEMENT_GET), po);

    }

    public int update(T po) {
        return sqlSessionTemplate.update(getNameSpace(STATEMENT_UPDATE), po);
    }

    public int del(PK id) {
        return sqlSessionTemplate.delete(getNameSpace(STATEMENT_DELETE_BY_ID), id);
    }

    public int del(T po) {
        return sqlSessionTemplate.delete(getNameSpace(STATEMENT_DELETE), po);
    }

    public int execute(String sql) {
        return sqlSessionTemplate.update(getNameSpace(STATEMENT_EXECUTE), sql);
    }

    public long count(T po) {
        return sqlSessionTemplate.selectOne(getNameSpace(STATEMENT_COUNT), po);
    }

    public long size() {
        return sqlSessionTemplate.selectOne(getNameSpace(STATEMENT_SIZE));
    }

    public abstract String getNameSpace(String var1);
}
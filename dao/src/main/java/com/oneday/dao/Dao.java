package com.oneday.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/6 17:05
 */
public interface Dao<T, PK extends Serializable> {

    /**
     * 记录添加（所有字段入库，效率中）
     * @param po
     * @return
     */
    int add(T po);

    /**
     * 通过主键获取某个记录
     * @param id 主键
     * @return PO
     */
    T get(PK id);

    /**
     * 条件获取记录
     * @param t
     * @return
     */
    List<T> get(T t);


    /**
     * 更新PO的所有字段
     * @param po
     * @return 受影响的行数
     */
    int update(T po);

    /**
     * 删除某个记录
     * @param id 主键
     * @return 受影响的行数
     */
    int del(PK id);

    /**
     * 条件删除某个记录
     * @param po 条件
     * @return 受影响的行数
     */
    int del(T po);

    /**
     * 执行自定义sql
     * @param sql 用于执行的Sql
     * @return 受影响的行数
     */
    int execute(String sql);

    /**
     * 获取指定条件的记录数
     * @param po 条件
     * @return 查询到的记录数
     */
    long count(T po);

    /**
     * 获取对应表中的记录数
     * @return 表中的条数
     */
    long size();

}
package com.dzytsiuk.dbdeveloper.dao;


import com.dzytsiuk.dbdeveloper.entity.ResultSetData;


public interface DAO {
    boolean create(String query);

    ResultSetData select(String query);

    int insert(String query);

    int update(String query);

    int delete(String query);

    boolean drop(String query);
}

package com.dzytsiuk.dbdeveloper.dao;


import com.dzytsiuk.dbdeveloper.entity.Data;


public interface QueryDao {
    boolean create(String query);

    Data select(String query);

    int insert(String query);

    int update(String query);

    int delete(String query);

    boolean drop(String query);
}

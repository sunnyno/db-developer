package com.dzytsiuk.dbdeveloper.service;

import com.dzytsiuk.dbdeveloper.dao.DAO;
import com.dzytsiuk.dbdeveloper.entity.ResultSetData;

public interface QueryMessageService {
    String create(String query);

    ResultSetData select(String query);

    String insert(String query);

    String update(String query);

    String delete(String query);

    String drop(String query);

    void setDao(DAO dao);
}

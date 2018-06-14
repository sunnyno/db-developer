package com.dzytsiuk.dbdeveloper.service;

import com.dzytsiuk.dbdeveloper.dao.DAO;
import com.dzytsiuk.dbdeveloper.entity.ResultSetData;

public class DefaultQueryMessageService implements QueryMessageService {
    private DAO dao;


    @Override
    public String create(String query) {
        return dao.create(query) ? "Table created\n" : "Error creating table\n";
    }

    @Override
    public ResultSetData select(String query) {
        return dao.select(query);
    }

    @Override
    public String insert(String query) {
        int affectedRows = dao.insert(query);
        return affectedRows + (affectedRows == 1 ? " row" : " rows") + " inserted\n";
    }

    @Override
    public String update(String query) {
        int affectedRows = dao.update(query);
        return affectedRows + (affectedRows == 1 ? " row" : " rows") + " updated\n";
    }

    @Override
    public String delete(String query) {
        int affectedRows = dao.delete(query);
        return affectedRows + (affectedRows == 1 ? " row" : " rows") + " deleted\n";
    }

    @Override
    public String drop(String query) {
        return dao.drop(query) ? "Table dropped\n" : "Error dropping table\n";
    }

    @Override
    public void setDao(DAO dao) {
        this.dao = dao;
    }
}

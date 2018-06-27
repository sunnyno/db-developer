package com.dzytsiuk.dbdeveloper.service;

import com.dzytsiuk.dbdeveloper.dao.QueryDao;
import com.dzytsiuk.dbdeveloper.entity.Data;
import com.dzytsiuk.dbdeveloper.entity.Result;
import com.dzytsiuk.dbdeveloper.exception.QueryExecuteException;
import com.dzytsiuk.dbdeveloper.locator.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

public class QueryMessageService {
    private static final String SELECT = "select";
    private static final String INSERT = "insert";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String CREATE = "create";
    private static final String DROP = "drop";

    private QueryDao queryDao;

    public QueryMessageService() {
        queryDao = (QueryDao) ServiceLocator.get("queryDao");
    }

    public List<Result> execute(String[] queries) {
        List<Result> results = new ArrayList<>();
        for (String query : queries) {
            Result result = new Result();

            query = query.toLowerCase().trim();
            try {
                if (query.contains(SELECT)) {
                    Data data = queryDao.select(query);

                    result.setData(data);
                    List<List<String>> rows = data.getData();
                    result.setHasData(rows != null);
                    int affectedRows = (rows != null) ? rows.size() : 0;

                    result.setMessage(affectedRows + (affectedRows == 1 ? " row" : " rows") + " fetched\n");

                } else {
                    result.setMessage(applyFunction(query));
                    result.setHasData(false);
                }
            } catch (QueryExecuteException e) {
                result.setHasData(false);
                result.setMessage(e.getMessage() + (e.getCause() != null ? " :\n " + e.getCause().getMessage() : "") + "\n");
            }
            results.add(result);

        }
        return results;
    }


    String applyFunction(String query) {

        if (query.contains(INSERT)) {
            Integer affectedRows = queryDao.insert(query);
            return affectedRows + (affectedRows == 1 ? " row" : " rows") + " inserted\n";
        } else if (query.contains(UPDATE)) {
            Integer affectedRows = queryDao.update(query);
            return affectedRows + (affectedRows == 1 ? " row" : " rows") + " updated\n";
        } else if (query.contains(DELETE)) {
            Integer affectedRows = queryDao.delete(query);
            return affectedRows + (affectedRows == 1 ? " row" : " rows") + " deleted\n";
        } else if (query.contains(CREATE)) {
            Boolean result = queryDao.create(query);
            return result ? "Created\n" : "Error creating \n";
        } else if (query.contains(DROP)) {
            Boolean result = queryDao.drop(query);
            return result ? "Dropped\n" : "Error dropping \n";
        } else {
            return "Unable to parse query\n";
        }
    }


}

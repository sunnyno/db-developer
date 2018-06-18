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
    private static final int QUERY_TYPE_WORD_INDEX = 0;

    private QueryDao queryDao;

    public QueryMessageService() {
        queryDao = (QueryDao) ServiceLocator.get("queryDao");
    }

    public List<Result> execute(String[] queries) {
        List<Result> results = new ArrayList<>();
        for (String query : queries) {
            Result result = new Result();

            String queryType = query.replace("\n", "").split("\\s")[QUERY_TYPE_WORD_INDEX];
            try {
                if (queryType.equalsIgnoreCase(SELECT)) {
                    Data data = queryDao.select(query);
                    result.setData(data);
                    result.setHasData(true);
                    int affectedRows = data.getData().size();
                    result.setMessage(affectedRows + (affectedRows == 1 ? " row" : " rows") + " fetched\n");
                } else {
                    result.setMessage(applyFunction(queryType, query));
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


    String applyFunction(String queryType, String query) {

        if (queryType.equalsIgnoreCase(INSERT)) {
            Integer affectedRows = queryDao.insert(query);
            return affectedRows + (affectedRows == 1 ? " row" : " rows") + " inserted\n";
        } else if (queryType.equalsIgnoreCase(UPDATE)) {
            Integer affectedRows = queryDao.update(query);
            return affectedRows + (affectedRows == 1 ? " row" : " rows") + " updated\n";
        } else if (queryType.equalsIgnoreCase(DELETE)) {
            Integer affectedRows = queryDao.delete(query);
            return affectedRows + (affectedRows == 1 ? " row" : " rows") + " deleted\n";
        } else if (queryType.equalsIgnoreCase(CREATE)) {
            Boolean result = queryDao.create(query);
            return result ? "Table created\n" : "Error creating table\n";
        } else if (queryType.equalsIgnoreCase(DROP)) {
            Boolean result = queryDao.drop(query);
            return result ? "Table dropped\n" : "Error dropping table\n";
        } else {
            return "Unable to parse query\n";
        }


    }


}

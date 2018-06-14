package com.dzytsiuk.dbdeveloper.handler;

import com.dzytsiuk.dbdeveloper.exception.QueryExecuteException;
import com.dzytsiuk.dbdeveloper.dao.jdbc.JdbcDao;
import com.dzytsiuk.dbdeveloper.datasource.DataSourceProvider;
import com.dzytsiuk.dbdeveloper.entity.ResultSetData;
import com.dzytsiuk.dbdeveloper.service.DefaultQueryMessageService;
import com.dzytsiuk.dbdeveloper.service.QueryMessageService;

import java.util.Properties;
import java.util.function.Function;

public class RequestHandler {
    private static final String SELECT = "select";
    private static final String INSERT = "insert";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String CREATE = "create";
    private static final String DROP = "drop";
    private static final int QUERY_TYPE_WORD_INDEX = 0;


    private QueryMessageService queryMessageService;

    private ResponseWriter responseWriter;

    public RequestHandler(Properties properties) {
        queryMessageService = new DefaultQueryMessageService();
        queryMessageService.setDao(new JdbcDao(new DataSourceProvider().getDataSource(properties)));
    }

    RequestHandler() {
    }

    public void execute(String[] queries) {
        for (String query : queries) {
            try {
                String queryType = query.replace("\n", "").split("\\s")[QUERY_TYPE_WORD_INDEX];
                if (queryType.equalsIgnoreCase(SELECT)) {
                    ResultSetData data = queryMessageService.select(query);
                    responseWriter.writeSelectResult(data);
                } else {
                    Function<String, String> function = getFunction(queryType);
                    responseWriter.writeResponse(function.apply(query));
                }
            } catch (QueryExecuteException e) {
                responseWriter.writeResponse(e.getMessage() + (e.getCause() != null ? " :\n " + e.getCause().getMessage() : "") + "\n");
            }

        }
    }

    //for tests
    Function<String, String> getFunction(String queryType) {
        if (queryType.equalsIgnoreCase(INSERT)) {
            return queryMessageService::insert;
        }
        if (queryType.equalsIgnoreCase(UPDATE)) {
            return queryMessageService::update;
        }
        if (queryType.equalsIgnoreCase(DELETE)) {
            return queryMessageService::delete;
        }
        if (queryType.equalsIgnoreCase(CREATE)) {
            return queryMessageService::create;
        }
        if (queryType.equalsIgnoreCase(DROP)) {
            return queryMessageService::drop;
        }
        throw new QueryExecuteException("Unable to parse query");
    }


    public void setResponseWriter(ResponseWriter responseWriter) {
        this.responseWriter = responseWriter;
    }

    public void setQueryMessageService(QueryMessageService queryMessageService) {
        this.queryMessageService = queryMessageService;
    }
}

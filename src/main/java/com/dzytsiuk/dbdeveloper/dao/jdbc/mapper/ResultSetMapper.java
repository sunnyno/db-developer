package com.dzytsiuk.dbdeveloper.dao.jdbc.mapper;

import com.dzytsiuk.dbdeveloper.entity.Data;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultSetMapper {

    public Data mapResultSet(ResultSet resultSet) {
        try {
            Data data = new Data();
            data.setColumnNames(new ArrayList<>());
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            for (int i = 0; i < rsMetaData.getColumnCount(); i++) {
                String columnName = rsMetaData.getColumnName(i + 1);
                data.getColumnNames().add(columnName);
            }
            data.setData(new ArrayList<>());

            while (resultSet.next()) {
                List<String> row = new ArrayList<>();
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                    row.add(resultSet.getString(i));
                }
                data.getData().add(row);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping data",e);
        }
    }
}




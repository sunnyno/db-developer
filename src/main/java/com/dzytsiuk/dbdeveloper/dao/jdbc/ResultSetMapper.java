package com.dzytsiuk.dbdeveloper.dao.jdbc;

import com.dzytsiuk.dbdeveloper.entity.ResultSetData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResultSetMapper {

    public ResultSetData mapResultSet(ResultSet resultSet) {
        try {
            ResultSetData resultSetData = new ResultSetData();
            resultSetData.setColumnNames(new ArrayList<>());
            for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                String columnName = metaData.getColumnName(i + 1);
                resultSetData.getColumnNames().add(columnName);
            }
            resultSetData.setData(FXCollections.observableArrayList());

            while (resultSet.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    row.add(resultSet.getString(i));
                }
                resultSetData.getData().add(row);
            }
            return resultSetData;
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping data");
        }
    }
}




package com.dzytsiuk.dbdeveloper.entity;

import javafx.collections.ObservableList;

import java.util.List;
import java.util.Objects;

public class ResultSetData {
    private List<String> columnNames;
    private ObservableList<ObservableList<String>> data;

    public ObservableList<ObservableList<String>> getData() {
        return data;
    }

    public void setData(ObservableList<ObservableList<String>> data) {
        this.data = data;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultSetData that = (ResultSetData) o;
        return Objects.equals(columnNames, that.columnNames) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {

        return Objects.hash(columnNames, data);
    }
}

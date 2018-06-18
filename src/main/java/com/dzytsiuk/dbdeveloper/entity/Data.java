package com.dzytsiuk.dbdeveloper.entity;

import java.util.List;
import java.util.Objects;

public class Data {

    private List<String> columnNames;

    private List<List<String>> data;

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
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
        Data that = (Data) o;
        return Objects.equals(columnNames, that.columnNames) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {

        return Objects.hash(columnNames, data);
    }
}

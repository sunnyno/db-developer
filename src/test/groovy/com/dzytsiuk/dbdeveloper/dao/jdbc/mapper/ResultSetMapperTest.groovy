package com.dzytsiuk.dbdeveloper.dao.jdbc.mapper

import com.dzytsiuk.dbdeveloper.dao.jdbc.mapper.mapper.ResultSetMapper
import com.dzytsiuk.dbdeveloper.entity.Data
import javafx.collections.FXCollections
import org.junit.Test

import java.sql.ResultSet
import java.sql.ResultSetMetaData

import static org.junit.Assert.assertEquals;


class ResultSetMapperTest {

    @Test
    void mapResultSetTest() {
        def data = FXCollections.observableArrayList()
        Data expectedResultSetData = new Data(columnNames: ["id"], data: data);
        def rsMetaDataMock = [getColumnCount: { 1 }, getColumnName: { column -> "id" }] as ResultSetMetaData
        def rsmock = [
                getMetaData: { rsMetaDataMock },
                next       : { false },
                getString  : { "1" }] as ResultSet
        def actualResultSetData = new ResultSetMapper().mapResultSet(rsmock)
        assertEquals(expectedResultSetData, actualResultSetData)

    }
}

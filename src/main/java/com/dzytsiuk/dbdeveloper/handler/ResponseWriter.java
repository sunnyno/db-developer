package com.dzytsiuk.dbdeveloper.handler;

import com.dzytsiuk.dbdeveloper.entity.ResultSetData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.util.Callback;

import java.util.List;

public class ResponseWriter {
    private TextArea result;

    private TableView selectResult;

    public ResponseWriter(TextArea result, TableView selectResult) {
        this.result = result;
        this.selectResult = selectResult;

    }

    public void writeSelectResult(ResultSetData resultSetData) {
        selectResult.getColumns().clear();
        ObservableList<ObservableList<String>> data = resultSetData.getData();

        List<String> columnNames = resultSetData.getColumnNames();
        for (int i = 0; i < columnNames.size(); i++) {

            final int j = i;
            TableColumn col = new TableColumn(columnNames.get(i));
            col.setCellValueFactory((Callback<CellDataFeatures<ObservableList, String>,
                    ObservableValue<String>>) param ->
                    new SimpleStringProperty(param.getValue().get(j).toString()));

            selectResult.getColumns().addAll(col);
        }
        selectResult.setItems(data);
        int size = data.size();
        result.appendText(size + (size == 1 ? " row" : " rows") + " fetched\n");
    }

    public void writeResponse(String message) {
        result.appendText(message);
    }


}

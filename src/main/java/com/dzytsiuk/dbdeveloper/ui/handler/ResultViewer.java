package com.dzytsiuk.dbdeveloper.ui.handler;

import com.dzytsiuk.dbdeveloper.entity.Data;
import com.dzytsiuk.dbdeveloper.entity.Result;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.util.Callback;

import java.util.List;

public class ResultViewer {
    private TextArea result;

    private TableView selectResult;

    public ResultViewer(TextArea result, TableView selectResult) {
        this.result = result;
        this.selectResult = selectResult;

    }

    public void writeSelectResult(Data data) {
        selectResult.getColumns().clear();
        ObservableList<ObservableList<String>> observableData = FXCollections.observableArrayList();
        for (int i = 0; i < data.getData().size(); i++) {
            List<String> col = data.getData().get(i);
            observableData.add(FXCollections.observableArrayList(col));
        }

        List<String> columnNames = data.getColumnNames();
        for (int i = 0; i < columnNames.size(); i++) {

            final int j = i;
            TableColumn col = new TableColumn(columnNames.get(i));
            col.setCellValueFactory((Callback<CellDataFeatures<ObservableList, String>,
                    ObservableValue<String>>) param ->
                    new SimpleStringProperty(param.getValue().get(j).toString()));

            selectResult.getColumns().addAll(col);
        }
        selectResult.setItems(observableData);
    }

    public void writeResponse(String message) {
        result.appendText(message);
    }


    public void viewResult(List<Result> resultList) {
        for (Result result : resultList) {
            if (result.hasData()) {
                writeSelectResult(result.getData());
            }
            writeResponse(result.getMessage());

        }
    }
}

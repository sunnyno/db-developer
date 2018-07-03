package com.dzytsiuk.dbdeveloper.ui.control;

import com.dzytsiuk.dbdeveloper.Starter;
import com.dzytsiuk.dbdeveloper.entity.Result;
import com.dzytsiuk.dbdeveloper.locator.ServiceLocator;
import com.dzytsiuk.dbdeveloper.service.QueryMessageService;
import com.dzytsiuk.dbdeveloper.ui.handler.ResultViewer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Controller {
    @FXML
    private TextArea query;

    @FXML
    private TextArea result;

    @FXML
    private TableView selectResult;

    @FXML
    private MenuItem paste;

    @FXML
    private Label database;


    private ResultViewer resultViewer;
    private final Clipboard clipboard = Clipboard.getSystemClipboard();
    private final ClipboardContent content = new ClipboardContent();
    private Properties properties;
    private QueryMessageService queryMessageService;

    public Controller() {

    }


    @FXML
    private void initialize() {
        result.setEditable(false);
        query.setEditable(false);
        ServiceLocator.registerService(ResultViewer.class, new ResultViewer(result, selectResult));
    }

    @FXML
    private void executeQuery() {
        if (properties != null) {
            String queryText = getSelectedText();
            String[] queriesToExecute = ((queryText == null) ? query.getText() : queryText).trim().split(";");
            List<Result> resultList = queryMessageService.execute(queriesToExecute);
            resultViewer.viewResult(resultList);
        }
    }

    @FXML
    private void getPropertyFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Property File");
        fileChooser.setInitialDirectory(new File(getClass().getResource("/properties/").getFile()));
        try (FileInputStream inStream = new FileInputStream(fileChooser.showOpenDialog(new Stage()))) {
            properties = new Properties();
            properties.load(inStream);

            Starter.registerServices(properties);

            database.setText(properties.getProperty("database"));
            query.setEditable(true);
            queryMessageService = ServiceLocator.get(QueryMessageService.class);
            resultViewer = ServiceLocator.get(ResultViewer.class);
            query.getScene().setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER && event.isControlDown()) {
                    executeQuery();
                }
            });

        } catch (IOException e) {
            resultViewer.writeResponse("Failed to load properties\n");
        }

    }

    private String getSelectedText() {
        if (StringUtils.isNotEmpty(query.getSelectedText())) {
            return query.getSelectedText();
        }
        return null;
    }

    @FXML
    private void copy() {
        String copyText = getSelectedText();
        content.putString(copyText);
        clipboard.setContent(content);
    }

    private TextArea getFocusedTextField() {
        if (query.isFocused()) {
            return query;
        }
        return null;
    }

    @FXML
    public void cut() {
        TextArea focusedTF = getFocusedTextField();
        String text = focusedTF.getSelectedText();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);

        IndexRange range = focusedTF.getSelection();
        String origText = focusedTF.getText();
        String firstPart = StringUtils.substring(origText, 0, range.getStart());
        String lastPart = StringUtils.substring(origText, range.getEnd(), StringUtils.length(origText));
        focusedTF.setText(firstPart + lastPart);
        focusedTF.positionCaret(range.getStart());

    }

    @FXML
    public void paste() {
        if (!clipboard.hasContent(DataFormat.PLAIN_TEXT)) {
            paste.setDisable(true);
            return;
        }

        String clipboardText = clipboard.getString();
        TextArea focusedTF = getFocusedTextField();
        IndexRange range = focusedTF.getSelection();
        String origText = focusedTF.getText();
        int endPos = 0;
        String updatedText = "";
        String firstPart = StringUtils.substring(origText, 0, range.getStart());
        String lastPart = StringUtils.substring(origText, range.getEnd(), StringUtils.length(origText));

        updatedText = firstPart + clipboardText + lastPart;

        if (range.getStart() == range.getEnd()) {
            endPos = range.getEnd() + StringUtils.length(clipboardText);
        } else {
            endPos = range.getStart() + StringUtils.length(clipboardText);
        }

        focusedTF.setText(updatedText);
        focusedTF.positionCaret(endPos);
    }

    @FXML
    private void quit() {
        Stage stage = (Stage) query.getScene().getWindow();
        stage.close();
    }

}

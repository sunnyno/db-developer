package com.dzytsiuk.dbdeveloper.control;

import com.dzytsiuk.dbdeveloper.handler.RequestHandler;
import com.dzytsiuk.dbdeveloper.handler.ResponseWriter;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

    private ResponseWriter responseWriter;
    private final Clipboard clipboard = Clipboard.getSystemClipboard();
    private final ClipboardContent content = new ClipboardContent();
    private Properties properties;

    public Controller() {

    }


    @FXML
    private void initialize() {
        result.setEditable(false);
        query.setEditable(false);
        responseWriter = new ResponseWriter(result, selectResult);

    }

    @FXML
    private void executeQuery() {
        if (properties != null) {
            RequestHandler requestHandler = new RequestHandler(properties);
            requestHandler.setResponseWriter(responseWriter);
            String queryText = getSelectedText();
            requestHandler.execute(((queryText == null) ? query.getText() : queryText).trim().split(";"));
        }
    }

    @FXML
    private void getPropertyFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Property File");
        fileChooser.setInitialDirectory(new File("src/main/resources"));
        try {
            properties = new Properties();
            properties.load(new FileInputStream(fileChooser.showOpenDialog(new Stage())));
            database.setText(properties.getProperty("database"));
            query.setEditable(true);
        } catch (IOException e) {
            responseWriter.writeResponse("Failed to load properties");
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

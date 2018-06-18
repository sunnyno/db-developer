package com.dzytsiuk.dbdeveloper;

import com.dzytsiuk.dbdeveloper.dao.jdbc.mapper.DataSourceProvider;
import com.dzytsiuk.dbdeveloper.dao.jdbc.mapper.JdbcQueryDao;
import com.dzytsiuk.dbdeveloper.locator.ServiceLocator;
import com.dzytsiuk.dbdeveloper.service.QueryMessageService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.sql.DataSource;
import java.io.File;
import java.net.URL;
import java.util.Properties;

public class Starter extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        URL url = new File("src/main/resources/fxml/main.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("DB Developer");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();

    }

    public static void registerServices(Properties properties) {
        DataSource dataSource = new DataSourceProvider().getDataSource(properties);
        ServiceLocator.registerService("queryDao", new JdbcQueryDao(dataSource));
        ServiceLocator.registerService("queryMessageService", new QueryMessageService());
    }


    public static void main(String[] args) {
        launch(args);
    }


}

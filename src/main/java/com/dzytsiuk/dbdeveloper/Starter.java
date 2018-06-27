package com.dzytsiuk.dbdeveloper;

import com.dzytsiuk.dbdeveloper.dao.customdb.CustomDataSource;
import com.dzytsiuk.dbdeveloper.dao.customdb.CustomQueryDao;
import com.dzytsiuk.dbdeveloper.dao.jdbc.DataSourceProvider;
import com.dzytsiuk.dbdeveloper.dao.jdbc.JdbcQueryDao;
import com.dzytsiuk.dbdeveloper.locator.ServiceLocator;
import com.dzytsiuk.dbdeveloper.service.QueryMessageService;
import com.mysql.cj.jdbc.MysqlDataSource;
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
        if (dataSource instanceof MysqlDataSource) {
            ServiceLocator.registerService("queryDao", new JdbcQueryDao(dataSource));
        } else {
            CustomDataSource customDataSource = new CustomDataSource(properties);
            ServiceLocator.registerService("customDataSource", customDataSource);
            ServiceLocator.registerService("queryDao", new CustomQueryDao(
                    customDataSource));
        }
        System.out.println("registered");
        ServiceLocator.registerService("queryMessageService", new QueryMessageService());

    }


    public static void main(String[] args) {
        try {
            launch(args);
        } finally {
            CustomDataSource customDataSource = (CustomDataSource) ServiceLocator.get("customDataSource");
            if (customDataSource != null) {
                customDataSource.close();
            }
            System.out.println("disconnected");
        }

    }


}

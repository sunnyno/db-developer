package com.dzytsiuk.dbdeveloper;

import com.dzytsiuk.dbdeveloper.dao.QueryDao;
import com.dzytsiuk.dbdeveloper.dao.jdbc.JdbcQueryDao;
import com.dzytsiuk.dbdeveloper.locator.ServiceLocator;
import com.dzytsiuk.dbdeveloper.service.QueryMessageService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Properties;

public class Starter extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        URL uiUrl = getClass().getResource("/fxml/main.fxml");
        Parent root = FXMLLoader.load(uiUrl);
        primaryStage.setTitle("DB Developer");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();

    }

    public static void registerServices(Properties properties) {
        try {
            Class.forName("com.dzytsuik.dbconnector.driver.Driver");
            ServiceLocator.registerService(QueryDao.class, new JdbcQueryDao(properties));
            ServiceLocator.registerService(QueryMessageService.class, new QueryMessageService());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to register DB Driver");
        }

    }


    public static void main(String[] args) {

        launch(args);

    }


}

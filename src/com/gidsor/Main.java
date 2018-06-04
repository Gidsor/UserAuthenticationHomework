package com.gidsor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("Authentication");
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"))));
        window.show();
    }

    public static void setWindowScene(Scene scene, String title) {
        window.setTitle(title);
        window.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}


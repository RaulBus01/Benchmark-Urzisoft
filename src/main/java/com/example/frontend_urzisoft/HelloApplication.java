package com.example.frontend_urzisoft;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {

    public static Scene scene;

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        scene = new Scene(fxmlLoader.load(), 1280, 720);

        stage.setTitle("Benchmark Application");
        Image iconApp = new Image(getClass().getResource("/com/example/frontend_urzisoft/png/logo_file.jpg").toExternalForm());
        stage.getIcons().add(iconApp);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
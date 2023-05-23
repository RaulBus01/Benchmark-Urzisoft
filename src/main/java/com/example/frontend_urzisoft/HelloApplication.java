package com.example.frontend_urzisoft;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class HelloApplication extends Application {

    public static Scene scene;
    public HelloController helloController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        scene = new Scene(fxmlLoader.load(), 1280, 720);

        stage.setTitle("Benchmark Application");
        Image iconApp = new Image(getClass().getResource("/com/example/frontend_urzisoft/png/logo_file.jpg").toExternalForm());
        stage.getIcons().add(iconApp);


        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        helloController = fxmlLoader.getController();
        boolean disable = true;
        helloController.changePanesView(disable);
        PauseTransition delay = new PauseTransition(Duration.millis(4890));
        delay.setOnFinished(event -> {
            try {

                helloController.onHomeButtonClicked();
                helloController.changePanesView(!disable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        delay.play();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
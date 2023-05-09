package com.example.frontend_urzisoft.ui;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

public class UILoading {


    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public Label getTaskText() {
        return taskText;
    }

    public Label getTotalScoreText() {
        return totalScoreText;
    }

    public Label getSingleScoreText() {
        return singleScoreText;
    }

    public Label getMultiScoreText() {
        return multiScoreText;
    }

    private ProgressBar progressBar;
    private Label taskText;
    private Label totalScoreText;
    private Label singleScoreText;
    private Label multiScoreText;
    public UILoading()
    {
        progressBar = new ProgressBar(0);
        taskText  = new Label();
        totalScoreText = new Label();
        singleScoreText = new Label();
        multiScoreText = new Label();

    }
    public void setUI(AnchorPane side)
    {
        progressBar.setPrefWidth(250);
        progressBar.setPrefHeight(20);
        progressBar.setLayoutX(205);
        progressBar.setLayoutY(270);

        taskText.setStyle("-fx-font-size: 18px; -fx-text-fill: #C2C0A6;");
        totalScoreText.setStyle("-fx-font-size: 18px; -fx-text-fill: #C2C0A6;");
        singleScoreText.setStyle("-fx-font-size: 18px; -fx-text-fill: #C2C0A6;");
        multiScoreText.setStyle("-fx-font-size: 18px; -fx-text-fill: #C2C0A6;");

        singleScoreText.setLayoutX(85);
        singleScoreText.setLayoutY(150);

        multiScoreText.setLayoutX(430);
        multiScoreText.setLayoutY(150);

        totalScoreText.setLayoutX(280);
        totalScoreText.setLayoutY(320);


        taskText.setLayoutX(220);
        taskText.setLayoutY(235);


        side.getChildren().add(progressBar);
        side.getChildren().add(taskText);
        progressBar.setStyle("-fx-accent: #2D3E40;");









    }

}

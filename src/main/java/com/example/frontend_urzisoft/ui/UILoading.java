package com.example.frontend_urzisoft.ui;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

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

    public TextField getDialog() {
        return dialog;
    }
    public Label getDialogText() {
        return dialogText;
    }
    public Button getDialogButton() {
        return dialogButton;
    }


    private ProgressBar progressBar;
    private Label taskText;
    private Label totalScoreText;
    private Label singleScoreText;
    private Label multiScoreText;

  private TextField dialog;
  private Label dialogText;
  private Button dialogButton;



    public UILoading()
    {
        progressBar = new ProgressBar(0);
        taskText  = new Label();
        totalScoreText = new Label();
        singleScoreText = new Label();
        multiScoreText = new Label();
        dialog = new TextField();
        dialogText = new Label();
        dialogButton = new Button("Save");

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
        dialogText.setStyle("-fx-font-size: 18px; -fx-text-fill: #C2C0A6;");

        singleScoreText.setLayoutX(85);
        singleScoreText.setLayoutY(150);

        multiScoreText.setLayoutX(430);
        multiScoreText.setLayoutY(150);

        totalScoreText.setLayoutX(280);
        totalScoreText.setLayoutY(320);


        taskText.setLayoutX(220);
        taskText.setLayoutY(235);

        dialog.setLayoutX(225);
        dialog.setLayoutY(40);
        dialog.setPrefWidth(200);
        dialog.setPrefHeight(25);
        dialog.setVisible(false);

        dialog.setStyle("-fx-background-color: #2D3E40; -fx-text-fill: #C2C0A6; -fx-font-size: 18px; -fx-border-color: #C2C0A6; -fx-border-width: 1px; -fx-border-radius: 5px;");
        dialogText.setLayoutX(190);
        dialogText.setLayoutY(80);

        dialogButton.setStyle("-fx-background-color: #2D3E40; -fx-text-fill: #C2C0A6; -fx-font-size: 14px; -fx-border-color: #C2C0A6; -fx-border-width: 1px; -fx-border-radius: 5px;");
        dialogButton.setLayoutX(435);
        dialogButton.setLayoutY(45);
        dialogButton.setPrefWidth(60);
        dialogButton.setPrefHeight(25);


       dialogText.setText("Enter your name and save your score!");
       dialogText.setVisible(false);
         dialogButton.setVisible(false);




        side.getChildren().add(progressBar);
        side.getChildren().add(taskText);
        side.getChildren().add(dialog);
        side.getChildren().add(dialogText);
        side.getChildren().add(dialogButton);

        progressBar.setStyle("-fx-accent: #2D3E40;");









    }

}

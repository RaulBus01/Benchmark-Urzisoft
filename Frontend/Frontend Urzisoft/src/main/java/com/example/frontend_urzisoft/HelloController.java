package com.example.frontend_urzisoft;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

import com.example.frontend_urzisoft.HardwareDetails.GetDetailsHardware;
public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button homeButton,CPUTestButton,RAMTestButton,LeaderboardButton,ExitButton;

    @FXML
    private AnchorPane borderpane;



    @FXML
    protected void onHomeButtonClicked() throws IOException
    {
            AnchorPane sidePane = FXMLLoader.load(getClass().getResource("Home-view.fxml"));
            GetDetailsHardware hardware = new GetDetailsHardware();
            hardware.getHardwareInfo();
            sidePane.setMaxSize(1280,720);
            sidePane.setMinSize(1280,720);
            sidePane.setPrefSize(1280,720);
            borderpane.getChildren().setAll(sidePane);



    }
    @FXML
    protected void onCPUTestButtonClicked()
    {

    }
}
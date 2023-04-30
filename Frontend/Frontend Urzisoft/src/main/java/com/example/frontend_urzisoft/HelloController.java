package com.example.frontend_urzisoft;

import com.example.frontend_urzisoft.HardwareDetails.RAM;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

import com.example.frontend_urzisoft.HardwareDetails.HardwareDetails;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

public class HelloController {
    @FXML
    private Text SY_OS = new Text();
    private Pane SY = new Pane();

    @FXML
    private Button homeButton,CPUTestButton,RAMTestButton,LeaderboardButton,ExitButton;

    @FXML
    private AnchorPane borderpane;




    @FXML
    private void SetSystemInformation(AnchorPane parent)
    {

        int bank = 0;

        HardwareDetails hardware = new HardwareDetails();
        hardware.getHardwareInfo();

        Pane SY =(Pane) parent.lookup("#SY"),
             RAM_Info = (Pane) parent.lookup("#RAM_Info"),
                CPU_Info = (Pane) parent.lookup("#CPU_Info");

        Text SY_OS = (Text) SY.lookup("#SY_OS"); SY_OS.setWrappingWidth(125);
        Text SY_Model = (Text) SY.lookup("#SY_Model"); SY_Model.setWrappingWidth(200);
        Text SY_Motherboard = (Text) SY.lookup("#SY_Motherboard"); SY_Motherboard.setWrappingWidth(150);

        Text CPU_Name = (Text) CPU_Info.lookup("#CPU_Name"); CPU_Name.setWrappingWidth(215); CPU_Name.setLayoutX(90);
        Text CPU_Physical_Processors = (Text) CPU_Info.lookup("#CPU_Physical_Processors");
        Text CPU_Logical_Processors = (Text) CPU_Info.lookup("#CPU_Logical_Processors");

        Text RAM_Manufacturer = (Text) RAM_Info.lookup("#RAM_Manufacturer"); RAM_Manufacturer.setWrappingWidth(155);
        Text RAM_Capacity = (Text) RAM_Info.lookup("#RAM_Capacity"); RAM_Capacity.setWrappingWidth(155);
        Text RAM_Frequency = (Text) RAM_Info.lookup("#RAM_Frequency"); RAM_Frequency.setWrappingWidth(155);
        Text RAM_Type = (Text) RAM_Info.lookup("#RAM_Type"); RAM_Type.setWrappingWidth(155);



        Button bankButton = (Button) RAM_Info.lookup("#bank_btn");



        ContextMenu bankMenu = new ContextMenu();
        bankMenu.setHideOnEscape(true);


        bankMenu.setStyle("-fx-background-color:#2D3E40;-fx-font-size: 16px;-fx-background-radius: 5px; -fx-text-alignment: center; -fx-padding: 5px; -fx-border-color: #2D3E40; -fx-border-radius: 5px; -fx-border-width: 2px; -fx-text-fill: white;");

        if(hardware.getRamList().size() > 1) {
            for(int i = 0; i < hardware.getRamList().size(); i++) {
                final int index = i;
                MenuItem item = new MenuItem("Bank " + (i + 1));
                item.setStyle(" -fx-text-fill: #e4f2e7;"  );
                item.setOnAction(event -> {
                    RAM ram = hardware.getRamList().get(index);
                    bankButton.setText("Bank "+(index+1));
                    RAM_Manufacturer.setText(ram.getManufacturer());
                    RAM_Capacity.setText(ram.getCapacity());
                    RAM_Frequency.setText(ram.getFrequency());
                    RAM_Type.setText(ram.getMemoryType());
                });
                bankMenu.getItems().add(item);
            }
            bankButton.setDisable(false);
        } else {
            bankButton.setDisable(true);
        }

        bankButton.setOnAction(event -> {
            bankMenu.show(bankButton, Side.RIGHT, 0, 0);
        });

        bankButton.setContextMenu(bankMenu);



        SY_OS.setText(hardware.getSystemBoard().getOperatingSystem());
        SY_Model.setText(hardware.getSystemBoard().getModel());
        SY_Motherboard.setText(hardware.getSystemBoard().getMotherboard());

        CPU_Name.setText(hardware.getCpuInst().getName());
        CPU_Physical_Processors.setText(String.valueOf(hardware.getCpuInst().getPhysicalCores()));
        CPU_Logical_Processors.setText(String.valueOf(hardware.getCpuInst().getLogicalCores()));

        RAM_Manufacturer.setText(hardware.getRamList().get(bank).getManufacturer());
        RAM_Capacity.setText(hardware.getRamList().get(bank).getCapacity());
        RAM_Frequency.setText(hardware.getRamList().get(bank).getFrequency());
        RAM_Type.setText(hardware.getRamList().get(bank).getMemoryType());


    }


    @FXML
    protected void onHomeButtonClicked() throws IOException
    {
        AnchorPane sidePane = FXMLLoader.load(getClass().getResource("Home-view.fxml"));

        SetSystemInformation(sidePane);

        sidePane.setMaxSize(1280,720);
        sidePane.setMinSize(1280,720);
        sidePane.setPrefSize(1280,720);
        borderpane.getChildren().setAll(sidePane);





    }
    @FXML
    protected void onCPUTestButtonClicked()
    {

    }
    @FXML
    protected void onRAMTestButtonClicked() throws IOException
    {
        AnchorPane newSidePane = FXMLLoader.load(getClass().getResource("RAM-View.fxml"));
        borderpane.getChildren().setAll(newSidePane);
        HardwareDetails hardware = new HardwareDetails();
        hardware.getHardwareInfo();

       int totalMemory=0;
       int frequency=0;
       String memoryType= hardware.getRamList().get(0).getMemoryType();
        for (RAM ram : hardware.getRamList()) {
            totalMemory = Integer.parseInt(ram.getCapacity().substring(0, ram.getCapacity().indexOf(" ")))+totalMemory;
            if(frequency<Integer.parseInt(ram.getFrequency().substring(0, ram.getFrequency().indexOf(" "))))
                frequency=Integer.parseInt(ram.getFrequency().substring(0, ram.getFrequency().indexOf(" ")));
        }
        Button runTestButton = (Button) newSidePane.lookup("#ramtest_btn");


        Text ram_capacity = (Text) newSidePane.lookup("#RAM_TCapacity");
        Text ram_frequency = (Text) newSidePane.lookup("#RAM_Freq");
        Text ram_type = (Text) newSidePane.lookup("#RAM_Type");



        ram_capacity.setText(totalMemory +" GB");
        ram_frequency.setText(frequency+" MHz");
        ram_type.setText(memoryType);

    }
    @FXML
    protected void onLeaderboardButtonClicked()
    {

    }
    @FXML
    protected void onExitButtonClicked()
    {
        System.exit(0);
    }
}
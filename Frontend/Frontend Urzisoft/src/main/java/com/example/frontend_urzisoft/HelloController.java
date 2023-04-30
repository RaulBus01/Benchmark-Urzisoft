package com.example.frontend_urzisoft;

import com.example.frontend_urzisoft.HardwareDetails.RAM;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import com.example.frontend_urzisoft.HardwareDetails.HardwareDetails;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class HelloController {
    @FXML
    private Text SY_OS = new Text();
    private Pane SY = new Pane();

    @FXML
    private Button homeButton,CPUTestButton,RAMTestButton,LeaderboardButton,ExitButton;

    @FXML
    private AnchorPane borderpane;


    @FXML
    private void SetSystemInformation(AnchorPane parent){

        int bank = 0;

        HardwareDetails hardware = new HardwareDetails();
        hardware.getHardwareInfo();

        Pane SY =(Pane) parent.lookup("#SY"),
             RAM_Info = (Pane) parent.lookup("#RAM_Info"),
                CPU_Info = (Pane) parent.lookup("#CPU_Info");

        Text SY_OS = (Text) SY.lookup("#SY_OS"); SY_OS.setWrappingWidth(125);
        Text SY_Model = (Text) SY.lookup("#SY_Model"); SY_Model.setWrappingWidth(125);
        Text SY_Motherboard = (Text) SY.lookup("#SY_Motherboard"); SY_Motherboard.setWrappingWidth(125);

        Text CPU_Name = (Text) CPU_Info.lookup("#CPU_Name"); CPU_Name.setWrappingWidth(215); CPU_Name.setLayoutX(90);
        Text CPU_Physical_Processors = (Text) CPU_Info.lookup("#CPU_Physical_Processors");
        Text CPU_Logical_Processors = (Text) CPU_Info.lookup("#CPU_Logical_Processors");

        Text RAM_Manufacturer = (Text) RAM_Info.lookup("#RAM_Manufacturer"); RAM_Manufacturer.setWrappingWidth(155);
        Text RAM_Capacity = (Text) RAM_Info.lookup("#RAM_Capacity"); RAM_Capacity.setWrappingWidth(155);
        Text RAM_Frequency = (Text) RAM_Info.lookup("#RAM_Frequency"); RAM_Frequency.setWrappingWidth(155);
        Text RAM_Type = (Text) RAM_Info.lookup("#RAM_Type"); RAM_Type.setWrappingWidth(155);

        SplitMenuButton BankDropdown = (SplitMenuButton) RAM_Info.lookup("#BankDropdown");
        BankDropdown.disableProperty().setValue(true);
        BankDropdown.setVisible(false);

        if(hardware.getRamList().size() > 1)
        {
            BankDropdown.disableProperty().setValue(false);
            BankDropdown.setVisible(true);
            BankDropdown.setText(hardware.getRamList().get(0).getBankLabel());
            BankDropdown.getItems().clear();
            int i = 0;
            for (RAM ram : hardware.getRamList()) {
                MenuItem item = new MenuItem(ram.getBankLabel());
                int finalI = i;
                item.setOnAction(event -> {
                    BankDropdown.setText(ram.getBankLabel());
                    RAM_Manufacturer.setText(hardware.getRamList().get(finalI).getManufacturer());
                    RAM_Capacity.setText(hardware.getRamList().get(finalI).getCapacity());
                    RAM_Frequency.setText(hardware.getRamList().get(finalI).getFrequency());
                    RAM_Type.setText(hardware.getRamList().get(finalI).getMemoryType());
                    System.out.println("Bank: " + ram.getBankLabel());
                });
                BankDropdown.getItems().add(item);
                i++;
            }
        }
        else
        {
            BankDropdown.disableProperty().setValue(true);
            BankDropdown.setVisible(false);
        }


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
//        SY_OS.setText("dasdsadasdasddasadlqwlas");




    }
    @FXML
    protected void onCPUTestButtonClicked()
    {

    }
    @FXML
    protected void onRAMTestButtonClicked()
    {

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
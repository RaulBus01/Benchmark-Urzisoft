package com.example.frontend_urzisoft;

import com.example.frontend_urzisoft.DataBase.LeaderboardEntry;
import com.example.frontend_urzisoft.DataBase.MongoDB;
import com.example.frontend_urzisoft.HardwareDetails.RAM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.util.Objects;

import com.example.frontend_urzisoft.HardwareDetails.HardwareDetails;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import com.example.frontend_urzisoft.DataBase.LeaderboardEntry;
import org.bson.Document;
import oshi.SystemInfo;
import oshi.driver.linux.proc.UserGroupInfo;

import static oshi.util.Util.sleep;

public class HelloController {


    @FXML
    private Button homeButton, CPUTestButton, RAMTestButton, LeaderboardButton, ExitButton;

    @FXML
    private AnchorPane borderpane;
    private String CPU_id, RAM_id;
    private int CPUScore, RAMScore, TotalScore;
    private String user_id;


    @FXML
    private void SetSystemInformation(AnchorPane parent) {

        int bank = 0;

        HardwareDetails hardware = new HardwareDetails();
        hardware.getHardwareInfo();


        Pane SY = (Pane) parent.lookup("#SY"),
                RAM_Info = (Pane) parent.lookup("#RAM_Info"),
                CPU_Info = (Pane) parent.lookup("#CPU_Info");

        Text SY_OS = (Text) SY.lookup("#SY_OS");
        SY_OS.setWrappingWidth(125);
        Text SY_Model = (Text) SY.lookup("#SY_Model");
        SY_Model.setWrappingWidth(200);
        Text SY_Motherboard = (Text) SY.lookup("#SY_Motherboard");
        SY_Motherboard.setWrappingWidth(150);

        Text CPU_Name = (Text) CPU_Info.lookup("#CPU_Name");
        CPU_Name.setWrappingWidth(215);
        CPU_Name.setLayoutX(90);
        Text CPU_Physical_Processors = (Text) CPU_Info.lookup("#CPU_Physical_Processors");
        Text CPU_Logical_Processors = (Text) CPU_Info.lookup("#CPU_Logical_Processors");

        Text RAM_Manufacturer = (Text) RAM_Info.lookup("#RAM_Manufacturer");
        RAM_Manufacturer.setWrappingWidth(155);
        Text RAM_Capacity = (Text) RAM_Info.lookup("#RAM_Capacity");
        RAM_Capacity.setWrappingWidth(155);
        Text RAM_Frequency = (Text) RAM_Info.lookup("#RAM_Frequency");
        RAM_Frequency.setWrappingWidth(155);
        Text RAM_Type = (Text) RAM_Info.lookup("#RAM_Type");
        RAM_Type.setWrappingWidth(155);


        Button bankButton = (Button) RAM_Info.lookup("#bank_btn");


        ContextMenu bankMenu = new ContextMenu();
        bankMenu.setHideOnEscape(true);


        bankMenu.setStyle("-fx-background-color:#2D3E40;-fx-font-size: 16px;-fx-background-radius: 5px; -fx-text-alignment: center; -fx-padding: 5px; -fx-border-color: #2D3E40; -fx-border-radius: 5px; -fx-border-width: 2px; -fx-text-fill: white;");

        if (hardware.getRamList().size() > 1) {
            for (int i = 0; i < hardware.getRamList().size(); i++) {
                final int index = i;
                MenuItem item = new MenuItem("Bank " + (i + 1));
                item.setStyle(" -fx-text-fill: #e4f2e7;");
                item.setOnAction(event -> {
                    RAM ram = hardware.getRamList().get(index);
                    bankButton.setText("Bank " + (index + 1));
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
    protected void onHomeButtonClicked() throws IOException {
        AnchorPane sidePane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Home-view.fxml")));

        SetSystemInformation(sidePane);

        sidePane.setMaxSize(1280, 720);
        sidePane.setMinSize(1280, 720);
        sidePane.setPrefSize(1280, 720);
        borderpane.getChildren().setAll(sidePane);


    }

    @FXML
    protected void onCPUTestButtonClicked() throws IOException {
        AnchorPane newSidePane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CPU-View.fxml")));
        borderpane.getChildren().setAll(newSidePane);
        HardwareDetails hardware = new HardwareDetails();
        hardware.getHardwareInfo();
        Button startButton = (Button) newSidePane.lookup("#cputest_btn");
        AnchorPane CPU_load = (AnchorPane) newSidePane.lookup("#CPUTest_View");
        startButton.setOnAction(event -> {
            try {
                TestCPU(CPU_load);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Text CPU_Name = (Text) newSidePane.lookup("#CPU_Name");
        Text CPU_Physical_Processors = (Text) newSidePane.lookup("#Phiys_Core");
        Text CPU_Logical_Processors = (Text) newSidePane.lookup("#Logic_Core");
        Text CPU_Frequency = (Text) newSidePane.lookup("#CPU_Freq");

        CPU_Name.setText(hardware.getCpuInst().getName());
        CPU_Physical_Processors.setText(String.valueOf(hardware.getCpuInst().getPhysicalCores()));
        CPU_Logical_Processors.setText(String.valueOf(hardware.getCpuInst().getLogicalCores()));
        CPU_Frequency.setText(String.valueOf(hardware.getCpuInst().getFrequency()));

        CPU_id = hardware.getCpuInst().getName();




    }
    @FXML
    protected void onRAMTestButtonClicked() throws IOException
    {
        AnchorPane newSidePane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("RAM-View.fxml")));
        borderpane.getChildren().setAll(newSidePane);
        Button runTestButton = (Button) newSidePane.lookup("#ramtest_btn");
        AnchorPane RAM_load = (AnchorPane) newSidePane.lookup("#RAMTest_View");



        runTestButton.setOnAction(event -> {
            try {

                TestRam(RAM_load);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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


        Text ram_capacity = (Text) newSidePane.lookup("#RAM_TCapacity");
        Text ram_frequency = (Text) newSidePane.lookup("#RAM_Freq");
        Text ram_type = (Text) newSidePane.lookup("#RAM_Type");



        ram_capacity.setText(totalMemory +" GB");
        ram_frequency.setText(frequency+" MHz");
        ram_type.setText(memoryType);
        RAM_id = totalMemory+" GB "+frequency+" MHz "+memoryType;

    }
    protected void TestCPU(AnchorPane side) throws IOException
    {
        AnchorPane viewLoad = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CPUTEST-View.fxml")));
        side.getChildren().setAll(viewLoad);


        // Create a progress bar
        ProgressBar progressBar = new ProgressBar(0);
        Label v = new Label();

        progressBar.setPrefWidth(200);
        progressBar.setPrefHeight(20);
        progressBar.setLayoutX(230);
        progressBar.setLayoutY(270);
        v.setStyle("-fx-font-size: 16px; -fx-text-fill: #C2C0A6;");
        v.setLayoutX(290);
        v.setLayoutY(230);
        side.getChildren().add(progressBar);
        side.getChildren().add(v);
        progressBar.setStyle("-fx-accent: #2D3E40;");

        // Create a task to perform the RAM test

        Task<Void> cpuTestTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                //First Message
                updateMessage("Testing CPU...");

                for (int i = 0; i < 5; i++) {
                    Thread.sleep(1000);
                    //Multiple messages

                    updateMessage("Testing CPU with Algo... " );

                }
                updateMessage("CPU Test Complete");
                updateProgress(1, 1);
                return null;
            }
        };
        progressBar.progressProperty().bind(cpuTestTask.progressProperty());


        v.setLayoutX(250);
        v.textProperty().bind(cpuTestTask.messageProperty());

        Thread thread = new Thread(cpuTestTask);
        thread.start();

    }


    protected void TestRam(AnchorPane side) throws IOException
    {
        AnchorPane viewLoad = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("RAMTEST-view.fxml")));
        side.getChildren().setAll(viewLoad);


        // Create a progress bar
        ProgressBar progressBar = new ProgressBar(0);
        Label v = new Label("Ram Test");

        progressBar.setPrefWidth(200);
        progressBar.setPrefHeight(20);
        progressBar.setLayoutX(230);
        progressBar.setLayoutY(270);
        v.setStyle("-fx-font-size: 16px; -fx-text-fill: #C2C0A6;");
        v.setLayoutX(290);
        v.setLayoutY(230);
        side.getChildren().add(progressBar);
        side.getChildren().add(v);
        progressBar.setStyle("-fx-accent: #2D3E40;");

        // Create a task to perform the RAM test

        Task<Void> ramTestTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                //First Message
                updateMessage("Testing RAM...");
                sleep(1000);
                updateMessage("Testing RAM with Algo... " );
                MongoDB mongoDB = new MongoDB();


                updateMessage("RAM Test Complete");
                updateProgress(1, 1);
                mongoDB.connect();

                Document document = new Document("user:","Test" ).append("CPU", CPU_id)
                        .append("RAM", RAM_id)
                        .append("RAMScore", 100)
                        .append("CPUScore", 100)
                        .append("TotalScore", 100);
                mongoDB.insertDocument(document);







                return null;
            }
        };
        progressBar.progressProperty().bind(ramTestTask.progressProperty());


        v.setLayoutX(250);
        v.textProperty().bind(ramTestTask.messageProperty());

            Thread thread = new Thread(ramTestTask);
            thread.start();

    }

    @FXML
    protected void onLeaderboardButtonClicked() throws IOException
    {
        AnchorPane newSidePane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Leaderboard-view.fxml")));
        borderpane.getChildren().setAll(newSidePane);

        TableView<LeaderboardEntry> table = (TableView<LeaderboardEntry>) newSidePane.lookup("#Table");
        TableColumn<LeaderboardEntry, String> nameColumn = (TableColumn<LeaderboardEntry, String>) table.getColumns().get(0);
        TableColumn<LeaderboardEntry, String> cpuColumn = (TableColumn<LeaderboardEntry, String>) table.getColumns().get(1);
        TableColumn<LeaderboardEntry, String> ramColumn = (TableColumn<LeaderboardEntry, String>) table.getColumns().get(2);
        TableColumn<LeaderboardEntry, Integer> cpuScoreColumn = (TableColumn<LeaderboardEntry, Integer>) table.getColumns().get(3);
        TableColumn<LeaderboardEntry, Integer> ramScoreColumn = (TableColumn<LeaderboardEntry, Integer>) table.getColumns().get(4);
        TableColumn<LeaderboardEntry, Integer> totalScoreColumn = (TableColumn<LeaderboardEntry, Integer>) table.getColumns().get(5);


        nameColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        cpuColumn.setCellValueFactory(new PropertyValueFactory<>("CPU"));
        ramColumn.setCellValueFactory(new PropertyValueFactory<>("RAM"));
        cpuScoreColumn.setCellValueFactory(new PropertyValueFactory<>("CPUScore"));
        ramScoreColumn.setCellValueFactory(new PropertyValueFactory<>("RAMScore"));
        totalScoreColumn.setCellValueFactory(new PropertyValueFactory<>("TotalScore"));




        LeaderboardEntry newEntry = new LeaderboardEntry("Test", "I5","8GB", 10, 12);


        ObservableList<LeaderboardEntry> data = FXCollections.observableArrayList();
        data.add(newEntry);




        table.setItems(data);









    }
    @FXML
    protected void onAboutButtonClicked() throws IOException
    {
        AnchorPane newSidePane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("About-view.fxml")));
        borderpane.getChildren().setAll(newSidePane);
    }


    @FXML
    protected void onExitButtonClicked()
    {
        System.exit(0);
    }
}
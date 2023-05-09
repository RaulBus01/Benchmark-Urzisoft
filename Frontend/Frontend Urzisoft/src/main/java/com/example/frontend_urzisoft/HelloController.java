package com.example.frontend_urzisoft;

import com.example.frontend_urzisoft.DataBase.LeaderboardEntry;
import com.example.frontend_urzisoft.DataBase.MongoDB;
import com.example.frontend_urzisoft.HardwareDetails.CPU;
import com.example.frontend_urzisoft.HardwareDetails.RAM;
import com.example.frontend_urzisoft.HardwareDetails.SYI;

import com.example.frontend_urzisoft.ui.RingProgressIndicator;
import com.example.frontend_urzisoft.ui.RingProgressIndicatorSkin;
import com.example.frontend_urzisoft.ui.UILoading;
import com.mongodb.client.FindIterable;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import org.bson.Document;



public class HelloController implements Initializable {




    @FXML
    private AnchorPane borderpane;
    private String CPU_id, RAM_id;

    private CPU cpu;
    private List<RAM> ram;
    private SYI syi;
    private int CPUScore, RAMScore, TotalScore;
    private String user_id;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("HelloController initialized");
        ComponentsService componentsService = new ComponentsService();
        this.cpu = componentsService.createCPU();
        this.ram = componentsService.createRAM();
        this.syi = componentsService.createSYI();

        System.out.println("CPU: " + this.cpu.getName() + " " + this.cpu.getPhysicalCores() + " " + this.cpu.getLogicalCores() + " " + this.cpu.getFrequency());

        for (RAM ram1 : ram) {
            if(ram1 != null){
                System.out.println("RAM: " + ram1.getManufacturer() + " " + ram1.getBank() + " " + ram1.getCapacity() + " " + ram1.getFrequency());
            }
        }

        System.out.println("SYI: " + this.syi.getOperatingSystem() + " " + this.syi.getModel() + " Motherboard: " + this.syi.getMotherboard());
    }

    @FXML
    private void SetSystemInformation(AnchorPane parent) {

        int bank = 0;



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

        if (this.ram.size() > 1) {
            for (int i = 0; i < this.ram.size(); i++) {
                final int index = i;
                MenuItem item = new MenuItem("Bank " + this.ram.get(i).getBank());
                item.setStyle(" -fx-text-fill: #e4f2e7;");
                item.setOnAction(event -> {
                    RAM ramItem = this.ram.get(index);
                    bankButton.setText("Bank " + ramItem.getBank());
                    RAM_Manufacturer.setText(ramItem.getManufacturer());
                    RAM_Capacity.setText(ramItem.getCapacity());
                    RAM_Frequency.setText(ramItem.getFrequency());
                   // System.out.println(ramItem.getMemoryType());
                    RAM_Type.setText(String.valueOf(ramItem.getMemoryType()));
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


        SY_OS.setText(this.syi.getOperatingSystem());
        SY_Model.setText(this.syi.getModel());
        SY_Motherboard.setText(this.syi.getMotherboard());

        CPU_Name.setText(this.cpu.getName());
        CPU_Physical_Processors.setText(String.valueOf(this.cpu.getPhysicalCores()));
        CPU_Logical_Processors.setText(String.valueOf(this.cpu.getLogicalCores()));

        RAM_Manufacturer.setText(this.ram.get(bank).getManufacturer());
        RAM_Capacity.setText(this.ram.get(bank).getCapacity());
        RAM_Frequency.setText(this.ram.get(bank).getFrequency());
        RAM_Type.setText(this.ram.get(bank).getMemoryType());


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

        CPU_Name.setText(this.cpu.getName());
        CPU_Physical_Processors.setText(String.valueOf(this.cpu.getPhysicalCores()));
        CPU_Logical_Processors.setText(String.valueOf(this.cpu.getLogicalCores()));
        CPU_Frequency.setText(String.valueOf(this.cpu.getFrequency()));

        CPU_id = this.cpu.getName();




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

        int totalMemory=0;
        int frequency=0;
        String memoryType= this.ram.get(0).getMemoryType();
        for (RAM ram : this.ram) {
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


        UILoading sideLoad = new UILoading();
        sideLoad.setUI(side);

        RingProgressIndicator totalScoreIndicator= new RingProgressIndicator();
        RingProgressIndicator singleScoreIndicator= new RingProgressIndicator();
        RingProgressIndicator multiScoreIndicator= new RingProgressIndicator();
        singleScoreIndicator.setInnerCircleRadius(40);
        singleScoreIndicator.setRingWidth(15);
        multiScoreIndicator.setInnerCircleRadius(50);
        multiScoreIndicator.setRingWidth(16);
        totalScoreIndicator.setInnerCircleRadius(60);
        totalScoreIndicator.setRingWidth(17);



        singleScoreIndicator.setLayoutX(83);
        singleScoreIndicator.setLayoutY(200);
        multiScoreIndicator.setLayoutX(420);
        multiScoreIndicator.setLayoutY(189);

        totalScoreIndicator.setLayoutX(240);
        totalScoreIndicator.setLayoutY(360);
        String css = getClass().getResource("/com/example/frontend_urzisoft/CSS/circleProgressSingle.css").toExternalForm();
        singleScoreIndicator.getStylesheets().add(css);
        multiScoreIndicator.getStylesheets().add(css);






        Task<Void> cpuTestTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                //First Message
                updateMessage("Loading Tests...");
                Thread.sleep(1000);
                updateMessage("Testing CPU with Algo... ");
                Thread.sleep(4000);
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(40);
                    updateProgress(i + 1, 10);
                }
                updateMessage("CPU Test Complete");
                Thread.sleep(750);


                return null;
            }
        };


        sideLoad.getProgressBar().progressProperty().bind(cpuTestTask.progressProperty());
        sideLoad.getTaskText().textProperty().bind(cpuTestTask.messageProperty());
// Create a task to update the ring indicator
        Task<Void> updateRingTask = new Task<>()
        {

            @Override
            protected Void call() throws Exception
            {
                int single = 8907;
                int multi = 12407;
                int total = single + multi;
                int targetProgress = total;
                int increment = Math.max(targetProgress / 100, 1);
                int progress = 0;

                while (progress < targetProgress )
                {
                    progress += increment;
                    Thread.sleep(10);
                    if (progress < targetProgress)
                    {
                        int finalProgress = progress;
                        Platform.runLater(() -> totalScoreIndicator.setProgress(finalProgress));
                        if( progress < multi)
                        {
                            Platform.runLater(() -> multiScoreIndicator.setProgress(finalProgress));
                            if(progress<single)
                            {
                                Platform.runLater(() -> singleScoreIndicator.setProgress(finalProgress));
                            }
                            else
                            {
                                Platform.runLater(() -> singleScoreIndicator.setProgress(single));
                            }



                        }
                        else {
                            Platform.runLater(() -> multiScoreIndicator.setProgress(multi));
                        }
                    }
                    else
                    {
                        Platform.runLater(() ->totalScoreIndicator.setProgress(total));
                    }


                }

                sideLoad.getTaskText().setVisible(false);
                MongoDB mongoDB = new MongoDB();
                mongoDB.connect();

                Document document = new Document("user","AP" ).append("CPU", CPU_id)
                        .append("RAM", RAM_id)
                        .append("RAMScore", 200)
                        .append("CPUScore", 200)
                        .append("TotalScore", 200);
                mongoDB.insertDocument(document);

                mongoDB.closeConnection();
                return null;
            }

        };



        Thread thread = new Thread(cpuTestTask);
        thread.start();

        cpuTestTask.setOnSucceeded(event -> {

            sideLoad.getProgressBar().setVisible(false);
            sideLoad.getTaskText().setLayoutX(255);
            sideLoad.getTaskText().setLayoutY(25);
            sideLoad.getTaskText().textProperty().unbind();
            sideLoad.getTaskText().setText("Loading Scores...");
            Thread thread2 = new Thread(updateRingTask);
            thread2.start();
            viewLoad.getChildren().add(singleScoreIndicator);
            viewLoad.getChildren().add(totalScoreIndicator);
            viewLoad.getChildren().add(multiScoreIndicator);
            viewLoad.getChildren().add(sideLoad.getSingleScoreText());
            viewLoad.getChildren().add(sideLoad.getMultiScoreText());
            viewLoad.getChildren().add(sideLoad.getTotalScoreText());
            sideLoad.getSingleScoreText().setText("Single Core Score ");
            sideLoad.getMultiScoreText().setText("Multi Core Score ");
            sideLoad.getTotalScoreText().setText("Total Score ");

        });

    }


    protected void TestRam(AnchorPane side) throws IOException
    {
        AnchorPane viewLoad = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("RAMTEST-view.fxml")));
        side.getChildren().setAll(viewLoad);

        UILoading ramLoad = new UILoading();
        ramLoad.setUI(side);

        RingProgressIndicator totalScoreIndicator= new RingProgressIndicator();
        RingProgressIndicator singleScoreIndicator= new RingProgressIndicator();
        RingProgressIndicator multiScoreIndicator= new RingProgressIndicator();
        singleScoreIndicator.setInnerCircleRadius(40);
        singleScoreIndicator.setRingWidth(15);
        multiScoreIndicator.setInnerCircleRadius(50);
        multiScoreIndicator.setRingWidth(16);
        totalScoreIndicator.setInnerCircleRadius(60);
        totalScoreIndicator.setRingWidth(17);



        singleScoreIndicator.setLayoutX(83);
        singleScoreIndicator.setLayoutY(200);
        multiScoreIndicator.setLayoutX(420);
        multiScoreIndicator.setLayoutY(189);

        totalScoreIndicator.setLayoutX(240);
        totalScoreIndicator.setLayoutY(360);
        String css = getClass().getResource("/com/example/frontend_urzisoft/CSS/circleProgressSingle.css").toExternalForm();
        singleScoreIndicator.getStylesheets().add(css);
        multiScoreIndicator.getStylesheets().add(css);

        // Create a task to perform the RAM test

        Task<Void> cpuTestTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                //First Message
                updateMessage("Loading Tests...");
                Thread.sleep(1000);
                updateMessage("Testing RAM with Algo... ");
                Thread.sleep(4000);
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(40);
                    updateProgress(i + 1, 10);
                }
                updateMessage("RAM Test Complete");
                Thread.sleep(750);


                return null;
            }
        };


        ramLoad.getProgressBar().progressProperty().bind(cpuTestTask.progressProperty());
        ramLoad.getTaskText().textProperty().bind(cpuTestTask.messageProperty());
// Create a task to update the ring indicator
        Task<Void> updateRingTask = new Task<>()
        {

            @Override
            protected Void call() throws Exception
            {
                int single = 8907;
                int multi = 12407;
                int total = single + multi;
                int targetProgress = total;
                int increment = Math.max(targetProgress / 100, 1);
                int progress = 0;

                while (progress < targetProgress )
                {
                    progress += increment;
                    Thread.sleep(10);
                    if (progress < targetProgress)
                    {
                        int finalProgress = progress;
                        Platform.runLater(() -> totalScoreIndicator.setProgress(finalProgress));
                        if( progress < multi)
                        {
                            Platform.runLater(() -> multiScoreIndicator.setProgress(finalProgress));
                            if(progress<single)
                            {
                                Platform.runLater(() -> singleScoreIndicator.setProgress(finalProgress));
                            }
                            else
                            {
                                Platform.runLater(() -> singleScoreIndicator.setProgress(single));
                            }



                        }
                        else {
                            Platform.runLater(() -> multiScoreIndicator.setProgress(multi));
                        }
                    }
                    else
                    {
                        Platform.runLater(() ->totalScoreIndicator.setProgress(total));
                    }


                }

                ramLoad.getTaskText().setVisible(false);
                MongoDB mongoDB = new MongoDB();
                mongoDB.connect();

                Document document = new Document("user","AP" ).append("CPU", CPU_id)
                        .append("RAM", RAM_id)
                        .append("RAMScore", 200)
                        .append("CPUScore", 200)
                        .append("TotalScore", 200);
                mongoDB.insertDocument(document);

                mongoDB.closeConnection();
                return null;
            }

        };



        Thread thread = new Thread(cpuTestTask);
        thread.start();

        cpuTestTask.setOnSucceeded(event -> {

            ramLoad.getProgressBar().setVisible(false);
            ramLoad.getTaskText().setLayoutX(255);
            ramLoad.getTaskText().setLayoutY(25);
            ramLoad.getTaskText().textProperty().unbind();
            ramLoad.getTaskText().setText("Loading Scores...");
            Thread thread2 = new Thread(updateRingTask);
            thread2.start();
            viewLoad.getChildren().add(singleScoreIndicator);
            viewLoad.getChildren().add(totalScoreIndicator);
            viewLoad.getChildren().add(multiScoreIndicator);
            viewLoad.getChildren().add(ramLoad.getSingleScoreText());
            viewLoad.getChildren().add(ramLoad.getMultiScoreText());
            viewLoad.getChildren().add(ramLoad.getTotalScoreText());
            ramLoad.getSingleScoreText().setText("Single Core Score ");
            ramLoad.getMultiScoreText().setText("Multi Core Score ");
            ramLoad.getTotalScoreText().setText("Total Score ");

        });

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
        MongoDB mongoDB = new MongoDB();
        mongoDB.connect();
        FindIterable<Document> documents = mongoDB.getCollection().find();



        nameColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        cpuColumn.setCellValueFactory(new PropertyValueFactory<>("CPU"));
        ramColumn.setCellValueFactory(new PropertyValueFactory<>("RAM"));
        cpuScoreColumn.setCellValueFactory(new PropertyValueFactory<>("CPUScore"));
        ramScoreColumn.setCellValueFactory(new PropertyValueFactory<>("RAMScore"));
        totalScoreColumn.setCellValueFactory(new PropertyValueFactory<>("TotalScore"));

        ObservableList<LeaderboardEntry> data = FXCollections.observableArrayList();
        for (Document doc : documents)
        {
            LeaderboardEntry entry= new LeaderboardEntry("1", doc.getString("CPU"), doc.getString("RAM"), doc.getInteger("CPUScore"), doc.getInteger("RAMScore"));

            for(int i=0;i<10;i++)
            {

                data.add(entry);

            }
        }





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
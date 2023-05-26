package com.example.frontend_urzisoft;

import com.example.CPU_Benchmark;
import com.example.backend.RAM.RamBenchmark_Test;
import com.example.frontend_urzisoft.DataBase.LeaderboardEntry;
import com.example.frontend_urzisoft.DataBase.MongoDB;
import com.example.frontend_urzisoft.HardwareDetails.CPU;
import com.example.frontend_urzisoft.HardwareDetails.RAM;
import com.example.frontend_urzisoft.HardwareDetails.SYI;


import com.example.frontend_urzisoft.ui.RingProgressIndicator;
import com.example.frontend_urzisoft.ui.UILoading;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;


import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;


public class HelloController implements Initializable {




    @FXML
    private AnchorPane borderpane;
    private String CPU_id, RAM_id;

    private CPU cpu;
    private List<RAM> ram;
    private SYI syi;
    private  final MongoDB mongoDB = new MongoDB();





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // System.out.println("HelloController initialized");
        ComponentsService componentsService = new ComponentsService();
        this.cpu = componentsService.createCPU();
        this.ram = componentsService.createRAM();
        this.syi = componentsService.createSYI();
        CPU_id = cpu.getName();
        double capacity = 0;





       // System.out.println("CPU: " + this.cpu.getName() + " " + this.cpu.getPhysicalCores() + " " + this.cpu.getLogicalCores() + " " + this.cpu.getFrequency());

        for (RAM ram1 : ram) {
            if(ram1 != null){

                String ramCapacity = ram1.getCapacity();
                capacity =capacity+  Double.parseDouble(ramCapacity.replace("GB", ""));
                //System.out.println("RAM: " + ram1.getManufacturer() + " " + ram1.getBank() + " " + ram1.getCapacity() + " " + ram1.getFrequency());
            }
        }
        RAM_id = capacity + " GB " + ram.get(0).getFrequency() + " " + ram.get(0).getMemoryType();

        //System.out.println("SYI: " + this.syi.getOperatingSystem() + " " + this.syi.getModel() + " Motherboard: " + this.syi.getMotherboard());
    }

    void changePanesView(boolean disabled){
        AnchorPane menu = (AnchorPane) HelloApplication.scene.lookup("#menuPane");



        ArrayList<ImageView> icons = new ArrayList<>(6);

            icons.add((ImageView) menu.lookup("#homeIcon"));
            icons.add((ImageView) menu.lookup("#cpuTestIcon"));
            icons.add((ImageView) menu.lookup("#ramTestIcon"));
            icons.add((ImageView) menu.lookup("#leaderboardIcon"));
            icons.add((ImageView) menu.lookup("#aboutIcon"));
            icons.add((ImageView) menu.lookup("#exitIcon"));



        menu.setDisable(disabled);


        if(disabled){
            for (ImageView icon : icons) {
                icon.setStyle("-fx-opacity: 0.5");
            }
        }else{
            for (ImageView icon : icons) {
                icon.setStyle("-fx-opacity: 1");
            }
        }
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

        bankButton.setOnAction(event -> bankMenu.show(bankButton, Side.RIGHT, 0, 0));

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
        ImageView aboutImage = (ImageView) borderpane.getParent().lookup("#AboutPng");
        aboutImage.setVisible(false);
        ImageView leaderboardImage = (ImageView) borderpane.getParent().lookup("#LeaderboardPng");
        leaderboardImage.setVisible(false);
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
        ImageView aboutImage = (ImageView) borderpane.getParent().lookup("#AboutPng");
        aboutImage.setVisible(false);
        ImageView leaderboardImage = (ImageView) borderpane.getParent().lookup("#LeaderboardPng");
        leaderboardImage.setVisible(false);

        Button startButton = (Button) newSidePane.lookup("#cputest_btn");
        AnchorPane CPU_load = (AnchorPane) newSidePane.lookup("#CPUTest_View");
        Button stopButton = (Button) newSidePane.lookup("#Stop");

        stopButton.setDisable(true);
        startButton.setOnAction(event -> {
            try {
                changePanesView(true);
                startButton.setDisable(true);
                TestCPU(CPU_load, startButton);
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






    }
    @FXML
    protected void onRAMTestButtonClicked() throws IOException
    {
        AnchorPane newSidePane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("RAM-View.fxml")));
        borderpane.getChildren().setAll(newSidePane);
        ImageView aboutImage = (ImageView) borderpane.getParent().lookup("#AboutPng");
        aboutImage.setVisible(false);
        ImageView leaderboardImage = (ImageView) borderpane.getParent().lookup("#LeaderboardPng");
        leaderboardImage.setVisible(false);
        Button runTestButton = (Button) newSidePane.lookup("#ramtest_btn");
        AnchorPane RAM_load = (AnchorPane) newSidePane.lookup("#RAMTest_View");



        runTestButton.setOnAction(event -> {
            try {
                changePanesView(true);
                runTestButton.setDisable(true);
                TestRam(RAM_load, runTestButton);
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


    }
    protected void TestCPU(AnchorPane side, Button runTestButton) throws IOException
    {
        AnchorPane viewLoad = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CPUTEST-View.fxml")));
        side.getChildren().setAll(viewLoad);


        UILoading cpuLoad = new UILoading();
        cpuLoad.setUI(side);

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
        String css = Objects.requireNonNull(getClass().getResource("/com/example/frontend_urzisoft/CSS/circleProgressSingle.css")).toExternalForm();
        singleScoreIndicator.getStylesheets().add(css);
        multiScoreIndicator.getStylesheets().add(css);



        CPU_Benchmark cpuBench = new CPU_Benchmark();
        final int[] singleScoreCPU = {0};
        final int[] multiScoreCPU = {0};
        final int[] totalScoreCPU = {0};


        AnchorPane pane = (AnchorPane) viewLoad.getParent().getParent();

        Button stopButton = (Button) pane.lookup("#Stop");
        stopButton.setDisable(false);
        ImageView ramImage = (ImageView) borderpane.lookup("#RAMPng");
        Task<Void> cpuTestTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                // First Message
                if (isCancelled()) {

                    System.out.println("Test Canceled1");

                    return null;
                }
                updateMessage("Loading Tests...");
                Thread.sleep(100);

                if (isCancelled()) {

                    System.out.println("Test Canceled2");
                    return null;
                }


                if (!isCancelled()) {
                    updateMessage("Testing CPU with Single Core...");
                    cpuBench.runSingle();
                }

                if (isCancelled()) {

                    System.out.println("Test Canceled3");
                    return null;
                }

                updateMessage("Testing CPU with Multi Core...");
                if (!isCancelled()) {
                    cpuBench.runMulti();
                }

                for (int i = 0; i < 10; i++) {
                    Thread.sleep(40);

                    updateProgress(i + 1, 10);

                    if (isCancelled()) {

                        System.out.println("Test Canceled4");
                        return null;
                    }
                }
                stopButton.setDisable(true);
                updateMessage("CPU Test Complete");
                Thread.sleep(750);


                return null;
            }
        };


        stopButton.setOnAction(event -> {
            cpuTestTask.cancel();
            cpuBench.cancel();
            ramImage.setVisible(false);
            Platform.runLater(() -> {
                cpuLoad.getProgressBar().progressProperty().unbind();
                cpuLoad.getTaskText().textProperty().unbind();
                cpuLoad.getProgressBar().setProgress(1);
                cpuLoad.getProgressBar().setStyle("-fx-accent: red;");
                cpuLoad.getTaskText().setLayoutX(275);
                cpuLoad.getTaskText().setText("Test Canceled");


                runTestButton.setDisable(false);
                changePanesView(false);
                stopButton.setDisable(true);


            });
        });

        cpuLoad.getProgressBar().progressProperty().bind(cpuTestTask.progressProperty());
        cpuLoad.getTaskText().textProperty().bind(cpuTestTask.messageProperty());
        // Create a task to update the ring indicator
        Task<Void> updateRingTask = new Task<>()
        {

            @Override
            protected Void call() throws Exception
            {


                totalScoreCPU[0] = singleScoreCPU[0] + multiScoreCPU[0];

                int increment = Math.max(totalScoreCPU[0] / 100, 1);
                int progress = 0;

                while (progress < totalScoreCPU[0] )
                {
                    progress += increment;

                    if (progress < totalScoreCPU[0])
                    {
                        int finalProgress = progress;
                        Platform.runLater(() -> totalScoreIndicator.setProgress(finalProgress));
                        if( progress < multiScoreCPU[0])
                        {
                            Platform.runLater(() -> multiScoreIndicator.setProgress(finalProgress));
                            if(progress< singleScoreCPU[0])
                            {
                                Platform.runLater(() -> singleScoreIndicator.setProgress(finalProgress));
                            }
                            else
                            {
                                Platform.runLater(() -> singleScoreIndicator.setProgress(singleScoreCPU[0]));
                            }



                        }
                        else {
                            Platform.runLater(() -> multiScoreIndicator.setProgress(multiScoreCPU[0]));
                        }
                    }
                    else
                    {
                        Platform.runLater(() ->totalScoreIndicator.setProgress(totalScoreCPU[0]));
                    }


                }
                cpuLoad.getTaskText().setVisible(false);

                cpuLoad.getDialogText().setVisible(true);
                cpuLoad.getDialog().setVisible(true);
                cpuLoad.getDialogButton().setVisible(true);

                return null;
            }

        };



        Thread thread = new Thread(cpuTestTask);
        thread.start();




        cpuTestTask.setOnSucceeded(event -> {

            ramImage.setVisible(false);
            singleScoreCPU[0] = (int) cpuBench.getScoreSingleThreaded();
            multiScoreCPU[0] = (int) cpuBench.getScoreMultiThreaded();

            cpuLoad.getProgressBar().setVisible(false);
            cpuLoad.getTaskText().setLayoutX(255);
            cpuLoad.getTaskText().setLayoutY(25);

            cpuLoad.getTaskText().textProperty().unbind();
            cpuLoad.getTaskText().setText("Loading Scores...");

            Thread thread2 = new Thread(updateRingTask);
            thread2.start();


            viewLoad.getChildren().add(singleScoreIndicator);
            viewLoad.getChildren().add(totalScoreIndicator);
            viewLoad.getChildren().add(multiScoreIndicator);
            viewLoad.getChildren().add(cpuLoad.getSingleScoreText());
            viewLoad.getChildren().add(cpuLoad.getMultiScoreText());
            viewLoad.getChildren().add(cpuLoad.getTotalScoreText());
            cpuLoad.getSingleScoreText().setText("Single Core Score ");
            cpuLoad.getMultiScoreText().setText("Multi Core Score ");
            cpuLoad.getTotalScoreText().setText("Total Score ");
        });
        updateRingTask.setOnSucceeded(event -> {
            Button dialogButton = cpuLoad.getDialogButton();
            TextField dialog = cpuLoad.getDialog();
            Label dialogText = cpuLoad.getDialogText();
            dialogButton.setOnAction(eventButton -> {
                String user = dialog.getText();

                if(user.length()>0)
                {
                    dialog.setDisable(true);
                    dialogButton.setVisible(false);
                    mongoDB.connect();
                    Document document = new Document("user", user)
                            .append("CPU", CPU_id)
                            .append("RAM", RAM_id)
                            .append("RAMScore", 0)
                            .append("CPUScore", totalScoreCPU[0])
                            .append("TotalScore", totalScoreCPU[0]);


                    if (mongoDB.checkUser(user)) {
                        MongoCollection<Document> collection = mongoDB.getCollection();
                        Document existingDocument = collection.find(eq("user", user)).first();

                        int existingRAMScore = existingDocument.getInteger("RAMScore", 0);
                        int existingCPUScore = existingDocument.getInteger("CPUScore", 0);


                        if (existingCPUScore == 0) {
                            // Add the CPUScore only if it doesn't exist in the document
                            existingDocument.put("CPUScore", totalScoreCPU[0]);
                            int newTotalScore = totalScoreCPU[0] + existingRAMScore; // Calculate the new TotalScore
                            existingDocument.put("TotalScore", newTotalScore);
                            dialogText.setText(user + ", your score has been updated!");
                            dialogText.setStyle("-fx-font-size: 18px; -fx-text-fill: green;");
                        } else if (existingRAMScore > 0) {
                            // User already has CPUScore and RAMScore
                            dialogText.setText(user + ", your score already exists!");
                            dialogText.setStyle("-fx-font-size: 18px; -fx-text-fill: red;");
                        } else {
                            // User already has CPUScore but RAMScore is 0
                            dialogText.setText(user + ", your CPU score is lower!");
                            dialogText.setStyle("-fx-font-size: 18px; -fx-text-fill: #ffc816;");
                        }

                        collection.replaceOne(eq("user", user), existingDocument);
                    } else {
                        mongoDB.insertDocument(document);
                        dialogText.setText(user + ", your score has been saved!");
                        dialogText.setStyle("-fx-font-size: 18px; -fx-text-fill: green;");
                    }

                    mongoDB.closeConnection();
                    }

                });

            changePanesView(false);
            runTestButton.setDisable(false);

        });

    }
    protected void TestRam(AnchorPane side, Button runTestButton) throws IOException
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
        String css = Objects.requireNonNull(getClass().getResource("/com/example/frontend_urzisoft/CSS/circleProgressSingle.css")).toExternalForm();
        singleScoreIndicator.getStylesheets().add(css);
        multiScoreIndicator.getStylesheets().add(css);

        // Create a task to perform the RAM test
        RamBenchmark_Test ramBench = new RamBenchmark_Test();

        ImageView ramImage = (ImageView) borderpane.lookup("#RAMPng");



        int[] totalScoreRAM = {0};

        AnchorPane pane = (AnchorPane) viewLoad.getParent().getParent();
        Button stopButton = (Button) pane.lookup("#Stop");
        stopButton.setDisable(false);
        Task<Void> ramTestTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                //First Message

                if (isCancelled()) {

                    System.out.println("Test Canceled1");

                    return null;
                }
                updateMessage("Loading Tests...");
                Thread.sleep(100);

                if (isCancelled()) {

                    System.out.println("Test Canceled2");
                    return null;
                }


                if (!isCancelled())
                {
                    updateMessage("Loading RAM");
                    ramBench.warmUp();
                   ramBench.startBenchmark();
                }

                for (int i = 0; i < 10; i++)
                {
                    Thread.sleep(40);
                    if (isCancelled()) {

                        System.out.println("Test Canceled4");
                        return null;
                    }
                    updateProgress(i + 1, 10);
                }
                stopButton.setDisable(true);
                updateMessage("RAM Test Complete");
                Thread.sleep(750);


                return null;
            }
        };
        stopButton.setOnAction(event -> {
            ramTestTask.cancel();
            ramBench.cancel();
            ramImage.setVisible(false);
            Platform.runLater(() -> {
                ramLoad.getProgressBar().progressProperty().unbind();
                ramLoad.getTaskText().textProperty().unbind();
                ramLoad.getProgressBar().setProgress(1);
                ramLoad.getProgressBar().setStyle("-fx-accent: red;");
                ramLoad.getTaskText().setLayoutX(275);
                ramLoad.getTaskText().setText("Test Canceled");


                runTestButton.setDisable(false);
                changePanesView(false);
                stopButton.setDisable(true);


            });
        });

        ramLoad.getProgressBar().progressProperty().bind(ramTestTask.progressProperty());
        ramLoad.getTaskText().textProperty().bind(ramTestTask.messageProperty());
// Create a task to update the ring indicator
        Task<Void> updateRingTask = new Task<>()
        {

            @Override
            protected Void call() throws Exception
            {




                int increment = Math.max(totalScoreRAM[0] / 100, 1);
                int progress = 0;

                while (progress < totalScoreRAM[0] )
                {
                    progress += increment;

                    if (progress < totalScoreRAM[0])
                    {
                        final int finalProgress = progress;
                        Platform.runLater(() -> totalScoreIndicator.setProgress(finalProgress));
                    }
                    else
                    {
                        Platform.runLater(() ->totalScoreIndicator.setProgress(totalScoreRAM[0]));
                    }


                }

                ramLoad.getTaskText().setVisible(false);
                MongoDB mongoDB = new MongoDB();
                mongoDB.connect();

                ramLoad.getDialogText().setVisible(true);
                ramLoad.getDialog().setVisible(true);
                ramLoad.getDialogButton().setVisible(true);
                return null;
            }

        };



        Thread thread = new Thread(ramTestTask);
        thread.start();

        ramTestTask.setOnSucceeded(event -> {
            ramImage.setVisible(false);

            totalScoreRAM[0] = (int)ramBench.getScore();

            ramLoad.getProgressBar().setVisible(false);
            ramLoad.getTaskText().setLayoutX(255);
            ramLoad.getTaskText().setLayoutY(25);
            ramLoad.getTaskText().textProperty().unbind();
            ramLoad.getTaskText().setText("Loading Scores...");
            Thread thread2 = new Thread(updateRingTask);
            thread2.start();

            viewLoad.getChildren().add(totalScoreIndicator);



            viewLoad.getChildren().add(ramLoad.getTotalScoreText());

            ramLoad.getTotalScoreText().setText("Total Score ");

            changePanesView(false);
            runTestButton.setDisable(false);
        });
        updateRingTask.setOnSucceeded(event -> {
            Button dialogButton = ramLoad.getDialogButton();
            TextField dialog = ramLoad.getDialog();
            Label dialogText = ramLoad.getDialogText();
            dialogButton.setOnAction(eventButton -> {
                String user = dialog.getText();

                if (user.length() > 0) {
                    dialog.setDisable(true);
                    dialogButton.setVisible(false);

                    mongoDB.connect();
                    Document document = new Document("user", user)
                            .append("CPU", CPU_id)
                            .append("RAM", RAM_id)
                            .append("RAMScore", totalScoreRAM[0])
                            .append("CPUScore", 0)
                            .append("TotalScore", totalScoreRAM[0]);
                    if (mongoDB.checkUser(user)) {
                        MongoCollection<Document> collection = mongoDB.getCollection();
                        Document existingDocument = collection.find(eq("user", user)).first();

                        int existingRAMScore = existingDocument.getInteger("RAMScore", 0);
                        int existingCPUScore = existingDocument.getInteger("CPUScore", 0);

                        if (existingRAMScore == 0) {
                            // Add the RAMScore only if it doesn't exist in the document
                            existingDocument.put("RAMScore", totalScoreRAM[0]);
                            int newTotalScore = totalScoreRAM[0] + existingCPUScore; // Calculate the new TotalScore
                            existingDocument.put("TotalScore", newTotalScore);
                            dialogText.setText(user + ", your score has been updated!");
                            dialogText.setStyle("-fx-font-size: 18px; -fx-text-fill: green;");
                        } else if (existingCPUScore > 0) {
                            // User already has CPUScore and RAMScore
                            dialogText.setText(user + ", your score already exists!");
                            dialogText.setStyle("-fx-font-size: 18px; -fx-text-fill: red;");
                        } else {
                            // User already has RAMScore but CPUScore is 0
                            dialogText.setText(user + ", your RAM score is lower!");
                            dialogText.setStyle("-fx-font-size: 18px; -fx-text-fill: #ffc816;");
                        }

                        collection.replaceOne(eq("user", user), existingDocument);
                    } else {
                        mongoDB.insertDocument(document);
                        dialogText.setText(user + ", your score has been saved!");
                        dialogText.setStyle("-fx-font-size: 18px; -fx-text-fill: green;");
                    }

                    mongoDB.closeConnection();
                }
            });
        });
    }

    @FXML
    protected void onLeaderboardButtonClicked() throws IOException {
        AnchorPane newSidePane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Leaderboard-view.fxml")));
        borderpane.getChildren().setAll(newSidePane);
        ImageView aboutImage = (ImageView) borderpane.getParent().lookup("#AboutPng");
        aboutImage.setVisible(false);
        ImageView leaderboardImage = (ImageView) borderpane.getParent().lookup("#LeaderboardPng");
        leaderboardImage.setVisible(true);

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
        for (Document doc : documents) {
            LeaderboardEntry entry = new LeaderboardEntry(doc.getString("user"), doc.getString("CPU"), doc.getString("RAM"), doc.getInteger("CPUScore"), doc.getInteger("RAMScore"), doc.getInteger("TotalScore"));

            data.add(entry);
        }
        data.sort(Comparator.comparingInt(LeaderboardEntry::getTotalScore).reversed());

        table.setItems(data);
    }

    @FXML
    protected void onAboutButtonClicked() throws IOException
    {
        AnchorPane newSidePane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("About-view.fxml")));
        ImageView leaderboardImage = (ImageView) borderpane.getParent().lookup("#LeaderboardPng");
        leaderboardImage.setVisible(false);
        ImageView aboutImage = (ImageView) borderpane.getParent().lookup("#AboutPng");
        aboutImage.setVisible(true);

        borderpane.getChildren().setAll(newSidePane);
        borderpane.getChildren().setAll(newSidePane);
        Text text = (Text) newSidePane.lookup("#TextHover");
        Label textAbout = (Label) newSidePane.lookup("#TextAbout");

        Pane pane = (Pane) newSidePane.lookup("#TeamPane");
        Button teamButton = (Button) newSidePane.lookup("#TeamButton");
        Button moreButton = (Button) newSidePane.lookup("#MoreButton");
        Button aboutButton = (Button) newSidePane.lookup("#AboutButton");


        moreButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openBrowserLink();
            }

            private void openBrowserLink() {
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.browse(new URI("http://github.com/RaulBus01/Benchmark-Urzisoft/"));
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        final boolean[] isAbout = {true};

        teamButton.setOnMouseClicked(event -> {
            if (isAbout[0])
            {
                teamButton.setText("About");
                isAbout[0] = false;
                textAbout.setOpacity(0);
                pane.setOpacity(1);
            } else {
                teamButton.setText("Team");
                isAbout[0] = true;
                textAbout.setOpacity(1);
                pane.setOpacity(0);
            }

        });

    }


    @FXML
    protected void onExitButtonClicked()
    {
        System.exit(0);
    }
}
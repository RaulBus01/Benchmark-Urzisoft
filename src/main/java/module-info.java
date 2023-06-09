module com.example.frontend_urzisoft {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires javafx.base;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires jdk.management;
    requires com.github.oshi;


    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires java.desktop;

    opens com.example.frontend_urzisoft to javafx.fxml;
    opens com.example.frontend_urzisoft.DataBase to javafx.base;

    exports com.example.frontend_urzisoft;
}
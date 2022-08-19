module com.group3.grapher {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.group3.grapher to javafx.fxml;
    exports com.group3.grapher;
    exports com.group3.grapher.graphs;
    opens com.group3.grapher.graphs to javafx.fxml;
    exports com.group3.grapher.database;
    opens com.group3.grapher.database to javafx.fxml;
    exports com.group3.grapher.modals;
    opens com.group3.grapher.modals to javafx.fxml;
}
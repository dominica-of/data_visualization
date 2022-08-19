package com.group3.grapher;

import com.group3.grapher.customComponents.NumberTextField;
import com.group3.grapher.database.models.Table;
import com.group3.grapher.graphs.*;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application {

//    User user = new User("Jerry", "John", "password");
//    Table table = new Table("Jerry","Students",
//        "array[array['s','1','2','5']]","array['Name','Marks','Course','Year']");


    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Grapher");
        stage.setScene(LoginScene.getScene(stage));
        stage.show();
        //


    }

}


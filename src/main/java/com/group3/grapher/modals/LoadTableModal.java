package com.group3.grapher.modals;

import com.group3.grapher.InnerScene;
import com.group3.grapher.database.models.Table;
import com.group3.grapher.database.models.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

public class LoadTableModal extends Stage {
    String tableName;

    public String getTableName() {
        return tableName;
    }

    private void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public LoadTableModal(Stage stage) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(stage);

        VBox mainBox = new VBox();

        setTitle("Load Table Dialog");
        Table table = new Table();
        ArrayList<String> tableNames = table.getAllUserTables(User.email);

        Label tableNameTexts[] = new Label[tableNames.size()];
        for (int i = 0; i < tableNames.size(); i++) {
            tableNameTexts[i] = new Label();
            tableNameTexts[i].setText(tableNames.get(i));
        }

        ScrollPane scrollPane = new ScrollPane();
        ListView<Label> tableNameList = new ListView();
        tableNameList.setPrefWidth(300);
        tableNameList.getItems().addAll(tableNameTexts);
        scrollPane.setContent(tableNameList);
        scrollPane.setMaxWidth(300);
        scrollPane.setPrefHeight(200);

        HBox buttonsBox = new HBox();
        HBox selectedDisplayBox = new HBox();
        Label selectedTableNameLabel = new Label("Selected table:");
        selectedTableNameLabel.setId("selectedLabel");
        TextField selectedTableNameTextField = new TextField();
        selectedTableNameTextField.setEditable(false);

        selectedDisplayBox.getChildren().addAll(selectedTableNameLabel,selectedTableNameTextField);
        selectedDisplayBox.setSpacing(10);

        Button load = new Button("Load");
        load.setDisable(true);
        tableNameList.setOnMouseClicked(e->{
            if(tableNameList.getSelectionModel().getSelectedItem()!=null){
                load.setDisable(false);
                selectedTableNameTextField.setText(tableNameList.getSelectionModel().getSelectedItem().getText().trim());
            }
        });
        load.setOnAction(e->{
            String selectedTable = tableNameList.getSelectionModel().getSelectedItem().getText().trim();

            if(!selectedTable.equals("")){
                setTableName(selectedTable);
                System.out.println(tableName);
                close();
            }

        });
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e->{
            close();
        });


        buttonsBox.getChildren().addAll(load,cancel);
        buttonsBox.setSpacing(20);

        mainBox.setPadding(new Insets(20,20,20,20));
        mainBox.setSpacing(30);

        mainBox.getChildren().addAll(scrollPane,selectedDisplayBox,buttonsBox);

        Scene scene = new Scene(mainBox, 330,300);
        scene.getStylesheets().add(Objects.requireNonNull(InnerScene.class.getResource("modal.css")).toExternalForm());
        setScene(scene);
    }

}

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

public class SaveTableModal extends Stage {
    String tableName;

    public String getTableName() {
        return tableName;
    }

    private void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public SaveTableModal(Stage stage) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(stage);

        VBox mainBox = new VBox();

        setTitle("Save Table Dialog");
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
        Label selectedTableNameLabel = new Label("Enter table name:");
        selectedTableNameLabel.setId("selectedLabel");
        TextField tableNameTextField = new TextField();

        selectedDisplayBox.getChildren().addAll(selectedTableNameLabel,tableNameTextField);
        selectedDisplayBox.setSpacing(10);

        Button save = new Button("Save");
        save.setDisable(true);

        tableNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.trim().equals("")){
                save.setDisable(false);
            }else{
                save.setDisable(true);
            }
        });

        save.setOnAction(e->{
            String tableName = tableNameTextField.getText().trim();

            if(!tableName.equals("")){
//                for (int i = 0; i < tableNames.size(); i++) {
//                    if(tableNames.get(i).equals(tableName)){
//                        return;
//                    }
//                }
                setTableName(tableName);
                System.out.println(this.tableName);
                close();
            }

        });
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e->{
            close();
        });


        buttonsBox.getChildren().addAll(save,cancel);
        buttonsBox.setSpacing(20);

        mainBox.setPadding(new Insets(20,20,20,20));
        mainBox.setSpacing(30);

        mainBox.getChildren().addAll(scrollPane,selectedDisplayBox,buttonsBox);

        Scene scene = new Scene(mainBox, 330,300);
        scene.getStylesheets().add(Objects.requireNonNull(InnerScene.class.getResource("modal.css")).toExternalForm());
        setScene(scene);
    }

}

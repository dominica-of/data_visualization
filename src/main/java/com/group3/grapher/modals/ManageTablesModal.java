package com.group3.grapher.modals;

import com.group3.grapher.InnerScene;
import com.group3.grapher.database.models.Table;
import com.group3.grapher.database.models.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class ManageTablesModal extends Stage {
    String tableName;
    String newTableName;

    public String getNewTableName() {
        return newTableName;
    }

    public void setNewTableName(String newTableName) {
        this.newTableName = newTableName;
    }

    public String getTableName() {
        return tableName;
    }

    private void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ManageTablesModal(Stage stage) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(stage);

        VBox mainBox = new VBox();
        HBox superBox = new HBox();

        setTitle("Manage Tables");
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
        HBox newNameBox = new HBox();
        Label selectedTableNameLabel = new Label("Selected table:");
        selectedTableNameLabel.setId("selectedLabel");
        Label newNameLabel = new Label("New table name:");
        newNameLabel.setId("selectedLabel");
        TextField selectedTableNameTextField = new TextField();
        TextField newNameTextField = new TextField();
        selectedTableNameTextField.setEditable(false);

        newNameBox.getChildren().addAll(newNameLabel,newNameTextField);
        newNameBox.setSpacing(10);
        selectedDisplayBox.getChildren().addAll(selectedTableNameLabel,selectedTableNameTextField);
        selectedDisplayBox.setSpacing(10);

        Button renameButton = new Button("Rename");
        Button deleteButton = new Button("Delete");
        renameButton.setDisable(true);
        deleteButton.setDisable(true);
        tableNameList.setOnMouseClicked(e->{
            if(tableNameList.getSelectionModel().getSelectedItem()!=null){
                renameButton.setDisable(false);
                deleteButton.setDisable(false);
                selectedTableNameTextField.setText(tableNameList.getSelectionModel().getSelectedItem().getText().trim());
            }
        });

        deleteButton.setOnAction(e->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "DeLeted tables CANNOT be recovered");
            alert.setHeaderText("Are you sure you want to delete TABLE: " +
                    tableNameList.getSelectionModel().getSelectedItem().getText().trim());
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()){
                if(result.get() == ButtonType.OK){
                    table.deleteTable(User.email, tableNameList.getSelectionModel().getSelectedItem().getText().trim());
                    tableNameList.getItems().remove(tableNameList.getSelectionModel().getSelectedItem());
                }
            }
        });
        renameButton.setOnAction(e->{
            String selectedTable = tableNameList.getSelectionModel().getSelectedItem().getText().trim();
            String newTableName = newNameTextField.getText().trim();

            if(!selectedTable.equals("") && !newTableName.equals("")){
                if(table.updateTableName(User.email,selectedTable,newTableName)){
                    tableNameList.getSelectionModel().getSelectedItem().setText(newTableName);
                }
            }

        });
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e->{
            close();
        });


        buttonsBox.getChildren().addAll(renameButton,cancel);
        buttonsBox.setSpacing(20);

        mainBox.setPadding(new Insets(20,20,20,20));
        mainBox.setSpacing(30);

        StackPane deleteStackPane = new StackPane();
        deleteStackPane.getChildren().add(deleteButton);
        mainBox.getChildren().addAll(scrollPane,selectedDisplayBox,newNameBox,buttonsBox);
        superBox.getChildren().addAll(mainBox,deleteStackPane);
        Scene scene = new Scene(superBox, 480,300);
        scene.getStylesheets().add(Objects.requireNonNull(InnerScene.class.getResource("modal.css")).toExternalForm());
        setScene(scene);
    }

}

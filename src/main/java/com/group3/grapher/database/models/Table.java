package com.group3.grapher.database.models;

import com.group3.grapher.State;
import com.group3.grapher.XandYs;
import com.group3.grapher.database.Connections;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Table {
    Connection conn = Connections.getConnection();
    ObservableList<XandYs> data = FXCollections.observableArrayList();

    ObservableList<TableColumn<XandYs,String>> columns = FXCollections.observableArrayList();

    public Table(){

    }

    public Table(String email, String tableName, ObservableList tableData,
                 ObservableList<TableColumn<XandYs,?>> tableColumns) {
        ArrayList<String> columnNames = new ArrayList<>();
        for (int i = 0; i < tableColumns.size(); i++) {
            columnNames.add(tableColumns.get(i).getText());
        }

        try {
            Statement statement = conn.createStatement();
            String sql = String.format("INSERT INTO tables (email, \"tableName\", \"tableData\", \"tableColumns\") " +
                    "VALUES ('%s', '%s', %s, %s)", email, tableName, myDataToPGString(tableData)
                    , myColumnsToPGString(tableColumns));
            int rows = statement.executeUpdate(sql);
            System.out.println(rows);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "TABLE: "+tableName+" SAVED");
            alert.setHeaderText("SAVED");
            alert.show();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "TABLE: "+tableName+" NOT SAVED");
            alert.setHeaderText("TABLE WITH THE SAME NAME ALREADY EXISTS");
            alert.show();
        }
    }

    public ObservableList<XandYs> getData() {
        return data;
    }

    public ObservableList<TableColumn<XandYs, String>> getColumns() {
        return columns;
    }

    private String myColumnsToPGString(ObservableList<TableColumn<XandYs,?>> tableColumns){
        StringBuilder sb = new StringBuilder();
        sb.append("array[");

        for (int i = 0; i < tableColumns.size(); i++) {
            sb.append("'");
            sb.append(tableColumns.get(i).getText());
            sb.append("'");

            if(i != tableColumns.size()-1){
                sb.append(", ");
            }

        }

        sb.append("]");
        return sb.toString();
    }

    private String myDataToPGString(ObservableList<XandYs> data){
        StringBuilder sb = new StringBuilder();
        sb.append("array[");
        for (int i = 0; i < data.size(); i++) {
            sb.append("array[");
            sb.append("'");
            sb.append(data.get(i).getX());
            sb.append("'");

            if(data.get(i).getY().size() != 0){
                sb.append(", ");
            }

            for (int j = 0; j < data.get(i).getY().size(); j++) {
                sb.append("'");
                sb.append(data.get(i).getY(j));
                sb.append("'");

                if(j != data.get(i).getY().size()-1){
                    sb.append(", ");
                }

            }

            sb.append("]");

            if(i!=data.size()-1){
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public TableView getTable(String email, String tableName, State state){
        TableView<XandYs> tableView = new TableView<>();
        try {
            Statement s = conn.createStatement();
            String sql = String.format("SELECT * FROM tables WHERE email='%s' AND \"tableName\"='%s'",email,tableName);
            ResultSet result = s.executeQuery(sql);

            if(result.next()){
                this.data = pgDataResultsConverter(result);
                tableView.setItems(this.data);
                String[] colNames = pgColumnNamesResultsConverter(result);


                TableColumn<XandYs, String> xColumn = new TableColumn<>(colNames[0]);
                xColumn.setMinWidth(150);
                xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
                xColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                xColumn.setOnEditCommit(xandYsStringCellEditEvent -> {
                    XandYs point = xandYsStringCellEditEvent.getRowValue();
                    point.setX(xandYsStringCellEditEvent.getNewValue());
                    state.setEditted(true);
                });
                columns.add(xColumn);
                XandYs.maxYSize=0;

                for (int i = 1; i < colNames.length; i++) {
                    XandYs.maxYSize++;
                    TableColumn<XandYs, String> yColumn = new TableColumn<>(colNames[i]);
                    yColumn.setMinWidth(100);
                    int finalI = XandYs.maxYSize - 1;
                    yColumn.setCellValueFactory(celldata->new SimpleStringProperty(celldata.getValue().getY(finalI)));
                    yColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                    yColumn.setOnEditCommit(xyPairStringCellEditEvent -> {
                        XandYs point = xyPairStringCellEditEvent.getRowValue();
                        point.setY(finalI,xyPairStringCellEditEvent.getNewValue());
                        state.setEditted(true);
                    });
                    columns.add(yColumn);

                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "TABLE LOADED");
                alert.setHeaderText("TABLE: "+tableName+" HAS BEEN LOADED");
                alert.show();

            }

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "TABLE NOT LOADED");
            alert.setHeaderText("TABLE: "+tableName+" COULD NOT BE LOADED");
            alert.show();
            System.out.println(e.getMessage());
        }

        return tableView;
    }

    public ArrayList<String> getAllUserTables(String email){
        ArrayList<String> tableNames = new ArrayList<>();
        try {
            Statement s = conn.createStatement();
            String sql = String.format("SELECT * FROM tables WHERE email='%s' ",email);
            ResultSet result = s.executeQuery(sql);

            while(result.next()){
                tableNames.add(result.getString(2));
            }

            System.out.println(tableNames);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return tableNames;
    }

    public Boolean updateTableData(String email, String tableName,ObservableList tableData,
                                   ObservableList<TableColumn<XandYs,?>> tableColumns) {
        ArrayList<String> columnNames = new ArrayList<>();
        for (int i = 0; i < tableColumns.size(); i++) {
            columnNames.add(tableColumns.get(i).getText());
        }

        Boolean updated = false;
        try {
            Statement statement = conn.createStatement();
            String sql = String.format("UPDATE tables SET \"tableData\"=%s, \"tableColumns\"=%s" +
                            "\tWHERE email='%s' AND \"tableName\"='%s' ", myDataToPGString(tableData)
                    , myColumnsToPGString(tableColumns), email, tableName);
            int rows = statement.executeUpdate(sql);
            System.out.println(rows);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "TABLE: "+tableName+" UPDATED");
            alert.setHeaderText("UPDATED");
            alert.showAndWait();
            updated = true;
            System.out.println(tableName);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "TABLE: "+tableName+" NOT UPDATED");
            alert.setHeaderText("UPDATE FAILED");
            alert.showAndWait();
            System.out.println(e.getMessage());
        }

        return updated;
    }

    public Boolean updateTableName(String email, String oldTableName,String newTableName) {

        Boolean updated = false;
        try {
            Statement statement = conn.createStatement();
            String sql = String.format("UPDATE tables SET \"tableName\"='%s' " +
                            "WHERE email='%s' AND \"tableName\"='%s' ", newTableName, email, oldTableName);
            System.out.println(sql);
            int rows = statement.executeUpdate(sql);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "TABLE: "+oldTableName+" UPDATED");
            alert.setHeaderText("TABLE: "+oldTableName+" RENAMED TO "+newTableName);
            alert.showAndWait();
            updated = true;

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "TABLE: "+oldTableName+" NOT UPDATED");
            alert.setHeaderText("UPDATE FAILED");
            alert.showAndWait();
            System.out.println(e.getMessage());
        }

        return updated;
    }

    public Boolean deleteTable(String email, String tableName) {

        Boolean updated = false;
        try {
            Statement statement = conn.createStatement();
            String sql = String.format("DELETE FROM tables WHERE email='%s' " +
                    "AND \"tableName\"='%s' ", email, tableName);
            System.out.println(sql);
            int rows = statement.executeUpdate(sql);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "TABLE: " + tableName + " DELETED");
            alert.setHeaderText("TABLE: " + tableName + " HAS BEEN DELETED");
            alert.showAndWait();
            updated = true;

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "TABLE: " + tableName + " NOT DELETED");
            alert.setHeaderText("DELETE FAILED");
            alert.showAndWait();
            System.out.println(e.getMessage());
        }
        return updated;
    }
    private ObservableList<XandYs> pgDataResultsConverter(ResultSet result) throws SQLException {
        ObservableList<XandYs> data = FXCollections.observableArrayList();

        String[][] dataArray = (String[][]) result.getArray(3).getArray();

        for (int i = 0; i < dataArray.length; i++) {
                XandYs rowData = new XandYs(dataArray[i][0], Arrays.copyOfRange(dataArray[i], 1, dataArray[i].length));
                data.add(rowData);
        }
        
        return data;
    }

    private String[] pgColumnNamesResultsConverter(ResultSet result) throws SQLException {

        String[] columnsArray = (String[]) result.getArray(4).getArray();

        return columnsArray;
    }

}

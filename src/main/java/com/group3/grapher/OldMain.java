//package com.group3.grapher;
//
//import com.group3.grapher.customComponents.NumberTextField;
//import com.group3.grapher.database.models.Table;
//import com.group3.grapher.graphs.*;
//import javafx.application.Application;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.control.cell.TextFieldTableCell;
//import javafx.scene.layout.*;
//import javafx.scene.paint.Color;
//import javafx.stage.Stage;
//import org.kordamp.ikonli.javafx.FontIcon;
//
//import java.util.Arrays;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.concurrent.atomic.AtomicReference;
//
//public class Main extends Application {
//    String menuItems[] = {"File", "Home", "Plot", "Settings"};
//
////    User user = new User("Jerry", "John", "password");
////    Table table = new Table("Jerry","Students",
////        "array[array['s','1','2','5']]","array['Name','Marks','Course','Year']");
//
//
//    @Override
//    public void start(Stage stage) throws Exception {
//
//        String userName;
//        //rgb(238, 99, 82) red
//        //rgb(184,194,199) gray
//        //rgb(34, 116, 165) blue
//        //rgb(34, 116, 165) lightgray for text
//
//        AtomicReference<ObservableList<XandYs>> data = new AtomicReference<>(FXCollections.observableArrayList());
//
//        BorderPane borderPane = new BorderPane();
//        Scene scene = new Scene(borderPane,1000,600);
//
//        ComboBox columnSelectionComboBox = new ComboBox();
//        ComboBox deleteColumnComboBox = new ComboBox();
//        NumberTextField fromRowNum = new NumberTextField();
//        fromRowNum.setPrefWidth(30);
//        NumberTextField toRowNum = new NumberTextField();
//        toRowNum.setPrefWidth(30);
//        NumberTextField rowNumTextField = new NumberTextField();
//        // force the field to be numeric only
//
//        Button button[] = new Button[menuItems.length];
//
//        for (int i = 0; i < menuItems.length; i++) {
//            button[i] = new Button(menuItems[i]);
//            button[i].setBackground(rgbBackground(34, 116, 165));
//            button[i].setTextFill(Color.rgb(221, 226, 228));
//            button[i].setId("mainMenuButton");
//            button[i].setPrefSize(80,40);
//            int finalI = i;
//
//            button[i].hoverProperty().addListener(((observableValue, aBoolean, t1) -> {
//                if(t1){
//                    button[finalI].setBackground(rgbBackground(221, 227, 228));
//                    button[finalI].setTextFill(Color.BLACK);
//                }else{
//                    button[finalI].setBackground(rgbBackground(34, 116, 165));
//                    button[finalI].setTextFill(Color.rgb(221, 226, 228));
//                }
//            }));
//        }
//
//
//
//
//
//
//
//        TableView<XandYs> tableView = new TableView<>();
//
//        tableView.setItems(data.get());
//        tableView.setEditable(true);
//
//        //Add these 2 buttons to insert tab sub-menu. Check Ms-word window
//        Button addRowButton = new Button("Add row");
//        Button addRowsButton = new Button("Add rows");
//        Button deleteRowsButton = new Button("Delete Rows");
//
//        addRowButton.setId("addRow");
//        addRowsButton.setOnAction(e->{
//            try{
//                for (int i = 0; i < Integer.parseInt(rowNumTextField.getText()); i++) {
//                    data.get().add(new XandYs());
//                }
//            }catch (NumberFormatException exception){
//                Alert alert = new Alert(Alert.AlertType.INFORMATION, "NO ROWS ADDED");
//                alert.setHeaderText("Specify number of rows to add");
//                alert.show();
//                return;
//            }
//        });
//        addRowButton.setOnAction(e->{
//            data.get().add(new XandYs());
//        });
//
//        Button deleteColumnButton = new Button("Delete column");
//        Button addColumnButton = new Button("Add column");
//        addColumnButton.setId("addColumn");
//
//        deleteColumnButton.setOnAction(e->{
//            if(deleteColumnComboBox.getValue() == null){
//                Alert alert = new Alert(Alert.AlertType.INFORMATION, "YOU NEED TO SELECT A FIELD TO DELETE");
//                alert.setHeaderText("No field Selected");
//                alert.show();
//                return;
//            }
//            int deleteIndex = deleteColumnComboBox.getItems().indexOf(deleteColumnComboBox.getValue());
//            tableView.getColumns().remove(deleteIndex+1);
//            deleteColumnComboBox.getItems().remove(deleteIndex);
//            columnSelectionComboBox.getItems().remove(deleteIndex);
//        });
//
//        deleteRowsButton.setOnAction(e->{
//            if(fromRowNum.getText() == "" || toRowNum.getText() == ""){
//                Alert alert = new Alert(Alert.AlertType.ERROR, "YOU NEED TO SPECIFY ROW RANGE TO DELETE");
//                alert.setHeaderText("DELETION RANGE NOT SPECIFIED");
//                alert.show();
//                return;
//            }
//            int deleteRowStartIndex = Integer.parseInt(fromRowNum.getText());
//            int deleteRowEndIndex = Integer.parseInt(toRowNum.getText());
//
//            if( (data.get().size() < deleteRowStartIndex) ||
//                    (data.get().size() < deleteRowEndIndex) || (deleteRowStartIndex > deleteRowEndIndex)){
//                Alert alert = new Alert(Alert.AlertType.ERROR, "INVALID ROW RANGE");
//                alert.setHeaderText("INVALID ROW RANGE SPECIFIED");
//                alert.show();
//                return;
//            }
//            data.get().remove(deleteRowStartIndex-1, deleteRowEndIndex);
//
//        });
//
//        addColumnButton.setOnAction(e->{
//            TextInputDialog inputDialog = new TextInputDialog("MyColumn");
//            inputDialog.setHeaderText("Enter Column Name");
//            inputDialog.setTitle("Column Creation Dialog");
//            Optional<String> result2 =  inputDialog.showAndWait();
//
//            if(result2.isPresent()){
//                XandYs.maxYSize++;
//
//                for (int i = 0; i < data.get().size(); i++) {
//                    data.get().get(i).addY("_");
//                }
//
//                String colName = inputDialog.getEditor().getText();
//                for (int i = 0; i < tableView.getColumns().size(); i++) {
//                    if(colName.equals(tableView.getColumns().get(i).getText())){
//                        Alert alert = new Alert(Alert.AlertType.ERROR,"Duplicate Column Name NOT ALLOWED");
//                        alert.show();
//                        return;
//                    }
//                }
//                TableColumn<XandYs, String> yColumn = new TableColumn<>(inputDialog.getEditor().getText());
//                yColumn.setMinWidth(130);
//                final int i = XandYs.maxYSize - 1;
//                yColumn.setCellValueFactory(celldata->new SimpleStringProperty(celldata.getValue()
//                        .getY(i)));
//                yColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//                yColumn.setOnEditCommit(xyPairStringCellEditEvent -> {
//                    XandYs point = xyPairStringCellEditEvent.getRowValue();
//                    point.setY(i,xyPairStringCellEditEvent.getNewValue());
//                });
//                tableView.getColumns().add(yColumn);
//                columnSelectionComboBox.getItems().add(yColumn.getText());
//                deleteColumnComboBox.getItems().add(yColumn.getText());
//            }
//
//        });
//
//
//        VBox mainBox = new VBox();
//        VBox subMenus = new VBox();
//        HBox fileMenu = new HBox();
//        HBox homeMenu = new HBox();
//        HBox plotMenu = new HBox();
//        HBox settingsMenu = new HBox();
//        HBox tableBox = new HBox();
//
//        Button loadTable = new Button("Load");
//        Button saveTable = new Button("Save");
//
//        saveTable.setOnAction(e->{
//            //save
//            Table table = new Table("Jerry","A",tableView.getItems(),tableView.getColumns());
//        });
//        loadTable.setOnAction(e->{
//            Table table = new Table();
//            //load
//            table.getTable("Jerry", "A");
//
//            String[] colNames = extractColumnNames(table.getColumns());
//            colNames = Arrays.copyOfRange(colNames,1,colNames.length);
//
//            tableView.getColumns().clear();
//            tableView.getItems().clear();
//            data.set(table.getData());
////            data.set(table.getData()); //Has been set atomic, if there's an error change it;
//            tableView.setItems(table.getData());
//            tableView.getColumns().addAll(table.getColumns());
//            columnSelectionComboBox.getItems().clear();
//            deleteColumnComboBox.getItems().clear();
//            deleteColumnComboBox.getItems().addAll(colNames);
//            columnSelectionComboBox.getItems().addAll(colNames);
//        });
//
//        fileMenu.getChildren().addAll(loadTable, saveTable);
//        HBox[] subMenusArray = {fileMenu, homeMenu, plotMenu, settingsMenu};
////        MyTabMenu myTabMenu = new MyTabMenu(menuItems, subMenusArray);
//        fileMenu.setMinHeight(100);
//        homeMenu.setMinHeight(100);
//        plotMenu.setMinHeight(100);
//        settingsMenu.setMinHeight(100);
//
//        fileMenu.setId("innerMenu");
//        homeMenu.setId("innerMenu");
//        plotMenu.setId("innerMenu");
//        settingsMenu.setId("innerMenu");
//
//        // Home / Insert menu
//        VBox deleteColumnBox = new VBox();
//        VBox addRowsBox = new VBox();
//
//        HBox rowBox = new HBox();
//        VBox rowSubBox1 = new VBox();
//        VBox rowSubBox2 = new VBox();
//        VBox columnBox = new VBox();
//        Label rowSubBox2label = new Label("Delete Rows from");
//        HBox fromtoBox = new HBox();
//        fromtoBox.setSpacing(5);
//        Label tolabel = new Label("to");
//        fromtoBox.getChildren().addAll(fromRowNum,tolabel,toRowNum);
//        columnBox.setSpacing(5);
//        columnBox.setId("subBox");
//        rowSubBox1.setSpacing(5);
//        rowSubBox1.setId("prevSubBox");
//        rowSubBox2.setId("subBox");
//        rowSubBox2.setSpacing(5);
//
//        rowSubBox1.getChildren().addAll(addRowButton, addRowsBox);
//        rowSubBox2.getChildren().addAll(rowSubBox2label,fromtoBox,deleteRowsButton);
//        rowBox.getChildren().addAll(rowSubBox1, rowSubBox2);
//        columnBox.getChildren().addAll(addColumnButton,deleteColumnBox);
//        addRowsBox.getChildren().addAll(rowNumTextField, addRowsButton);
//        deleteColumnBox.getChildren().addAll(deleteColumnComboBox, deleteColumnButton);
//        homeMenu.getChildren().addAll(rowBox, columnBox);
//
//        //Plot Menu
//        for (int i = 0; i < tableView.getColumns().size(); i++) {
//            columnSelectionComboBox.getItems().add(tableView.getColumns().get(i).getText());
//            deleteColumnComboBox.getItems().add(tableView.getColumns().get(i).getText());
//        }
//
//        TilePane plotMenuPane = new TilePane();
//        VBox plotSettingsBox = new VBox();
//        plotSettingsBox.setId("plotSetBox");
//        HBox plotGraphButtonsBox = new HBox();
//        plotMenuPane.getChildren().add(plotSettingsBox);
//        plotMenuPane.getChildren().add(plotGraphButtonsBox);
//
//        columnSelectionComboBox.setMinWidth(200);
//        deleteColumnComboBox.setMinWidth(100);
//
//        Label comboLabel = new Label("Select field to plot");
//
//        plotSettingsBox.getChildren().addAll(comboLabel,columnSelectionComboBox);
//
//        Button linePlotButton = new Button("Line Graph");
//        Button piePlotButton = new Button("Pie Chart");
//        Button barPlotButton = new Button("Bar Chart");
//        Button histoPlotButton = new Button("Histogram");
//        Button scatterPlotButton = new Button("Scatter Chart");
//        Button areaPlotButton = new Button("Area Chart");
//
//        linePlotButton.setGraphic(new FontIcon("mdi2c-chart-line"));
//        piePlotButton.setGraphic(new FontIcon("mdi2c-chart-pie"));
//        barPlotButton.setGraphic(new FontIcon("mdi2c-chart-bar"));
//        histoPlotButton.setGraphic(new FontIcon("mdi2c-chart-histogram"));
//        scatterPlotButton.setGraphic(new FontIcon("mdi2c-chart-scatter-plot"));
//        areaPlotButton.setGraphic(new FontIcon("mdi2c-chart-areaspline-variant"));
//
//
//
//
//
//        LineGraph lineGraph = new LineGraph(stage);
//        PieGraph pieGraph = new PieGraph(stage);
//        BarGraph barGraph = new BarGraph(stage);
//        Histogram histogram = new Histogram(stage);
//        ScatterGraph scatterGraph = new ScatterGraph(stage);
//        AreaGraph areaGraph = new AreaGraph(stage);
//
//        Button[] graphButtons = {linePlotButton,piePlotButton,barPlotButton,histoPlotButton,
//                scatterPlotButton,areaPlotButton};
//
//        Graph[] graphWindows = {lineGraph,pieGraph,barGraph,histogram,scatterGraph,areaGraph};
//
//        for (int i = 0; i < graphButtons.length; i++) {
//            int finalI = i;
//            graphButtons[i].setOnAction(e->{
//                if(columnSelectionComboBox.getValue() == null){
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "YOU NEED TO SELECT A FIELD TO PLOT");
//                    alert.setHeaderText("No field Selected");
//                    alert.show();
//                } else{
//                    int selectedColumnIndex = columnSelectionComboBox.getItems().indexOf(columnSelectionComboBox.getValue());
//                    try {
//                        graphWindows[finalI].createGraph(data.get(),tableView,selectedColumnIndex);
//                        graphWindows[finalI].show();
//                    }catch (NumberFormatException exception){
//                        Alert alert = new Alert(Alert.AlertType.ERROR, "ALL DEPENDENT COLUMN VALUES MUST BE NUMERIC");
//                        alert.setHeaderText("INVALID ROW DATA");
//                        alert.show();
//                    }
//                }
//            });
//        }
//
//        plotGraphButtonsBox.getChildren().addAll(graphButtons);
//        plotMenu.getChildren().addAll(plotMenuPane);
//
//
//        subMenus.getChildren().add(homeMenu);
//
//        button[0].setOnAction(e->{
//            subMenus.getChildren().clear();
//            subMenus.getChildren().add(fileMenu);
//        });
//
//        button[1].setOnAction(e->{
//            subMenus.getChildren().clear();
//            subMenus.getChildren().add(homeMenu);
//        });
//
//        button[2].setOnAction(e->{
//            subMenus.getChildren().clear();
//            subMenus.getChildren().add(plotMenu);
//        });
//
//        button[3].setOnAction(e->{
//            subMenus.getChildren().clear();
//            subMenus.getChildren().add(settingsMenu);
//        });
//
//
//        HBox topMenu = new HBox();
//
//        mainBox.getChildren().add(subMenus);
//
//        topMenu.getChildren().addAll(button);
//        topMenu.setBackground(rgbBackground(34, 116, 165));
//        topMenu.setPadding(new Insets(0,10,0,20));
//
//        tableBox.setPadding(new Insets(10,10,10,10));
//        tableBox.getChildren().addAll(tableView);
//        mainBox.getChildren().add(tableBox);
//
//        borderPane.setTop(topMenu);
//        borderPane.setCenter(mainBox);
//
//        //Main window
//        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("app.css")).toExternalForm());
//        stage.setTitle("Grapher");
//        stage.setScene(LoginScene.getScene(stage));
//
//        tableView.setPrefWidth(2000);
//        tableView.setPrefHeight(2000);
//        stage.show();
//        //
//
//        //On Launch Input Dialogs
//        TextInputDialog textInputDialog = new TextInputDialog("x");
//        textInputDialog.setHeaderText("Enter Independent Variable(X) Column Name");
//        textInputDialog.setTitle("Column Creation Dialog");
//        Optional<String> result =  textInputDialog.showAndWait();
//
//        if(result.isPresent()){
//
//            TableColumn<XandYs, String> xColumn = new TableColumn<>(textInputDialog.getEditor().getText());
//            xColumn.setMinWidth(150);
//            xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
//            xColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//            xColumn.setOnEditCommit(xandYsStringCellEditEvent -> {
//                XandYs point = xandYsStringCellEditEvent.getRowValue();
//                point.setX(xandYsStringCellEditEvent.getNewValue());
//            });
//            tableView.getColumns().add(xColumn);
//        }
//
//        textInputDialog = new TextInputDialog("y");
//        textInputDialog.setHeaderText("Enter Dependent Variable(Y) Column Name");
//        textInputDialog.setTitle("Column Creation Dialog");
//
//        result =  textInputDialog.showAndWait();
//
//        if(result.isPresent()){
//            XandYs.maxYSize++;
//            TableColumn<XandYs, String> yColumn = new TableColumn<>(textInputDialog.getEditor().getText());
//            yColumn.setMinWidth(100);
//            int finalI = XandYs.maxYSize - 1;
//            yColumn.setCellValueFactory(celldata->new SimpleStringProperty(celldata.getValue().getY(finalI)));
//            yColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//            yColumn.setOnEditCommit(xyPairStringCellEditEvent -> {
//                XandYs point = xyPairStringCellEditEvent.getRowValue();
//                point.setY(finalI,xyPairStringCellEditEvent.getNewValue());
//            });
//
//            tableView.getColumns().add(yColumn);
//            columnSelectionComboBox.getItems().add(yColumn.getText());
//            deleteColumnComboBox.getItems().add(yColumn.getText());
//            data.get().add(new XandYs());
//        }
//    }
//
//    public static Background rgbBackground(int r,int g, int b){
//        return new Background(new BackgroundFill(Color.rgb(r, g, b),CornerRadii.EMPTY, Insets.EMPTY));
//    }
//
//    public static String[] extractColumnNames(ObservableList<TableColumn<XandYs,String>> columns){
//        String[] tabColNames = new String[columns.size()];
//        for (int i = 0; i < columns.size(); i++) {
//            tabColNames[i] = columns.get(i).getText();
//        }
//        return tabColNames;
//    }
//
//}
//

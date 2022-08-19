package com.group3.grapher.graphs;

import com.group3.grapher.XandYs;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PieGraph extends Stage implements Graph {
    public PieGraph(Stage stage){
        super();
        initModality(Modality.WINDOW_MODAL);
        initOwner(stage);
    }

    @Override
    public void createGraph(ObservableList<XandYs> points, TableView<XandYs> tableView,int selectedColumnIndex){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(tableView.getColumns().get(0).getText());
        yAxis.setLabel(tableView.getColumns().get(selectedColumnIndex+1).getText());

        final PieChart pieChart = new PieChart();

        for (int i = 0; i < points.size(); i++) {
            PieChart.Data slice = new PieChart.Data(points.get(i).getX(), Double.parseDouble(points
                    .get(i).getY(selectedColumnIndex)));
            pieChart.getData().add(slice);
        }


        pieChart.setTitle("Pie Chart");
        setScene(new Scene(pieChart));
        setTitle("Graph");
    }

}

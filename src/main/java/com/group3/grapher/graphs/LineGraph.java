package com.group3.grapher.graphs;

import com.group3.grapher.XandYs;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class LineGraph extends Stage implements Graph {

    public LineGraph(Stage stage) {
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

        final LineChart<String,Number> lineChart = new LineChart<>(xAxis,yAxis);

        lineChart.setTitle("Line Graph");

        XYChart.Series series = new XYChart.Series();
        series.setName("My Data");

        for (int i = 0; i < points.size(); i++) {
            series.getData().add(new XYChart.Data(points.get(i).getX(), Double.parseDouble(points
                    .get(i).getY(selectedColumnIndex))));
        }

        lineChart.getData().add(series);
        setScene(new Scene(lineChart));
        setTitle("Line Graph");
    }
}

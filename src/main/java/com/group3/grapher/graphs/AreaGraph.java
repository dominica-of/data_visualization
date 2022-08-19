package com.group3.grapher.graphs;

import com.group3.grapher.XandYs;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class AreaGraph extends Stage implements Graph{

    public AreaGraph(Stage stage) {
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

        final AreaChart areaChart = new AreaChart(xAxis,yAxis);

        areaChart.setTitle("Area Graph");

        XYChart.Series series = new XYChart.Series();
        series.setName("My Data");

        for (int i = 0; i < points.size(); i++) {
            series.getData().add(new XYChart.Data(points.get(i).getX(), Double.parseDouble(points
                    .get(i).getY(selectedColumnIndex))));
        }

        areaChart.getData().add(series);
        setScene(new Scene(areaChart));
        setTitle("Graph");
    }
}

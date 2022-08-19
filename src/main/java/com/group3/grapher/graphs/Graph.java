package com.group3.grapher.graphs;

import com.group3.grapher.XandYs;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.TableView;

public interface Graph {
    void createGraph(ObservableList<XandYs> points, TableView<XandYs> tableView, int selectedColumnIndex);
    void show();
}

package com.group3.grapher;

import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XandYs {

    public static int maxYSize = 0;

    private String x;
    private List<String> y = FXCollections.observableArrayList();
    Boolean isEmpty;


    public XandYs() {
        this.isEmpty = true;
        this.x = "_";
        for (int i = 0; i < maxYSize; i++) {
            y.add("_");
        }
    }

    public XandYs(String x, String y) {
        this.x = x;
        this.y.add(y);
        this.isEmpty = false;
    }

    public XandYs(String x, String[] y) {
        this.x = x;
        this.y = new ArrayList<>(Arrays.asList(y));
        this.isEmpty = false;
    }

    public Boolean getEmpty() {
        return isEmpty;
    }

    public void setEmpty(Boolean empty) {
        isEmpty = empty;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY(int index) {
        return this.y.get(index);
    }

    public List<String> getY() {
        return this.y;
    }

    public void setY(int index, String y_i) {
        this.y.set(index, y_i);
    }

    public void setXAndY(String x, String y, int yIndex) {
        this.x = x;
        this.y.set(yIndex,y);
    }

    public void addY(String yValue){
        y.add(yValue);
    }

    @Override
    public String toString() {
        return "XandYs{" +
                "x='" + x + '\'' +
                ", y=" + y +
                ", isEmpty=" + isEmpty +
                '}';
    }
}

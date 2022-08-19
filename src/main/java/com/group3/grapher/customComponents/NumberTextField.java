package com.group3.grapher.customComponents;

import javafx.scene.control.TextField;

public class NumberTextField extends TextField {
    public NumberTextField() {
        textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        setPrefSize(20,10);
    }

    public NumberTextField(String s) {
        super(s);
        textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        setPrefSize(20,10);
    }
}

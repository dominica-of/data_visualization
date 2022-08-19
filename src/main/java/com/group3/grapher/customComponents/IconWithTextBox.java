package com.group3.grapher.customComponents;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class IconWithTextBox extends VBox {
    public IconWithTextBox(String labelText, Button iconButton) {
        Label label = new Label(labelText);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(label);
        getChildren().addAll(iconButton, stackPane);
    }
}

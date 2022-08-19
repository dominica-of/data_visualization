package com.group3.grapher;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import static com.group3.grapher.InnerScene.rgbBackground;

public class MyTabMenu extends VBox {
    HBox mainMenu;
    HBox subMenus;


    MyTabMenu(String[] menuItems,HBox[] subMenuBoxes){
        Button button[] = new Button[menuItems.length];

        mainMenu = new HBox();
        subMenus = new HBox();

        for (int i = 0; i < menuItems.length; i++) {
            button[i] = new Button(menuItems[i]);
            button[i].setBackground(rgbBackground(34, 116, 165));
            button[i].setTextFill(Color.rgb(221, 226, 228));
            button[i].setPrefSize(80,40);
            int finalI = i;
            button[i].setOnAction(e->{
                subMenus.getChildren().clear();
                subMenus.getChildren().add(subMenuBoxes[finalI]);
            });
            button[i].hoverProperty().addListener(((observableValue, aBoolean, t1) -> {
                if(t1){
                    button[finalI].setBackground(rgbBackground(184,194,199));
                    button[finalI].setTextFill(Color.BLACK);
                }else{
                    button[finalI].setBackground(rgbBackground(34, 116, 165));
                    button[finalI].setTextFill(Color.rgb(221, 226, 228));
                }
            }));
        }
        mainMenu.getChildren().addAll(button);
        this.getChildren().addAll(mainMenu, subMenus);
    }
}

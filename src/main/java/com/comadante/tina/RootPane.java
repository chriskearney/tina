package com.comadante.tina;

import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class RootPane extends BorderPane {

    private final ListView listView;
    private final ImageView imageView;

    public RootPane() {
        this.listView = new ListView();
        this.imageView = new ImageView();
        setCenter(imageView);
        setLeft(listView);
    }


}

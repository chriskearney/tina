package com.comadante.tina;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class RootPane extends BorderPane {

    private final ListView<MotionEvent> listView;
    private final ImageView imageView;
    private final ImageRefreshService imageRefreshService;

    public RootPane(EyeballsClient eyeballsClient) {
        ObservableList<MotionEvent> events = FXCollections.observableArrayList();
        listView = new ListView<>(events);
        ListData listData = new ListData(events);
        this.imageView = new ImageView();
        setCenter(imageView);
        setLeft(listView);
        this.imageRefreshService = new ImageRefreshService(imageView, listView, listData, eyeballsClient);
        this.imageRefreshService.startAsync();
        this.listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Image image;
                if (newValue.getEventId().equals(MotionEvent.liveview)) {
                    image = getImage(eyeballsClient.getLatestImage());
                } else {
                    image = getImage(eyeballsClient.getEventImage(newValue.getEventId()));
                }
                imageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private Image getImage(byte[] bytes) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        return new Image(byteArrayInputStream);
    }

}

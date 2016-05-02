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
                if (newValue.getEventId().equals(MotionEvent.liveview)) {
                    byte[] latestImage = eyeballsClient.getLatestImage();
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(latestImage);
                    Image image = new Image(byteArrayInputStream);
                    imageView.setImage(image);
                    return;
                }
                byte[] eventImage = eyeballsClient.getEventImage(newValue.getEventId());
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(eventImage);
                Image image = new Image(byteArrayInputStream);
                imageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}

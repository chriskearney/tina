package com.comadante.tina;

import com.google.common.util.concurrent.AbstractScheduledService;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ImageRefreshService extends AbstractScheduledService {

    private final ImageView imageView;
    private final ListData listData;
    private final EyeballsClient eyeballsClient;

    public ImageRefreshService(ImageView imageView, ListData listData, EyeballsClient eyeballsClient) {
        this.imageView = imageView;
        this.listData = listData;
        this.eyeballsClient = eyeballsClient;
    }

    protected void runOneIteration() throws Exception {
        byte[] latestImage = eyeballsClient.getLatestImage();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(latestImage);
        Image image = new Image(byteArrayInputStream);
        imageView.setImage(image);
        List<MotionEvent> recentMotionEvents = eyeballsClient.getRecentMotionEvents();
        recentMotionEvents.stream().forEach(listData::add);
    }

    protected Scheduler scheduler() {
        return Scheduler.newFixedDelaySchedule(0, 5, TimeUnit.SECONDS);
    }

}

package com.comadante.tina;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.AbstractScheduledService;
import javafx.scene.control.ListView;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ImageRefreshService extends AbstractScheduledService {

    private final ImageView imageView;
    private final ListData listData;
    private final EyeballsClient eyeballsClient;
    private final ListView listView;

    public ImageRefreshService(ImageView imageView, ListView listView, ListData listData, EyeballsClient eyeballsClient) {
        this.imageView = imageView;
        this.listView = listView;
        this.listData = listData;
        this.eyeballsClient = eyeballsClient;
    }

    protected void runOneIteration() throws Exception {
        MotionEvent selectedItem = (MotionEvent) listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null && selectedItem.getEventId().equals(MotionEvent.liveview)) {
            byte[] latestImage = eyeballsClient.getLatestImage();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(latestImage);
            Image image = new Image(byteArrayInputStream);
            imageView.setImage(image);
        }
        List<MotionEvent> recentMotionEvents = eyeballsClient.getRecentMotionEvents();
        listData.add(new MotionEvent(MotionEvent.liveview, System.currentTimeMillis()));
        Lists.reverse(recentMotionEvents).stream().forEach(listData::add);
    }

    protected Scheduler scheduler() {
        return Scheduler.newFixedDelaySchedule(0, 5, TimeUnit.SECONDS);
    }

}

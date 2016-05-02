package com.comadante.tina;


import com.google.common.collect.Maps;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class ListData {

    private final ObservableList<String> observableList;
    private final Map<String, MotionEvent> motionEventMap = Maps.newConcurrentMap();

    public ListData(ObservableList<String> observableList) {
        this.observableList = observableList;
    }

    public void add(MotionEvent motionEvent) {
        Platform.runLater(() -> {
            String formattedDate = getFormattedDate(motionEvent);
            if (!observableList.contains(formattedDate)) {
                observableList.add(formattedDate);
            }
            motionEventMap.put(getFormattedDate(motionEvent), motionEvent);
        });
    }

    private String getFormattedDate(MotionEvent motionEvent) {
        Long timestamp = motionEvent.getTimestamp();
        Date date = new Date(timestamp);
        return date.toString();
    }

    public MotionEvent getMotionEvent(String formattedDate) {
        return motionEventMap.get(formattedDate);
    }
}

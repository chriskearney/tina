package com.comadante.tina;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

import java.util.Collections;

public class ListData {

    private final ObservableList<MotionEvent> observableList;

    public ListData(ObservableList<MotionEvent> observableList) {
        this.observableList = observableList;
    }

    public void add(MotionEvent motionEvent) {
        Platform.runLater(() -> {
            boolean found = false;
            for (MotionEvent event : observableList) {
                if (event.getEventId().equals(motionEvent.getEventId())) {
                    found = true;
                }
            }
            if (!found) {
                observableList.add(motionEvent);
                SortedList<MotionEvent> sorted = observableList.sorted();
            }
        });
    }
}

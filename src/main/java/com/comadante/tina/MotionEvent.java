package com.comadante.tina;


import java.util.Date;

public class MotionEvent {

    public static final String liveview = "Live View";

    private String eventId;
    private Long timestamp;

    public MotionEvent(String eventId, Long timestamp) {
        this.eventId = eventId;
        this.timestamp = timestamp;
    }

    public MotionEvent() {
    }

    public String getEventId() {
        return eventId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        if (eventId.equals(liveview)) {
            return liveview;
        }
        Long timestamp = getTimestamp();
        Date date = new Date(timestamp);
        return date.toString();
    }
}

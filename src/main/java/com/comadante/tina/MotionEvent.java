package com.comadante.tina;


import java.util.Date;

public class MotionEvent {

    private String eventId;
    private Long timestamp;

    public String getEventId() {
        return eventId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        Long timestamp = getTimestamp();
        Date date = new Date(timestamp);
        return date.toString();
    }
}

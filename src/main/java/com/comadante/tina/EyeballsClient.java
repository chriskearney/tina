package com.comadante.tina;

import java.io.IOException;
import java.util.List;

public interface EyeballsClient {

    byte[] getLatestImage() throws IOException;

    List<MotionEvent> getRecentMotionEvents() throws IOException;

    byte[] getEventImage(String eventId) throws IOException;

}

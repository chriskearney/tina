package com.comadante.tina;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.IOUtils;
import com.google.common.collect.Lists;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EyeballsHttpClient implements EyeballsClient {

    private final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory();
    private final ObjectMapper objectMapper = new ObjectMapper();


    public List<MotionEvent> getRecentMotionEvents() throws IOException {
        HttpRequest getRequest = requestFactory.buildGetRequest(new GenericUrl("http://192.168.1.20:4444/event/recent/1000"));
        return objectMapper.readValue(getRequest.execute().parseAsString(), new TypeReference<ArrayList<MotionEvent>>() {});
    }

    public byte[] getLatestImage() throws IOException {
        HttpRequest getRequest = requestFactory.buildGetRequest(new GenericUrl("http://192.168.1.20:4444/image"));
        HttpResponse execute = getRequest.execute();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(execute.getContent(), byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] getEventImage(String eventId) throws IOException {
        HttpRequest getRequest = requestFactory.buildGetRequest(new GenericUrl("http://192.168.1.20:4444/event/" + eventId));
        HttpResponse execute = getRequest.execute();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(execute.getContent(), byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}

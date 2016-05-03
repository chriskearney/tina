package com.comadante.tina;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.Base64;
import com.google.api.client.util.IOUtils;
import com.google.common.collect.Lists;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class EyeballsHttpClient implements EyeballsClient {

    private final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String authHeader;

    public EyeballsHttpClient(String username, String password) throws IOException {
        HttpRequest getRequest = requestFactory.buildGetRequest(new GenericUrl("http://kearney.us:4444/event/recent/1"));
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
        authHeader = "Basic " + new String(encodedAuth);
        getRequest.setHeaders(new HttpHeaders().setAuthorization(authHeader));
        if (!getRequest.execute().isSuccessStatusCode()) {
            throw new RuntimeException();
        }
    }

    public List<MotionEvent> getRecentMotionEvents() throws IOException {
        HttpRequest getRequest = requestFactory.buildGetRequest(new GenericUrl("http://kearney.us:4444/event/recent/100"));
        getRequest.setHeaders(new HttpHeaders().setAuthorization(authHeader));
        return objectMapper.readValue(getRequest.execute().parseAsString(), new TypeReference<ArrayList<MotionEvent>>() {});
    }

    public byte[] getLatestImage() throws IOException {
        HttpRequest getRequest = requestFactory.buildGetRequest(new GenericUrl("http://kearney.us:4444/image"));
        getRequest.setHeaders(new HttpHeaders().setAuthorization(authHeader));
        HttpResponse execute = getRequest.execute();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(execute.getContent(), byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] getEventImage(String eventId) throws IOException {
        HttpRequest getRequest = requestFactory.buildGetRequest(new GenericUrl("http://kearney.us:4444/event/" + eventId));
        getRequest.setHeaders(new HttpHeaders().setAuthorization(authHeader));
        HttpResponse execute = getRequest.execute();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(execute.getContent(), byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}

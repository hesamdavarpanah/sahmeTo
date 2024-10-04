package com.backend.sahmeto.authentication.utils;
import org.asynchttpclient.*;

import static org.asynchttpclient.Dsl.*;

public class RequestSender {
    private String url;
    private final AsyncHttpClient asyncHttpClient = asyncHttpClient();
    public RequestSender() {
    }

    public RequestSender(String url) {
        this.url = url;
    }

    public BoundRequestBuilder postAsyncHTTP(String data) {
        return asyncHttpClient.preparePost(url).setHeader("Content-Type","application/json").setBody(data);
    }
}

package com.txsanyix.asd.utils;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class RestExceptionHandler implements ResponseErrorHandler {
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        System.out.println(response);
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        System.out.println(response);
        return false;
    }
}

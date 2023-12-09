package org.example.client;

import lombok.Data;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.concurrent.Callable;

@Data
public class Client {
    private static Client instance;
    public Client(String url) {
        this.url = url;
    }
    private static String JWTToken;
    private static String contentType = "application/json";
    private String url;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private static String authorization;

    public static void setAuthorization(String authorization) {
        Client.authorization = authorization;
    }

    public HttpResponse<String> post(String endPoint, String payload) {
        final HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url + endPoint))
                .header("Content-Type", contentType)
                .POST(HttpRequest.BodyPublishers.ofString(payload));
        addAuthorizationToken(builder);
        final HttpRequest request = builder.build();
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpResponse<String> patch(String endPoint, String payload) {
        final HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url + endPoint))
                .header("Content-Type", contentType)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(payload));
        addAuthorizationToken(builder);
        final HttpRequest request = builder.build();
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpResponse<String> delete(String endPoint, String payload) {
        final HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url + endPoint+payload))
                .header("Content-Type", contentType)
                .DELETE();
        addAuthorizationToken(builder);
        final HttpRequest request = builder.build();
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private void addAuthorizationToken(HttpRequest.Builder builder) {
        if(Objects.nonNull(authorization)){
            builder.header("Authorization", authorization);
        }
    }

    public HttpResponse<String> get(String endPoint) {
        final HttpRequest.Builder builder = HttpRequest.newBuilder()
               .uri(URI.create(url + endPoint))
               .header("Content-Type", contentType)
                .GET();
        addAuthorizationToken(builder);
        final HttpRequest request = builder.build();
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    public static Client getInstance(String url){

        if(Objects.isNull(instance)){
            instance = new Client(url);
        }else if(!instance.getUrl().equals(url)){
            instance = new Client(url);
        }

        return instance;
    }

}

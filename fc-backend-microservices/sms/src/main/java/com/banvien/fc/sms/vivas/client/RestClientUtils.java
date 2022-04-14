package com.banvien.fc.sms.vivas.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;

public class RestClientUtils {
    private static String X_API_KEY = "X-Api-Key";
    public static ClientResponse instance(String url, Object xmlBody, String cookie, MediaType type, String apiKey){
        Client client = Client.create();
        WebResource webResource = client
                .resource(url);
        ClientResponse response = webResource
                .header("Cookie",cookie)
                .header(X_API_KEY,apiKey)
                .accept(type)
                .post(ClientResponse.class,xmlBody);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        return response;
    }

    public static String cookie(ClientResponse clientResponse){
        String cookie = null;
        if(clientResponse != null){
            cookie = clientResponse.getHeaders().getFirst("Set-Cookie");
        }
        return cookie;
    }

}

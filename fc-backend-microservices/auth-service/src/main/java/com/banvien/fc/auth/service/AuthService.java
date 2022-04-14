package com.banvien.fc.auth.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;

@Service
public class AuthService {

    public boolean isValid(String token) throws UnirestException {
        HttpResponse<String> response = Unirest.post(env.getProperty("security.platform.verifyTokenUri"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("token", token).asString();
        return Boolean.parseBoolean(response.getBody());
    }

    public String getToken(String username, String password, String role, String countryId, String clientId, String clientSecret) throws UnirestException {
        HttpResponse<String> response = Unirest.post(env.getProperty("security.oauth2.client.accessTokenUri"))
                .header("Authorization", "Basic " +
                        Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("username", username)
                .field("password", password)
                .field("role", role)
                .field("countryId", countryId)
                .field("grant_type", "password")
                .field("scope", "ui")
                .asString();
        return response.getBody();
    }

    @PostConstruct
    private void init() {
        Unirest.setTimeouts(0, 0);
    }

    @Autowired
    private Environment env;
}

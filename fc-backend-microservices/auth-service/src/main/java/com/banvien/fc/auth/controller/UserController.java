package com.banvien.fc.auth.controller;

import com.banvien.fc.auth.entities.UserEntity;
import com.banvien.fc.auth.repository.UserRepository;
import com.banvien.fc.auth.service.AuthService;
import com.banvien.fc.common.util.DesEncryptionUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang.NullArgumentException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/current")
    public Principal getUser(Principal principal) {
        return principal;
    }

    @PostMapping("/getToken")
    public String getUiToken(@RequestParam("user") String userDetail) throws JSONException, UnirestException, TimeoutException {
        String data = new String(Base64.getDecoder().decode(userDetail));
        JSONObject user = new JSONObject(DesEncryptionUtils.getInstance().decrypt(data));
        String username = user.getString("username");
        String password = user.getString("password");
        String countryId = user.getString("countryId");
        long time = user.getLong("time");
        long userId = user.getLong("id");
        long currentTime = System.currentTimeMillis();
        if (currentTime - time < 60 * 1000) {
            Optional<UserEntity> entity = userRepository.findById(userId);
            if (entity.isPresent()) {
                return authService.getToken(username,
                        DesEncryptionUtils.getInstance().decrypt(password),
                        entity.get().getRole().getCode(),
                        countryId,
                        env.getProperty("security.oauth2.web.clientId"),
                        env.getProperty("security.oauth2.web.clientSecret"));
            } else {
                throw new NullArgumentException("Id can not be null");
            }
        } else {
            throw new TimeoutException("Request too long [" + currentTime + "] - [" + time + "]");
        }
    }

    @PostMapping("/mobile/getToken")
    public String getMobileToken(@RequestParam("token") String token) throws UnirestException {
        if (authService.isValid(token)) {
            Jwt jwt = JwtHelper.decode(token);
            try {
                JSONObject obj = new JSONObject(jwt.getClaims());
                long userId = obj.getLong("userid");
                Optional<UserEntity> user = userRepository.findById(userId);
                if (user.isPresent()) {
                    return authService.getToken(user.get().getUsername(),
                            DesEncryptionUtils.getInstance().decrypt(user.get().getPassword()),
                            user.get().getRole().getCode(),
                            user.get().getCountryId().toString(),
                            env.getProperty("security.oauth2.mobile.clientId"),
                            env.getProperty("security.oauth2.mobile.clientSecret"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new InvalidTokenException("Invalid token");
    }

    @RequestMapping("/login")
    public ResponseEntity getURL(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("countryId") Long countryId) {
        Map<String, Object> loginData = new HashMap<>();
        loginData.put("username", username);
        loginData.put("password", DesEncryptionUtils.getInstance().encrypt(password));
        loginData.put("countryId", countryId);
        loginData.put("id", userRepository.findByUsernameAndPasswordAndCountryId(username, DesEncryptionUtils.getInstance().encrypt(password), countryId).get(0).getUserid());
        loginData.put("time", System.currentTimeMillis());
        ObjectMapper oMapper = new ObjectMapper();
        String json = null;
        try {
            json = oMapper.writeValueAsString(loginData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String data = DesEncryptionUtils.getInstance().encrypt(json);
        if (username.equals("admin")) {
            return ResponseEntity.ok("http://localhost:4200/admin/promotion/list?user=" + new String(Base64.getEncoder().encode(data.getBytes())));
            //            return ResponseEntity.ok("http://192.168.8.162:4201/admin/promotion/list?user=" + new String(Base64.getEncoder().encode(data.getBytes())));
        } else {
            return ResponseEntity.ok("http://localhost:4200/promotion/list?user=" + new String(Base64.getEncoder().encode(data.getBytes())));
//            return ResponseEntity.ok("http://192.168.8.162:4201/promotion/list?user=" + new String(Base64.getEncoder().encode(data.getBytes())));
        }
    }

    @Autowired
    private AuthService authService;
    @Autowired
    private Environment env;
    @Autowired
    private UserRepository userRepository;
}

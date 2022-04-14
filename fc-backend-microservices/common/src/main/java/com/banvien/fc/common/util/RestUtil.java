package com.banvien.fc.common.util;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by son.nguyen on 7/8/2020.
 */
public class RestUtil {

    public static Long getUserIdFromToken(HttpServletRequest request) {
        String token = StringUtils.defaultString(request.getHeader(HttpHeaders.AUTHORIZATION), "").replaceAll("Bearer ", "");
        if (StringUtils.isNotBlank(token)) {
            Jwt jwt = JwtHelper.decode(token);
            try {
                JSONObject obj = new JSONObject(jwt.getClaims());
                return obj.getLong("userId");
            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static Long getOutletIdFromToken(HttpServletRequest request) {
        String token = StringUtils.defaultString(request.getHeader(HttpHeaders.AUTHORIZATION), "").replaceAll("Bearer ", "");
        if (StringUtils.isNotBlank(token)) {
            Jwt jwt = JwtHelper.decode(token);
            try {
                JSONObject obj = new JSONObject(jwt.getClaims());
                return obj.getLong("outletId");
            } catch (Exception e){
//                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static Long getCountryIdFromToken(HttpServletRequest request) {
        String token = StringUtils.defaultString(request.getHeader(HttpHeaders.AUTHORIZATION), "").replaceAll("Bearer ", "");
        if (StringUtils.isNotBlank(token)) {
            Jwt jwt = JwtHelper.decode(token);
            try {
                JSONObject obj = new JSONObject(jwt.getClaims());
                return obj.getLong("countryId");
            } catch (Exception e){
//                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

//    public static void main(String[] args) throws JSONException {
//        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbInVpIl0sIm91dGxldElkIjo1MjM0LCJleHAiOjE1OTY3OTg0OTEsInVzZXJJZCI6MSwiYXV0aG9yaXRpZXMiOlsiQURNSU4iXSwianRpIjoiOGUxYjkwODUtMTUwYS00YWFhLWIyYzgtZDc5ZDVlMTVjOGY2IiwiY2xpZW50X2lkIjoiYnZXZWJDbGllbnQifQ.IPAPXv4bkBnLnppSctlUaXk-YcGMxWUrmZnSJGtyP4Y";
//        Jwt jwt = JwtHelper.decode(token);
//        JSONObject obj = new JSONObject(jwt.getClaims());
//        int k = 10;
//    }

}

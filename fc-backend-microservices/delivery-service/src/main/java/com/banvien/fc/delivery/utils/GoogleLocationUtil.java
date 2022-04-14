package com.banvien.fc.delivery.utils;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;

import java.util.ArrayList;
import java.util.List;

public class GoogleLocationUtil {
    private static final String API_KEY = "AIzaSyA8xwU0WdtQw1znzhiLo8yuFfS63IP7xC4";
    private static final GeoApiContext context = new GeoApiContext.Builder().apiKey(API_KEY).build();

    public GoogleLocationUtil() {
    }

    /**
     *
     * @param address
     * @return
     * @throws Exception
     */
    public static List<Double> getCoordinate(String address) throws Exception {
        List<Double> coordinate = new ArrayList();
        GeocodingResult[] results = (GeocodingResult[]) GeocodingApi.geocode(context, address).await();
        if (results != null && results.length > 0) {
            Geometry geometry = results[0].geometry;
            coordinate.add(geometry.location.lat);
            coordinate.add(geometry.location.lng);
        }
        return coordinate;
    }

//    public static void main(String[] args) throws Exception {
//        getCoordinate("172 Võ Thị Sáu, Phường 6, Quận 3, Thành phố Hồ Chí Minh, Vietnam");
//    }
}

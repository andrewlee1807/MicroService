package com.banvien.fc.common.util;

import com.google.maps.*;
import com.google.maps.model.*;

import java.util.ArrayList;
import java.util.List;

public class GoogleLocationUtil {
    private static final GeoApiContext.Builder builder = new GeoApiContext.Builder().apiKey(Constants.GOOGLE_API_KEY);
    private static final GeoApiContext context = builder.build();

    public static void main(String[] args) throws Exception {
        List<Double> location = getCoordinate("34 Cống Lỡ, Phường 15, Tan Binh, Ho Chi Minh City, Vietnam");
        System.out.println(location.get(0));
        System.out.println(location.get(1));
        List<String> places = getSameNameLocation("12 Tôn đản");
        int k = 10;
    }

    public static List<Double> getCoordinate(String address) {
        List<Double> coordinate = new ArrayList<>();
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
            if (results != null && results.length > 0) {
                Geometry geometry = results[0].geometry;
                coordinate.add(geometry.location.lat);
                coordinate.add(geometry.location.lng);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return coordinate;
    }

    public static List<String> getSameNameLocation(String address) {
        return getSameNameLocation(address,"vi");
    }

    public static List<String> getSameNameLocation(String address, String language) {
        if (language == null || language.length() == 0) return getSameNameLocation(address);
        List<String> rs = new ArrayList<>();
        try {
            AutocompletePrediction[] places = PlacesApi.queryAutocomplete(context, address).language(language).await();
            for (AutocompletePrediction item : places) {
                rs.add(item.description);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public static String getLocation(Double longitude, Double latitude) throws Exception {
        LatLng latLng = new LatLng(latitude, longitude);
        GeocodingResult[] results = GeocodingApi.reverseGeocode(context, latLng).await();
        return results[0].formattedAddress;
    }

    public static double calculateDistanceFrom2Point(LatLng p1, LatLng p2) {
        double distApart = 0;
        long distance = 0L;
        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);
        try {
            DistanceMatrix result = req.origins(p1)
                    .destinations(p2)
                    .mode(TravelMode.DRIVING)
                    // .avoid(DirectionsApi.RouteRestriction.TOLLS)
                    .language("en-US")
                    .await();
            distance = result.rows[0].elements[0].distance.inMeters;
            distApart = distance / 1000.0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return distApart;
    }
}

package com.banvien.fc.order.utils;

import com.banvien.fc.common.enums.ErrorCodeMap;
import com.banvien.fc.common.rest.errors.BadRequestException;
import com.banvien.fc.common.util.GoogleLocationUtil;
import com.banvien.fc.order.entity.DeliveryServiceEntity;
import com.banvien.fc.order.entity.OutletEntity;
import com.google.maps.model.LatLng;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeliveryUtils {

    public static Double calculateDeliveryPrice(OutletEntity outletEntity, String address, String deliveryMethod, List<DeliveryServiceEntity> entities) {
        List<Double> locationCustomer = GoogleLocationUtil.getCoordinate(address);
        if (locationCustomer.size() != 2) {
            throw new BadRequestException(ErrorCodeMap.FAILURE_INVALID_ADDRESS.name());
        }
        return calculateDeliveryPrice(outletEntity, locationCustomer.get(0), locationCustomer.get(1), deliveryMethod, entities);
    }

    public static Double calculateDeliveryPrice(OutletEntity outletEntity, double lat, double lng, String deliveryMethod, List<DeliveryServiceEntity> entities) {
        double price = 0;
        double distance = 0;

        double R = 6371e3;
        double φ1 = outletEntity.getLatitude().doubleValue() * Math.PI / 180;
        double φ2 = lat * Math.PI / 180;
        double Δφ = (lat - outletEntity.getLatitude().doubleValue()) * Math.PI / 180;
        double Δλ = (lng - outletEntity.getLongitude().doubleValue()) * Math.PI / 180;
        double a = Math.sin(Δφ / 2) * Math.sin(Δφ / 2) + Math.cos(φ1) * Math.cos(φ2) * Math.sin(Δλ / 2) * Math.sin(Δλ / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        distance = (R * c) / 1000;

        entities = entities.stream().filter(item -> item.getStatus()).filter(item -> item.getCode().equals(deliveryMethod))
                .sorted((first, second) -> (int) (first.getDistance() - second.getDistance())).collect(Collectors.toList());

        if (distance > entities.get(entities.size() - 1).getDistance()) {
            return entities.get(entities.size() - 1).getPrice();
        }

        for (DeliveryServiceEntity entity : entities) {
            if (distance <= entity.getDistance()) {
                price = entity.getPrice();
                break;
            }
        }

        return price;
    }

    public static double calculateDeliveryDistance(OutletEntity outletEntity, String address, Double lng, Double lat) {
        List<Double> locationCustomer = new ArrayList<>();
        if (lng == null && lat == null && StringUtils.isNotBlank(address)) {
            locationCustomer = GoogleLocationUtil.getCoordinate(address);
        } else {
            locationCustomer.add(lat);
            locationCustomer.add(lng);
        }
        LatLng latLngFrom = new LatLng(outletEntity.getLatitude().doubleValue(), outletEntity.getLongitude().doubleValue());
        LatLng latLngTo = new LatLng(locationCustomer.get(0), locationCustomer.get(1));
        return GoogleLocationUtil.calculateDistanceFrom2Point(latLngFrom, latLngTo);
    }
}

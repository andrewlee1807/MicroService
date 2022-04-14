package com.banvien.fc.delivery.dto.grabexpress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourierDTO {
    private String name;
    private String phone;
    private String pictureURL;
    private Float rating;
    private CoordinatesDTO coordinates;
    private VehicleDTO vehicle;
}

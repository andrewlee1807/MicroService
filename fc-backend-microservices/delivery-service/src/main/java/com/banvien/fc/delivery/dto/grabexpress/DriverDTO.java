package com.banvien.fc.delivery.dto.grabexpress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {
    private String name;
    private String phone;
    private String licensePlate;
    private String photoURL;
    private Float currentLat;
    private Float currentLng;
}

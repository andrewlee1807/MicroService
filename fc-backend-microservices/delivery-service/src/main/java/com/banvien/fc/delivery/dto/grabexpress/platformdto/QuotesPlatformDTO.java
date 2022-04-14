package com.banvien.fc.delivery.dto.grabexpress.platformdto;

import com.banvien.fc.delivery.dto.grabexpress.DestinationDTO;
import com.banvien.fc.delivery.dto.grabexpress.PackagesDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuotesPlatformDTO {
    private Long customerId;
    private String device;
    private Long outletId;
    private Double latitude;
    private Double longitude;
    private String address;
    private List<PackagesDTO> packages;
    private Double srcLatitude;
    private Double srcLongitude;
}

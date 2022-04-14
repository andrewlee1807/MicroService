package com.banvien.fc.delivery.dto.grabexpress.requestdto;

import com.banvien.fc.delivery.dto.grabexpress.CoreInfoDTO;
import com.banvien.fc.delivery.dto.grabexpress.DestinationDTO;
import com.banvien.fc.delivery.dto.grabexpress.OriginDTO;
import com.banvien.fc.delivery.dto.grabexpress.PackagesDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuotesRequestDTO implements CoreInfoDTO,Serializable {

    private Long customerId;
    private String device;
    private Long outletId;
    private String serviceType;
    private List<PackagesDTO> packages;
    private OriginDTO origin;
    private DestinationDTO destination;
    private String merchantOrderID;
}

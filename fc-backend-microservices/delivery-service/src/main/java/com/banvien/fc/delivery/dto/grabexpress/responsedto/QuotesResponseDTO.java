package com.banvien.fc.delivery.dto.grabexpress.responsedto;

import com.banvien.fc.delivery.dto.grabexpress.DestinationDTO;
import com.banvien.fc.delivery.dto.grabexpress.OriginDTO;
import com.banvien.fc.delivery.dto.grabexpress.PackagesDTO;
import com.banvien.fc.delivery.dto.grabexpress.QuotesDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuotesResponseDTO {
    private List<QuotesDTO> quotes;
    private List<PackagesDTO> packages;
    private OriginDTO origin;
    private DestinationDTO destination;

}

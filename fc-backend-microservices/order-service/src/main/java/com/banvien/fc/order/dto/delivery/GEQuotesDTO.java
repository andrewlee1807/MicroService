package com.banvien.fc.order.dto.delivery;

import com.banvien.fc.order.dto.OutletDTO;
import com.banvien.fc.order.dto.ShoppingCartDTO;

import java.util.List;

public class GEQuotesDTO {
    private OutletDTO origin;
    private List<ShoppingCartDTO> packages;

    public OutletDTO getOrigin() {
        return origin;
    }

    public void setOrigin(OutletDTO origin) {
        this.origin = origin;
    }

    public List<ShoppingCartDTO> getPackages() {
        return packages;
    }

    public void setPackages(List<ShoppingCartDTO> packages) {
        this.packages = packages;
    }
}

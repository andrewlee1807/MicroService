package com.banvien.fc.order.dto.rules;

import java.util.List;

/**
 * Created by son.nguyen on 6/18/2020.
 */
public class RuleInputDTO {
    private List<RuleProductDTO> products;
    private boolean isByPass;

    public RuleInputDTO() {
        isByPass = false;
    }

    public RuleInputDTO(List<RuleProductDTO> products, boolean isByPass) {
        this.products = products;
        this.isByPass = isByPass;
    }

    public RuleInputDTO(List<RuleProductDTO> products, double totalPrice, int totalQuantity) {
        this.products = products;
        isByPass = false;
    }

    public List<RuleProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<RuleProductDTO> products) {
        this.products = products;
    }

    public boolean isByPass() {
        return isByPass;
    }

    public boolean getIsByPass() {
        return isByPass;
    }

    public void setByPass(boolean byPass) {
        isByPass = byPass;
    }
}

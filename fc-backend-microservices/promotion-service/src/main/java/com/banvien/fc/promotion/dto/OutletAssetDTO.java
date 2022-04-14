package com.banvien.fc.promotion.dto;

import java.io.Serializable;

public class OutletAssetDTO implements Serializable {

    private Long outletAssetId;
    private OutletDTO outlet;
    private String url;
    private String type;
    private Integer displayPos;

    public OutletAssetDTO() {
    }

    public Long getOutletAssetId() {
        return outletAssetId;
    }

    public OutletDTO getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletDTO outlet) {
        this.outlet = outlet;
    }

    public void setOutletAssetId(Long outletAssetId) {
        this.outletAssetId = outletAssetId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDisplayPos() {
        return displayPos;
    }

    public void setDisplayPos(Integer displayPos) {
        this.displayPos = displayPos;
    }
}

package com.banvien.fc.order.dto.mobile;

public class SubmitMobileDTO {
    private Long userId;
    private String device;
    private Long warehouseId;
    private Long salesManUserId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getSalesManUserId() {
        return salesManUserId;
    }

    public void setSalesManUserId(Long salesManUserId) {
        this.salesManUserId = salesManUserId;
    }
}

package com.banvien.fc.order.dto.mobile;

public class DeliveryValue4MobileDTO {
    private double originalShippingFee;
    private double shippingDiscount;
    private double shippingFee;

    public double getOriginalShippingFee() {
        return originalShippingFee;
    }

    public void setOriginalShippingFee(double originalShippingFee) {
        this.originalShippingFee = originalShippingFee;
    }

    public double getShippingDiscount() {
        return shippingDiscount;
    }

    public void setShippingDiscount(double shippingDiscount) {
        this.shippingDiscount = shippingDiscount;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }
}

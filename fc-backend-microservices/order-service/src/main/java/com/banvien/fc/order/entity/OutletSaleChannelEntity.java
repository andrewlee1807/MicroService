package com.banvien.fc.order.entity;

import javax.persistence.*;

@Table(name = "outletsalechannel")
@Entity
public class OutletSaleChannelEntity {
    private Long outletSaleChannelId;
    private Long outletId;
    private SaleChannelEntity saleChannel;
    private Integer status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getOutletSaleChannelId() {
        return outletSaleChannelId;
    }

    public void setOutletSaleChannelId(Long outletSaleChannelId) {
        this.outletSaleChannelId = outletSaleChannelId;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @ManyToOne()
    @JoinColumn(name = "salechannelid", referencedColumnName = "salechannelid")
    public SaleChannelEntity getSaleChannel() {
        return saleChannel;
    }

    public void setSaleChannel(SaleChannelEntity saleChannel) {
        this.saleChannel = saleChannel;
    }

}

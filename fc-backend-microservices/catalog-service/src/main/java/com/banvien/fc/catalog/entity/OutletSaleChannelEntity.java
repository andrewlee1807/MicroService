package com.banvien.fc.catalog.entity;

import javax.persistence.*;

@Table(name = "outletsalechannel")
@Entity
public class OutletSaleChannelEntity {
    private Long outletSaleChannelId;
    private OutletEntity outlet;
    private SaleChannelEntity saleChannel;
    private Integer status;

    @Id
    @Column(name="outletsalechannelid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getOutletSaleChannelId() {
        return outletSaleChannelId;
    }

    public void setOutletSaleChannelId(Long outletSaleChannelId) {
        this.outletSaleChannelId = outletSaleChannelId;
    }

    @ManyToOne()
    @JoinColumn(name = "outletid", referencedColumnName = "outletid")
    public OutletEntity getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletEntity outlet) {
        this.outlet = outlet;
    }

    @ManyToOne()
    @JoinColumn(name = "salechannelid", referencedColumnName = "salechannelid")
    public SaleChannelEntity getSaleChannel() {
        return saleChannel;
    }

    public void setSaleChannel(SaleChannelEntity saleChannel) {
        this.saleChannel = saleChannel;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

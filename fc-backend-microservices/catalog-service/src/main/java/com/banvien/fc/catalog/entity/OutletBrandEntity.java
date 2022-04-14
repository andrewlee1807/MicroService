package com.banvien.fc.catalog.entity;

import javax.persistence.*;

@Entity
@Table(name = "outlet_brand")
public class OutletBrandEntity {
    @Id
    @Column(name = "outletbrandid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long outletbrandid;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brandId", referencedColumnName = "brandId")
    private BrandEntity brand;
    private long outletid;

    public long getOutletbrandid() {
        return outletbrandid;
    }

    public void setOutletbrandid(long outletbrandid) {
        this.outletbrandid = outletbrandid;
    }

    public BrandEntity getBrand() {
        return brand;
    }

    public void setBrand(BrandEntity brand) {
        this.brand = brand;
    }

    public long getOutletid() {
        return outletid;
    }

    public void setOutletid(long outletid) {
        this.outletid = outletid;
    }
}

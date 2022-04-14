package com.banvien.fc.catalog.entity;

import javax.persistence.*;

@Table(name = "catgroup_outlet_salechannel")
@Entity
public class CatgroupOutletSaleChannelEntity {
    @Id
    @Column(name = "catgroupoutletsalechannelid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long catGroupOutletId;
    private Long saleChannelId;

    public CatgroupOutletSaleChannelEntity(Long catGroupOutletId, Long saleChannelId) {
        this.catGroupOutletId = catGroupOutletId;
        this.saleChannelId = saleChannelId;
    }

    public CatgroupOutletSaleChannelEntity() {
    }
}
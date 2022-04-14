package com.banvien.fc.catalog.entity;


import javax.persistence.*;

@Table(name = "productoutletsalechannel")
@Entity
public class ProductOutletSaleChannelEntity {

    private Long productOutletChannelID;
    private ProductOutletEntity productOutlet;
    private OutletSaleChannelEntity outletSaleChannel;

    @Id
    @Column(name="productoutletsalechannelid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getProductOutletChannelID() {
        return productOutletChannelID;
    }

    public void setProductOutletChannelID(Long productOutletChannelID) {
        this.productOutletChannelID = productOutletChannelID;
    }

    @ManyToOne()
    @JoinColumn(name = "productoutletid", referencedColumnName = "productoutletid")
    public ProductOutletEntity getProductOutlet() {
        return productOutlet;
    }

    public void setProductOutlet(ProductOutletEntity productOutlet) {
        this.productOutlet = productOutlet;
    }

    @ManyToOne()
    @JoinColumn(name = "outletsalechannelid", referencedColumnName = "outletsalechannelid")
    public OutletSaleChannelEntity getOutletSaleChannel() {
        return outletSaleChannel;
    }

    public void setOutletSaleChannel(OutletSaleChannelEntity outletSaleChannel) {
        this.outletSaleChannel = outletSaleChannel;
    }

    public ProductOutletSaleChannelEntity(ProductOutletEntity productOutlet, OutletSaleChannelEntity outletSaleChannel) {
        this.productOutlet = productOutlet;
        this.outletSaleChannel = outletSaleChannel;
    }

    public ProductOutletSaleChannelEntity() {
    }
}

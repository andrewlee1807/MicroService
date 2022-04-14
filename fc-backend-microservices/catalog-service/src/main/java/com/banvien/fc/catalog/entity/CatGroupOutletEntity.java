package com.banvien.fc.catalog.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Table(name = "catgroupoutlet")
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class CatGroupOutletEntity implements Serializable {

    private Long catGroupOutletId;
    private OutletEntity outlet;
    private CatGroupEntity catGroup;
    private String alias;
    private Long createdBy;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private Integer displayOrder;

    @Column(name = "catgroupoutletid")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getCatGroupOutletId() {
        return catGroupOutletId;
    }

    public void setCatGroupOutletId(Long catGroupOutletId) {
        this.catGroupOutletId = catGroupOutletId;
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
    @JoinColumn(name = "catGroupId", referencedColumnName = "catGroupId")
    public CatGroupEntity getCatGroup() {
        return catGroup;
    }

    public void setCatGroup(CatGroupEntity catGroup) {
        this.catGroup = catGroup;
    }

    @Basic
    @Column(name = "createdBy")
    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "createdDate")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "modifiedDate")
    @Basic
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Column(name = "displayOrder")
    @Basic
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }


}

package com.banvien.fc.order.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Table(name = "easyorder")
@Entity
public class EasyOrderEntity implements Serializable {

    private Long easyOrderId;
    private String name;
    private Integer totalViewer;
    private OutletEntity outletEntity;
    private Timestamp createdDate;
    private UserEntity createdBy;
    private Timestamp modifiedDate;
    private UserEntity modifiedBy;
    private String pcImage;
    private String mobileImage;
    private String easyOrderCode;
    private List<EasyOrderItemEntity> easyOrderItemEntities;

    public EasyOrderEntity() {
    }

    @Id
    @Column(name = "easyorderid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getEasyOrderId() {
        return easyOrderId;
    }

    public void setEasyOrderId(Long easyOrderId) {
        this.easyOrderId = easyOrderId;
    }

    @Basic
    @Column(name = "pcimage")
    public String getPcImage() {
        return pcImage;
    }

    public void setPcImage(String pcImage) {
        this.pcImage = pcImage;
    }

    @Basic
    @Column(name = "totalviewer")
    public Integer getTotalViewer() {
        return totalViewer;
    }

    public void setTotalViewer(Integer totalViewer) {
        this.totalViewer = totalViewer;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "createddate")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @ManyToOne
    @JoinColumn(name = "createdby", referencedColumnName = "userid")
    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "modifieddate")
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @ManyToOne
    @JoinColumn(name = "modifiedby", referencedColumnName = "userid")
    public UserEntity getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(UserEntity modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @ManyToOne
    @JoinColumn(name = "outletid", referencedColumnName = "outletid")
    public OutletEntity getOutletEntity() {
        return outletEntity;
    }

    public void setOutletEntity(OutletEntity outletEntity) {
        this.outletEntity = outletEntity;
    }

    @OneToMany(mappedBy = "easyOrderEntity", cascade = CascadeType.ALL)
    @OrderBy(value = "easyOrderItemId DESC")
    public List<EasyOrderItemEntity> getEasyOrderItemEntities() {
        return easyOrderItemEntities;
    }

    public void setEasyOrderItemEntities(List<EasyOrderItemEntity> easyOrderItemEntities) {
        this.easyOrderItemEntities = easyOrderItemEntities;
    }

    public String getMobileImage() {
        return mobileImage;
    }

    public void setMobileImage(String mobileImage) {
        this.mobileImage = mobileImage;
    }

    @Basic
    @Column(name = "easyordercode")
    public String getEasyOrderCode() {
        return easyOrderCode;
    }

    public void setEasyOrderCode(String easyOrderCode) {
        this.easyOrderCode = easyOrderCode;
    }
}

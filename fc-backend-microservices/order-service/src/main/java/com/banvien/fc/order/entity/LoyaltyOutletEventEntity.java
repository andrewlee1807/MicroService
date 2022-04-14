package com.banvien.fc.order.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "loyaltyoutletevent")
public class LoyaltyOutletEventEntity {

    @Id
    @Column(name = "loyaltyoutleteventid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loyaltyOutletEventId;

    @Column(name = "loyaltymastereventid")
    private Long loyaltyMasterEventId;

    @Column(name = "outletid")
    private Long outletId;

    @Basic
    @Column(name = "status")
    private String status;

    @Basic
    @Column(name = "point")
    private Integer point;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "maxPlay")
    private Integer maxPlay;

    @Basic
    @Column(name = "maxplayinorder")
    private Integer maxPlayInOrder;

    @Basic
    @Column(name = "targetType")
    private String targetType;

    @Basic
    @Column(name = "startDate")
    private Timestamp startDate;

    @Basic
    @Column(name = "endDate")
    private Timestamp endDate;

    @Basic
    @Column(name = "createdDate")
    private Timestamp createdDate;

    @JoinTable(
            name = "loyaltycustomertarget",
            joinColumns= @JoinColumn(name = "loyaltyoutleteventid"),
            inverseJoinColumns = @JoinColumn(name = "customergroupid"))
    @OneToMany
    private List<CustomerGroupEntity> customersTarget;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Long getLoyaltyOutletEventId() {
        return loyaltyOutletEventId;
    }

    public void setLoyaltyOutletEventId(Long loyaltyOutletEventId) {
        this.loyaltyOutletEventId = loyaltyOutletEventId;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public Long getLoyaltyMasterEventId() {
        return loyaltyMasterEventId;
    }

    public void setLoyaltyMasterEventId(Long loyaltyMasterEventId) {
        this.loyaltyMasterEventId = loyaltyMasterEventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxPlay() {
        return maxPlay;
    }

    public void setMaxPlay(Integer maxPlay) {
        this.maxPlay = maxPlay;
    }

    public Integer getMaxPlayInOrder() {
        return maxPlayInOrder;
    }

    public void setMaxPlayInOrder(Integer maxPlayInOrder) {
        this.maxPlayInOrder = maxPlayInOrder;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public void setCustomersTarget(List<CustomerGroupEntity> customersTarget) {
        this.customersTarget = customersTarget;
    }

    public List<CustomerGroupEntity> getCustomersTarget() {
        return customersTarget;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
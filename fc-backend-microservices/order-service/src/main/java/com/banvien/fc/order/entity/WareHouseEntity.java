package com.banvien.fc.order.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "warehouse")
public class WareHouseEntity {
    private Long wareHouseId;
    private OutletEntity outlet;
    private String code;
    private Timestamp createdDate;
    private UserEntity createdBy;
    private String name;
    private Integer status;
    private String description;
    private List<ProductOutletStorageEntity> productOutletStorages;
    private String address;
    private String type;
    private Boolean isDefault;

    @Id
    @Column(name = "warehouseid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(Long wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    @Basic
    @Column(name = "code", nullable = true, length = 255)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "createddate", nullable = true)
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }


    @ManyToOne()
    @JoinColumn(name = "createdBy", referencedColumnName = "userId")
    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne()
    @JoinColumn(name = "outletid", referencedColumnName = "outletid")
    public OutletEntity getOutlet() {
        return outlet;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wareHouse")
    public List<ProductOutletStorageEntity> getProductOutletStorages() {
        return productOutletStorages;
    }

    public void setProductOutletStorages(List<ProductOutletStorageEntity> productOutletStorages) {
        this.productOutletStorages = productOutletStorages;
    }

    public void setOutlet(OutletEntity outlet) {
        this.outlet = outlet;
    }

    @Basic
    @Column(name = "address", nullable = false)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "isdefault", nullable = false)
    public Boolean getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}

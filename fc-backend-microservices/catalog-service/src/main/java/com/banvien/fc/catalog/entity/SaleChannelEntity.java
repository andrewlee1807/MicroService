package com.banvien.fc.catalog.entity;


import javax.persistence.*;

@Table(name ="salechannel")
@Entity
public class SaleChannelEntity {
    private Long saleChannelId;
    private String code;
    private String name;

    @Id
    @Column(name = "SaleChannelID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getSaleChannelId() {
        return saleChannelId;
    }

    public void setSaleChannelId(Long saleChannelId) {
        this.saleChannelId = saleChannelId;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name ="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

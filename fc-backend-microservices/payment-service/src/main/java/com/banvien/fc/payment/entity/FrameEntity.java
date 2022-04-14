package com.banvien.fc.payment.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "frame")
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class FrameEntity implements Serializable {
    @Id
    @Column(name = "frameid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long frameid;

    @ManyToOne
    @JoinColumn(name = "outletId", referencedColumnName = "outletId")
    private OutletEntity outlet;

    @Basic
    @Column(name = "url")
    private String url;

    @Basic
    @Column(name = "name")
    private String name;

    public FrameEntity() {}

    public FrameEntity(OutletEntity outlet, String url, String name, Long frameid) {
        this.frameid = frameid;
        this.outlet = outlet;
        this.url = url;
        this.name = name;
    }

    public Long getFrameid() {
        return frameid;
    }

    public void setFrameid(Long frameid) {
        this.frameid = frameid;
    }

    public OutletEntity getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletEntity outlet) {
        this.outlet = outlet;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

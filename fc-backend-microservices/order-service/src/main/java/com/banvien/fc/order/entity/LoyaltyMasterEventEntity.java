package com.banvien.fc.order.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "loyaltymasterevent")
public class LoyaltyMasterEventEntity {

    @Id
    @Column(name = "loyaltymastereventid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loyaltyMasterEventId;

    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "image")
    private String image;

    @Basic
    @Column(name = "status")
    private String status;

    @Basic
    @Column(name = "eventtype")
    private String eventType;

    @Basic
    @Column(name = "createdby")
    private Long createdBy;

    @Basic
    @Column(name = "createddate")
    private Timestamp createdDate;

    public Long getLoyaltyMasterEventId() {
        return loyaltyMasterEventId;
    }

    public void setLoyaltyMasterEventId(Long loyaltyMasterEventId) {
        this.loyaltyMasterEventId = loyaltyMasterEventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}

package com.banvien.fc.order.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class EasyOrderDTO implements Serializable {

    private Long easyOrderId;
    private String name;
    private Integer totalViewer;
    private OutletDTO outletDTO;
    private Timestamp createdDate;
    private UserDTO createdBy;
    private Timestamp modifiedDate;
    private UserDTO modifiedBy;
    private String pcImage;
    private String mobileImage;
    private List<EasyOrderItemDTO> easyOrderItemDTOS;
    private long totalPage;
    private long totalElement;
    private String easyOrderCode;

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalElement() {
        return totalElement;
    }

    public void setTotalElement(long totalElement) {
        this.totalElement = totalElement;
    }

    public String getPcImage() {
        return pcImage;
    }

    public void setPcImage(String pcImage) {
        this.pcImage = pcImage;
    }

    public Long getEasyOrderId() {
        return easyOrderId;
    }

    public void setEasyOrderId(Long easyOrderId) {
        this.easyOrderId = easyOrderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalViewer() {
        return totalViewer;
    }

    public void setTotalViewer(Integer totalViewer) {
        this.totalViewer = totalViewer;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public UserDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDTO createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public UserDTO getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(UserDTO modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public List<EasyOrderItemDTO> getEasyOrderItemDTOS() {
        return easyOrderItemDTOS;
    }

    public void setEasyOrderItemDTOS(List<EasyOrderItemDTO> easyOrderItemDTOS) {
        this.easyOrderItemDTOS = easyOrderItemDTOS;
    }

    public OutletDTO getOutletDTO() {
        return outletDTO;
    }

    public void setOutletDTO(OutletDTO outletDTO) {
        this.outletDTO = outletDTO;
    }

    public String getMobileImage() {
        return mobileImage;
    }

    public void setMobileImage(String mobileImage) {
        this.mobileImage = mobileImage;
    }

    public String getEasyOrderCode() {
        return easyOrderCode;
    }

    public void setEasyOrderCode(String easyOrderCode) {
        this.easyOrderCode = easyOrderCode;
    }
}

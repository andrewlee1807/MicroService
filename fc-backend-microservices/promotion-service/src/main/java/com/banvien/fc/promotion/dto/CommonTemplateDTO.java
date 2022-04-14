package com.banvien.fc.promotion.dto;

import java.io.Serializable;

public class CommonTemplateDTO implements Serializable {

    private Long commonTemplateId;
    private String code;
    private String name;
    private String description;
    private String target;
    private Integer displayOrder;

    public Long getCommonTemplateId() {
        return commonTemplateId;
    }

    public void setCommonTemplateId(Long commonTemplateId) {
        this.commonTemplateId = commonTemplateId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}

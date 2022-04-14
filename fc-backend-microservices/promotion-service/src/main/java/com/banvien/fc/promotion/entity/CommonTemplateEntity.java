package com.banvien.fc.promotion.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "commontemplate")
public class CommonTemplateEntity implements Serializable {

    private Long commonTemplateId;
    private String code;
    private String name;
    private String description;
    private String target;
    private Integer displayOrder;

    @Id
    @Column(name = "commontemplateid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getCommonTemplateId() {
        return commonTemplateId;
    }

    public void setCommonTemplateId(Long commonTemplateId) {
        this.commonTemplateId = commonTemplateId;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "target")
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Basic
    @Column(name = "displayorder")
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}

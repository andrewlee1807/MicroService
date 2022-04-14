
package com.banvien.fc.order.dto;

import java.io.Serializable;
import java.util.List;

public class RoleDTO implements Serializable {
    private Long roleId;
    private String code;
    private String name;
    private String description;

    public RoleDTO() {
    }

    public RoleDTO(Long roleId, String code, String name, String description) {
        this.roleId = roleId;
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

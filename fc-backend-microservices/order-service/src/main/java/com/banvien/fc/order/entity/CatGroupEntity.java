package com.banvien.fc.order.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright by (c) Ban Vien Company Limited. All rights reserved.
 * User: Vincent
 * Date: 3/10/16
 * Time: 12:21 AM
 * Email: vien.nguyen@banvien.com
 */

@Table(name = "catgroup")
@Entity
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class CatGroupEntity implements Serializable {

    private Long catGroupId;
    private CatGroupEntity parent;
    private Set<ProductEntity> products;
    private Set<CatGroupEntity> childs;
    private String name;
    private String code;

    public CatGroupEntity() {
        this.products = new HashSet<>();
    }

    @Column(name = "catgroupid")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getCatGroupId() {
        return catGroupId;
    }

    public void setCatGroupId(Long catGroupId) {
        this.catGroupId = catGroupId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentid", referencedColumnName = "catgroupid")
    public CatGroupEntity getParent() {
        return parent;
    }

    public void setParent(CatGroupEntity parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "catGroup", fetch = FetchType.EAGER)
    public Set<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductEntity> products) {
        this.products = products;
    }

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    public Set<CatGroupEntity> getChilds() {
        return childs;
    }

    public void setChilds(Set<CatGroupEntity> childs) {
        this.childs = childs;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

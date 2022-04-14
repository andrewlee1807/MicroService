package com.banvien.fc.order.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "brand")
public class BrandEntity {

    private Long brandId;
    private Set<ProductEntity> products;
    private String name;

    public BrandEntity() {
        this.products = new HashSet<>();
    }

    @Id
    @Column(name = "brandid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    @OneToMany(mappedBy = "brand", fetch = FetchType.EAGER)
    public Set<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductEntity> products) {
        this.products = products;
    }

    @Basic
    @Column(name = "name", nullable = false, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

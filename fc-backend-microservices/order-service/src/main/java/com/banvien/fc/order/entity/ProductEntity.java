package com.banvien.fc.order.entity;

import javax.persistence.*;

@Table(name = "product")
@Entity
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class ProductEntity {

	private Long productId;
	private CatGroupEntity catGroup;
	private BrandEntity brand;
	private String name;
    private String thumbnail;

    public ProductEntity() {
    }

    @Column(name = "productid")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "catgroupid", referencedColumnName = "catgroupid")
	public CatGroupEntity getCatGroup() {
		return catGroup;
	}

	public void setCatGroup(CatGroupEntity catGroup) {
		this.catGroup = catGroup;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "brandid", referencedColumnName = "brandid")
	public BrandEntity getBrand() {
		return brand;
	}

	public void setBrand(BrandEntity brand) {
		this.brand = brand;
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
    @Column(name = "thumbnail", nullable = true, length = 255)
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


}

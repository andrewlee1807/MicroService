package com.banvien.fc.promotion.entity;

import javax.persistence.*;


@Table(name = "country")
@Entity
public class CountryEntity {
    private Long countryId;

    @Column(name = "countryid", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    private String code;

    @Column(name = "code", nullable = false)
    @Basic
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String name;

    @Column(name = "name", nullable = false)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String postCode;

    @Basic
    @Column(name = "postcode", nullable = false)
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    private String flag;

    @Basic
    @Column(name = "flag")
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    private Boolean status;
    @Basic
    @Column(name = "status")
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryEntity that = (CountryEntity) o;

        if (countryId != that.countryId) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = countryId != null? (int) (countryId ^ (countryId >>> 32)) : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public CountryEntity() {
    }

    public CountryEntity(Long countryId) {
        this.countryId = countryId;
    }

    public CountryEntity(Long countryId, String code, String name, String postCode, String flag, Boolean status) {
        this.countryId = countryId;
        this.code = code;
        this.name = name;
        this.postCode = postCode;
        this.flag = flag;
        this.status = status;
    }
}
package com.banvien.fc.promotion.dto;

public class PenetrationDTO {
    private Integer penetrationType;
    private String penetrationValue;
    private LapsedUserDTO lapsedUser;

    public Integer getPenetrationType() {
        return penetrationType;
    }

    public void setPenetrationType(Integer penetrationType) {
        this.penetrationType = penetrationType;
    }

    public String getPenetrationValue() {
        return penetrationValue;
    }

    public void setPenetrationValue(String penetrationValue) {
        this.penetrationValue = penetrationValue;
    }

    public LapsedUserDTO getLapsedUser() {
        return lapsedUser;
    }

    public void setLapsedUser(LapsedUserDTO lapsedUser) {
        this.lapsedUser = lapsedUser;
    }
}

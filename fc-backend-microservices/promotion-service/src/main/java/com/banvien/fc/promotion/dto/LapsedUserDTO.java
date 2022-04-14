package com.banvien.fc.promotion.dto;

public class LapsedUserDTO {
    private Long option;
    private String optionName;
    private String type;
    private Double value;
    private Long numberOfDay;


    public Long getOption() {
        return option;
    }

    public void setOption(Long option) {
        this.option = option;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Long getNumberOfDay() {
        return numberOfDay;
    }

    public void setNumberOfDay(Long numberOfDay) {
        this.numberOfDay = numberOfDay;
    }
}

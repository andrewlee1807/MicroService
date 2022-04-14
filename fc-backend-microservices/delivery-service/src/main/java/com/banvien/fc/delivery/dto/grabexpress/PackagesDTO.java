package com.banvien.fc.delivery.dto.grabexpress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PackagesDTO {

    private String name;
    private String description;
    private Integer quantity;
    private Double price;
    private DimensionsDTO dimensions;
}

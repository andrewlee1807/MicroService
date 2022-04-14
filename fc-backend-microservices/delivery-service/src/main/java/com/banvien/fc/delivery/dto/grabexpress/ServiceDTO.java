package com.banvien.fc.delivery.dto.grabexpress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDTO implements Serializable {
    private Integer id;
    private String type;
    private String name;
}

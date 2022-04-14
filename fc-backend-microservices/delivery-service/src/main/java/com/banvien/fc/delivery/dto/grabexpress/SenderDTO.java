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
public class SenderDTO implements Serializable {
    private String name;
    private String address;
    private String relationship;
    private String firstName;
    private String lastName;
    private String title;
    private String companyName;
    private String email;
    private String phone;
    private Boolean smsEnabled;
    private String instruction;

}

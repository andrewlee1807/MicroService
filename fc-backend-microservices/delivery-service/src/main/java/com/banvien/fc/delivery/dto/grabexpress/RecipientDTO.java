package com.banvien.fc.delivery.dto.grabexpress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecipientDTO {

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

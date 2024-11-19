package com.trimly.models.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {

    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String gender;
    private String profilePic;

    public UserDto() {
    }

    public UserDto(String fullName, String email, String password, String phone, String gender, String profilePic) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.profilePic = profilePic;
    }
}

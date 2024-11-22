package com.trimly.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {

    @NotBlank(message = "Name is required. Please enter your name.")
    private String fullName;
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Please enter a valid email address.")
    private String email;
    @NotBlank(message = "Password is required. Please enter a password.")
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    private String password;
    @NotBlank(message = "Phone number is required. Please enter a valid phone number.")
    private String phone;
    @NotBlank(message = "Please select your gender.")
    private String gender;
    @NotBlank(message = "You must agree to the terms and conditions to continue.")
    private String checkTerms;
    private String profilePic = "Default.png";

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

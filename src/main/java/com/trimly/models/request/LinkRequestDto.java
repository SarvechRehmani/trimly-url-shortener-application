package com.trimly.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LinkRequestDto {

    private String title;
    @NotBlank(message = "Please paste your link.")
    @Pattern(regexp = "\\b(?:(?:https?|ftp):\\/\\/)(?:\\S+(?::\\S*)?@)?(?:(?!10(?:\\.\\d{1,3}){3})(?!127(?:\\.\\d{1,3}){3})(?!169\\.254(?:\\.\\d{1,3}){2})(?!192\\.168(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\x{00a1}\\-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}\\-\\x{ffff}0-9]+)(?:\\.(?:[a-z\\x{00a1}\\-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}\\-\\x{ffff}0-9]+)*(?:\\.(?:[a-z\\x{00a1}\\-\\x{ffff}]{2,})))(?::\\d{2,5})?(?:\\/[^\\s]*)?\\b", message = "Please provide a valid URL.")
    private String longUrl;
    private String password;
    private String confirmPassword;
    private boolean isPasswordProtected;


    public LinkRequestDto(String longUrl) {
        this.longUrl = longUrl;
    }

    public LinkRequestDto(String title, String longUrl) {
        this.title = title;
        this.longUrl = longUrl;
    }

    public LinkRequestDto(String longUrl, String password, String confirmPassword, boolean isPasswordProtected) {
        this.longUrl = longUrl;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.isPasswordProtected = isPasswordProtected;
    }

    public LinkRequestDto(String title, String longUrl, String password, String confirmPassword, boolean isPasswordProtected) {
        this.title = title;
        this.longUrl = longUrl;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.isPasswordProtected = isPasswordProtected;
    }


}

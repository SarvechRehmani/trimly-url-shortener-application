package com.trimly.models.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LinkRequestDto {

    private String title;
    @NotBlank(message = "Please paste your link.")
    @Pattern(regexp = "^(https?|ftp)?://[^\\s/$.?#].[^\\s]*$\n", message = "Please provide a valid URL.")
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

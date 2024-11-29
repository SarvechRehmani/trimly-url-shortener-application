package com.trimly.models.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserResponseDto {

    private long id;
    private String name;
    private String email;
    private String phone;
    private String gender;
    private String profilePic;


}

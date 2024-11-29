package com.trimly.helper;

import com.trimly.exceptions.ResourceNotFoundException;
import com.trimly.models.entities.User;
import com.trimly.models.response.UserResponseDto;
import com.trimly.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticatedUserHelper {



    public static UserResponseDto getUserResponseDto(UsernamePasswordAuthenticationToken token) {
        Object principal = token.getPrincipal();
        if (principal instanceof User user) { // Replace `User` with your custom User class
            // Access user details
            System.out.println("User: " + user);
            // Other fields as needed
            return UserResponseDto.builder()
                    .id(user.getId())
                    .name(user.getFullName())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .gender(user.getGender())
                    .profilePic(user.getProfilePic())
                    .build();
        } else {
            System.out.println("Principal is not an instance of User.");
            return null;
        }

    }
}

package com.trimly.controllers;

import com.trimly.exceptions.ResourceFoundException;
import com.trimly.helper.Message;
import com.trimly.helper.MessageType;
import com.trimly.models.entities.User;
import com.trimly.models.request.UserDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import com.trimly.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UrlsController {


    private final UserService userService;

    private final ModelMapper modelMapper;

    public UrlsController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public String home() {
        return "home.html";
    }

    @GetMapping("/sign-in")
    public String login() {
        return "login.html";
    }

    @GetMapping("/sign-up")
    public String register(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "register.html";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute UserDto userDto, BindingResult result, Model model, HttpSession session) {
        // Check if user already exists

        userService.findUserByEmail(userDto.getEmail()).ifPresent(user -> session.setAttribute("message", new Message("Registration Successful", MessageType.green)));

        // If validation errors, return to the registration page
        if (result.hasErrors()) {
            return "register";
        }

        User user = convertToEntity(userDto);

        System.out.println("registration");
        userService.registerUser(user, "USER");
        return "redirect:/sign-in";
    }


//    @PostMapping("/login")
//    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        // Check if username and password match (you can replace this with your authentication logic)
//        User user = this.userService.authenticate(email, password);

//        if (user != null) {
//            model.addAttribute("user",user);
//            return "redirect:/"; // Redirect to the home page after successful login
//        } else {
//            model.addAttribute("error", "Invalid username or password"); // Add an error message
//            return "login"; // Return to the login page with an error message
//        }
//    }

    // Method to convert DTO to entity
    public User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}

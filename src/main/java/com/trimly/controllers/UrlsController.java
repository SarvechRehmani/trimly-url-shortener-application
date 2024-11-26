package com.trimly.controllers;

import com.trimly.helper.Message;
import com.trimly.helper.MessageType;
import com.trimly.models.entities.User;
import com.trimly.models.request.UserDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import com.trimly.services.imple.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UrlsController {


    private final UserServiceImpl userService;

    private final ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public UrlsController(UserServiceImpl userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

//    Home Controller
    @GetMapping("/")
    public String index() {
        return "home.html";
    }

    @GetMapping("/home")
    public String home() {
        return "redirect:/";
    }

//    Sign-in Controller
    @GetMapping("/sign-in")
    public String login(Model model) {
        model.addAttribute("currentPage", "sign-in");
        return "login.html";
    }


//    Sign-up Controller
    @GetMapping("/sign-up")
    public String register(Model model) {
        model.addAttribute("currentPage", "sign-up");
        model.addAttribute("userDto", new UserDto());
        return "register.html";
    }

//    Sign-up process Controller
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute UserDto userDto, BindingResult result, Model model, HttpSession session) {

        // Check if user already exists, return to the registration page
        if(userService.findUserByEmail(userDto.getEmail()).orElse(null) != null){
            logger.error("This email is already registered. Please use a different email.");
            session.setAttribute("message", new Message("This email is already registered. Please use a different email.", MessageType.red));
            return "redirect:/sign-up";
        }

        // validation errors, return to the registration page
        if (result.hasErrors()) {
            logger.error("Data is not set properly from sign-up form.");
            return "/register";
        }

//        Save User to the Database
        User user = convertToUser(userDto);
        userService.registerUser(user, "USER");
        logger.info("Registration Successful.");
        session.setAttribute("message", new Message("Registration Successful!", MessageType.green));
        return "redirect:/sign-in";
    }


    // Method to convert DTO to entity
    public User convertToUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}

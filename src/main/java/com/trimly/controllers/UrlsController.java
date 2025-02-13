package com.trimly.controllers;

import com.trimly.helper.AuthenticatedUserHelper;
import com.trimly.helper.Message;
import com.trimly.helper.MessageType;
import com.trimly.models.entities.Link;
import com.trimly.models.entities.User;
import com.trimly.models.request.LinkRequestDto;
import com.trimly.models.request.UserDto;
import com.trimly.models.response.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import com.trimly.services.imple.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    public String index(Model model, Authentication authentication) {
        // Check if the user is authenticated
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
        boolean authenticated = (authenticationToken != null && authenticationToken.isAuthenticated());
        if (authenticated){
            User user = AuthenticatedUserHelper.getAuthenticatedUser(authenticationToken);
            UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);
            model.addAttribute("user", userResponseDto);
        }
        // Pass the 'authenticated' variable to the view
        model.addAttribute("authenticated", authenticated);
        model.addAttribute("linkRequestDto", new LinkRequestDto());
        logger.info("home.html");
        return "home.html";
    }

    @GetMapping("/home")
    public String home() {
        return "redirect:/";
    }

//    Sign-in Controller
    @GetMapping("/sign-in")
    public String login(Model model, HttpServletRequest request) {
        // Check if user is authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            // If the user is authenticated, redirect to the home page
            logger.info("Redirecting from : login.html");
            return "redirect:/";
        }
        model.addAttribute("currentPage", "sign-in");
        logger.info("login.html");
        return "login.html";
    }


//    Sign-up Controller
    @GetMapping("/sign-up")
    public String register(Model model) {
        // Check if user is authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            // If the user is authenticated, redirect to the home page
            logger.info("Redirecting from : register.html");
            return "redirect:/";
        }
        model.addAttribute("currentPage", "sign-up");
        model.addAttribute("userDto", new UserDto());
        logger.info("register.html");
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
//      Save User to the Database
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

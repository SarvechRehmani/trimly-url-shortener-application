package com.trimly.controllers;

import com.trimly.entities.User;
import com.trimly.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UrlsController {


    private final UserService userService;

    public UrlsController(UserService userService) {
        this.userService = userService;
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
        model.addAttribute("user", new User());
        return "register.html";
    }

    @PostMapping("/registerUser")
    public String registerUser(User user, BindingResult result, Model model) {
        // Check if user already exists
        if (userService.findUserByEmail(user.getEmail()) != null) {
            result.rejectValue("username", "error.user", "Username already taken");
        }

        // If validation errors, return to the registration page
        if (result.hasErrors()) {
            return "redirect:/sign-up";
        }
        System.out.println("registeration");
        userService.registerUser(user, "USER");
        return "redirect:/sign-in";
    }


    @PostMapping("/login")
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        // Check if username and password match (you can replace this with your authentication logic)
        User user = this.userService.authenticate(email, password);

        if (user != null) {
            model.addAttribute("user",user);
            return "redirect:/"; // Redirect to the home page after successful login
        } else {
            model.addAttribute("error", "Invalid username or password"); // Add an error message
            return "login"; // Return to the login page with an error message
        }
    }
}

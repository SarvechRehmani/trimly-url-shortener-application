package com.trimly.controllers;

import com.trimly.helper.AuthenticatedUserHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {

    private final Logger logger = LoggerFactory.getLogger(RootController.class);

    @ModelAttribute
    public void addAuthenticatedUser(Model model, Authentication authentication) {
        this.logger.info("Getting authenticated user.");
//        Know if user is authenticated
        if(authentication == null) return;
        var user = AuthenticatedUserHelper.getAuthenticatedUser(authentication);
//        If user is not null then set password field null for security purpose.
        if(user != null) user.setPassword(null);
//        Set User in the session.
        model.addAttribute("user", user);
    }
}

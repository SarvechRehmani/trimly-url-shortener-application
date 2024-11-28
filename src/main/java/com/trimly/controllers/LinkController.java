package com.trimly.controllers;

import com.trimly.models.entities.Link;
import com.trimly.models.entities.User;
import com.trimly.models.request.LinkRequestDto;
import com.trimly.services.LinkService;
import com.trimly.services.imple.LinkServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LinkController {

    private final LinkService linkService;

    private final Logger logger = LoggerFactory.getLogger(LinkController.class);

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/shorten")
    public String saveLink(@Valid @ModelAttribute LinkRequestDto linkRequestDto, BindingResult result, HttpSession session, Authentication authentication){
        System.out.println(linkRequestDto);
        System.out.println(1);
        if(result.hasErrors()){
            System.out.println(result);
            return "home";
        }
        System.out.println(3);
//        User user = (User) ;
//        System.out.println(authentication.getPrincipal());
        System.out.println(4);
        return "redirect:/";
    }

}

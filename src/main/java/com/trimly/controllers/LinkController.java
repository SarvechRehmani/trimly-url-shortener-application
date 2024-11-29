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
        if(result.hasErrors()){
            System.out.println(result);
            return "home";
        }
        String title = linkRequestDto.getTitle();
        String longUrl = linkRequestDto.getLongUrl();
        String password = "";

        if(!linkRequestDto.getPassword().trim().isEmpty()){
            linkRequestDto.setPasswordProtected(true);
        }
        if(linkRequestDto.isPasswordProtected()){
            if(linkRequestDto.getPassword().trim().equals(linkRequestDto.getConfirmPassword().trim())){
                password = linkRequestDto.getPassword();
            }
        }
        User user = null;
        if(authentication != null && authentication.isAuthenticated()){
            user = (User) authentication.getPrincipal();
        }

        Link link = new Link(title,longUrl,password,linkRequestDto.isPasswordProtected(),user);
        this.linkService.saveLink(link);
        return "redirect:/";
    }

}

package com.trimly.controllers;

import com.trimly.models.entities.Link;
import com.trimly.models.request.LinkRequestDto;
import com.trimly.services.LinkService;
import com.trimly.services.imple.LinkServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LinkController {

    private final LinkService linkService;

    private final Logger logger = LoggerFactory.getLogger(LinkController.class);

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/shorten")
    public String saveLink(@Valid @ModelAttribute LinkRequestDto linkRequestDto, BindingResult result, HttpSession session){

        System.out.println(linkRequestDto);

        if(result.hasErrors()){
            return "home";
        }

        return "redirect:/";
    }

}

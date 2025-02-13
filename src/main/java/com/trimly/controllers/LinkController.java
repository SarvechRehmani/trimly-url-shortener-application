package com.trimly.controllers;

import com.trimly.helper.Message;
import com.trimly.helper.MessageType;
import com.trimly.models.entities.Link;
import com.trimly.models.entities.User;
import com.trimly.models.request.LinkRequestDto;
import com.trimly.models.response.LinkResponseDto;
import com.trimly.services.LinkService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
public class LinkController {

    private final LinkService linkService;

    private final Logger logger = LoggerFactory.getLogger(LinkController.class);

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

//    @PostMapping("/shorten")
//    public String saveLink(@Valid @ModelAttribute LinkRequestDto linkRequestDto, BindingResult result, HttpSession session, Authentication authentication){
//        this.logger.info("Check validations."+ linkRequestDto);
//        if(result.hasErrors()){
//            System.out.println(result);
//            return "home";
//        }
//        String title = linkRequestDto.getTitle();
//        String longUrl = linkRequestDto.getLongUrl();
//        String password = linkRequestDto.getPassword();
//
//        if (password != null && !password.trim().isEmpty()) {
//            linkRequestDto.setPasswordProtected(true);
//        }
//
//        if(linkRequestDto.isPasswordProtected()){
//            if(!linkRequestDto.getPassword().trim().equals(linkRequestDto.getConfirmPassword().trim())){
//                result.addError(new ObjectError("matchPassword", "Passwords do not match."));
//                if(result.hasErrors()){
//                    return "home";
//                }
//            }
//        }
//        User user = null;
//        if(authentication != null && authentication.isAuthenticated()){
//            user = (User) authentication.getPrincipal();
//        }
//        Link link = new Link(title,longUrl,password,linkRequestDto.isPasswordProtected(),user);
//        Link saveLink = this.linkService.saveLink(link);
//        session.setAttribute("message", new Message("Link has been successfully created!", MessageType.green));
//
//        return "redirect:/";
//    }


    @PostMapping("/shorten")
    public ResponseEntity<LinkResponseDto> saveLink(
            @Valid @ModelAttribute LinkRequestDto linkRequestDto,
            BindingResult result,
            Authentication authentication) {
        this.logger.info("Check validations: {}", linkRequestDto);
        Map<String, String> errors = new HashMap<>();
        // Check if there are validation errors
        if (result.hasErrors()) {
            this.logger.info("Long URL is not valid ot not present.");
            result.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(new LinkResponseDto("error", "Validation failed.", errors));
        }
        String title = linkRequestDto.getTitle();
        String longUrl = linkRequestDto.getLongUrl();
        String password = linkRequestDto.getPassword();

        this.logger.info("Protected by password or not.");
        if (password != null && !password.trim().isEmpty()) {
            linkRequestDto.setPasswordProtected(true);
        }

        // Password check
        this.logger.info("Check confirm password.");
        if (linkRequestDto.isPasswordProtected() && !linkRequestDto.getPassword().trim().equals(linkRequestDto.getConfirmPassword().trim())) {
            this.logger.info("Confirm password is not match.");
            errors.put("matchPassword","Passwords do not match.");
            return ResponseEntity.badRequest().body(new LinkResponseDto("error", "Validation failed.", errors));
        }

        // Authenticated user
        User user = null;
        if (authentication != null && authentication.isAuthenticated()) {
            user = (User) authentication.getPrincipal();
        }

        // Save the link
        this.logger.info("Saving link to the database.");
        Link link = new Link(title, longUrl, password, linkRequestDto.isPasswordProtected(), user);
//        Link savedLink = this.linkService.saveLink(link);
        link.setShortUrl("34343sadf");
        // Return success response with the shortened URL
        return ResponseEntity.ok(new LinkResponseDto("success", "Link has been successfully created!", link.getShortUrl()));
    }
}

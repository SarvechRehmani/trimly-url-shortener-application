package com.trimly.controllers;

import com.trimly.helper.AppConstants;
import com.trimly.helper.Message;
import com.trimly.helper.MessageType;
import com.trimly.models.entities.Link;
import com.trimly.models.entities.User;
import com.trimly.models.request.LinkRequestDto;
import com.trimly.models.response.LinkResponseDto;
import com.trimly.services.LinkService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Controller
public class LinkController {

    private final LinkService linkService;

    private final Logger logger = LoggerFactory.getLogger(LinkController.class);

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<LinkResponseDto> saveLink(
            @Valid @ModelAttribute LinkRequestDto linkRequestDto,
            BindingResult result,
            HttpServletRequest request,
            Authentication authentication) {
        this.logger.info("Check validations: {}", linkRequestDto);
        Map<String, String> errors = new HashMap<>();
        // Check if there are validation errors
        if (result.hasErrors()) {
            this.logger.info("Long URL is not valid or not present.");
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
        String userIp = AppConstants.getClientIp(request);
        // Save the link
        this.logger.info("Saving link to the database.");
        Link link = new Link(title, longUrl, password, linkRequestDto.isPasswordProtected(), user, userIp);
        Link savedLink = this.linkService.saveLink(link);
        // Return success response with the shortened URL
        return ResponseEntity.ok(new LinkResponseDto("success", "Link has been successfully created!", link.getShortUrl()));
    }

    @GetMapping("/{shortUrl}")
    public  String redirectLink(@PathVariable String shortUrl, HttpSession session, Model model){
        String shortUrlKey = AppConstants.BASE_URL+shortUrl;
        Link link = this.linkService.getLinkByShortUrl(shortUrlKey).orElse(null);

        if (link == null){
            this.logger.info("Page not Found.");
            return "404-error-page";
        }

        if(link.getUser() == null && LocalDate.now().isAfter(link.getExpirationDate().toLocalDate())){
            this.linkService.deleteLink(link.getId());
            this.logger.info("Link is expire and deleted from DB.");
            return "expire-link-page";
        }
        link.setCount(link.getCount()+1);
        link.setLastClickedAt(LocalDateTime.now());
        this.linkService.updateLink(link);

        if(link.getPassword()!=null && !link.getPassword().isEmpty()){
            model.addAttribute("link", link);
            return "protected-link";
        }

        String redirectUrl = link.getLongUrl().startsWith("https://") ? link.getLongUrl() : "https://"+link.getLongUrl();
        return "redirect:"+redirectUrl;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteLink(@PathVariable long id, HttpServletRequest request, Authentication authentication, HttpSession session){

        // Authenticated user
        User user = null;
        if (authentication != null && authentication.isAuthenticated()) {
            user = (User) authentication.getPrincipal();
        }
        String userIp = AppConstants.getClientIp(request);
        Link link = this.linkService.getLinkByIdAndUserOrUserIp(id,user,userIp).orElse(null);
        if(link == null){
            session.setAttribute("message", new Message("Link not found.", MessageType.red));
        }else{
            this.linkService.deleteLink(id);
            session.setAttribute("message", new Message("Link has been deleted.!", MessageType.green));
        }
        return "redirect:/";
    }

    @PostMapping("/link-password")
    public String linkProtected(@RequestParam("shortUrl") String shortUrl, @RequestParam("input-password") String inputPassword,  HttpSession session, Model model){
        Link link = this.linkService.getLinkByShortUrl(shortUrl).orElse(null);
        if (link!= null && link.getPassword().equals(inputPassword)){
            String redirectUrl = link.getLongUrl().startsWith("https://") ? link.getLongUrl() : "https://"+link.getLongUrl();
            return "redirect:"+redirectUrl;
        }else{
            model.addAttribute("link",link);
            session.setAttribute("message", new Message("Password is not match.!", MessageType.red));
            return "protected-link";
        }
    }
}

package com.trimly.helper.hadlers;

import com.trimly.helper.Message;
import com.trimly.helper.MessageType;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication != null) {
            // Set a success message in the session
            HttpSession session = request.getSession();
            session.setAttribute("message", new Message("Logout Successfully!", MessageType.green));
            logger.info("User {} logged out successfully.", authentication.getName());
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("message", new Message("You are not sign-in for logout!", MessageType.red));
            logger.info("Logout successful, no authenticated user.");
        }

        // Redirect to sign-in page
        response.sendRedirect("/sign-in");
    }
}

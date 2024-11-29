package com.trimly.config;

import com.trimly.helper.Message;
import com.trimly.helper.MessageType;
import com.trimly.services.imple.CustomUserDetailsService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/sign-in")
                        .loginProcessingUrl("/authenticate")
                        .successHandler((request, response, authentication) -> {
                            HttpSession session = request.getSession();
                            session.setAttribute("message", new Message("Welcome back to Trimly! You’ve successfully logged in.", MessageType.green));
                            if(authentication.getAuthorities().stream().anyMatch(grantedAuthority ->
                                grantedAuthority.getAuthority().equals("ADMIN")
                            )){
                                response.sendRedirect("/admin/dashboard");
                            }else{
                                response.sendRedirect("/");
                            }
                        })
                        .failureHandler((request, response, exception) -> {
                            HttpSession session = request.getSession();
                            session.setAttribute("message", new Message("Oops! Login failed. Please check your email and password, then try again.", MessageType.red));
                            response.sendRedirect("/sign-in");
                        })
                        .usernameParameter("email")
                        .passwordParameter("password")

                )
                .csrf(AbstractHttpConfigurer::disable)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            HttpSession session = request.getSession();
                            if (authentication != null) {
                                // Set a success message in the session
                                session.setAttribute("message", new Message("Logout Successfully!", MessageType.green));
                                logger.info("User {} logged out successfully.", authentication.getName());
                            } else {
                                session.setAttribute("message", new Message("You are not sign-in for logout!", MessageType.red));
                                logger.info("Logout successful, no authenticated user.");
                            }
                            response.sendRedirect("/sign-in");
                        })

                        .invalidateHttpSession(true)  // Invalidate the session after logout
                        .clearAuthentication(true)  // Clear authentication
                );
        return http.build();
    }


    //    Configuration of AuthenticationProvider
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        UserDetailsService Object
        authenticationProvider.setUserDetailsService(userDetailsService);
//        PasswordEncoder Object
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

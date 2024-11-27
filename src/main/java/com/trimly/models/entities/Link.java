package com.trimly.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String shortUrl;
    private String longUrl;
    private long count;
    private String password;
    private boolean isPasswordProtected;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastClickedAt;
    private LocalDateTime expirationDate;

    @ManyToOne
    @JsonIgnore
    private User user;


//    For non register users
    public Link(String shortUrl, String longUrl) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.count = 0;
        this.isPasswordProtected = false;
        this.createdAt = LocalDateTime.now();;
        this.updatedAt = LocalDateTime.now();;
        this.lastClickedAt = null;
        this.expirationDate = LocalDateTime.now().plusDays(1);
    }
//    For registered users

    public Link(String shortUrl, String longUrl, User user) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.count = 0;
        this.isPasswordProtected = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt =  LocalDateTime.now();
        this.lastClickedAt = null;
        this.expirationDate = null;
        this.user = user;
    }

    public Link(String title, String shortUrl, String longUrl, User user) {

        this.title = title;
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.count = 0;
        this.isPasswordProtected = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt =  LocalDateTime.now();;
        this.lastClickedAt = null;
        this.expirationDate = null;
        this.user = user;
    }

    public Link(String shortUrl, String longUrl, String password, boolean isPasswordProtected, User user) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.count = 0;
        this.password = password;
        this.isPasswordProtected = isPasswordProtected;
        this.createdAt =  LocalDateTime.now();;
        this.updatedAt =  LocalDateTime.now();;
        this.lastClickedAt = null;
        this.expirationDate = null;
        this.user = user;
    }

    public Link(long id, String title, String shortUrl, String longUrl, String password, boolean isPasswordProtected, User user) {
        this.title = title;
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.count = 0;
        this.password = password;
        this.isPasswordProtected = isPasswordProtected;
        this.createdAt =  LocalDateTime.now();;
        this.updatedAt =  LocalDateTime.now();;
        this.lastClickedAt = null;
        this.expirationDate = null;
        this.user = user;
    }
}


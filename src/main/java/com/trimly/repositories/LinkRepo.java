package com.trimly.repositories;

import com.trimly.models.entities.Link;
import com.trimly.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LinkRepo extends JpaRepository<Link, Long> {

    public Optional<Link> findByShortUrl(String shortUrl);
    List<Link> findByUser(User user);
    List<Link> findByUserIp(String userIp);

}

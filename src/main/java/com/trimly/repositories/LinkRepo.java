package com.trimly.repositories;

import com.trimly.models.entities.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepo extends JpaRepository<Link, Long> {

    public Optional<Link> findByShortUrl(String shortUrl);
}

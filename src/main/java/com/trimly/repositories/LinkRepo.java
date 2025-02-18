package com.trimly.repositories;

import com.trimly.models.entities.Link;
import com.trimly.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LinkRepo extends JpaRepository<Link, Long> {

    Optional<Link> findByShortUrl(String shortUrl);
    @Query("SELECT l FROM Link l WHERE l.id = :id AND (l.user = :user OR l.userIp = :userIp)")
    Optional<Link> findByIdAndUserOrUserIp(@Param("id") long id,@Param("user") User user,@Param("userIp") String userIp);
    List<Link> findByUser(User user);
    List<Link> findByUserIpAndUser(String userIp,User user);



}

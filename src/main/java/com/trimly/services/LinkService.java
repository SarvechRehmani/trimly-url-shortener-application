package com.trimly.services;

import com.trimly.models.entities.Link;
import com.trimly.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface LinkService {

    public Link saveLink(Link link);

     Optional<Link> getLinkById(long id);
     Optional<Link> getLinkByShortUrl(String shortUrl);
     Optional<Link> getLinkByIdAndUserOrUserIp(long id, User user,String userIp);
    List<Link> getAllLinksByUser(User user);
    List<Link> getAllLinksByUserIp(String userIp);

    public Link updateLink(Link link);

    public void deleteLink(long id);

}

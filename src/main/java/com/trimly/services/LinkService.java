package com.trimly.services;

import com.trimly.models.entities.Link;

import java.util.Optional;

public interface LinkService {

    public Link saveLink(Link link);

    public Optional<Link> getLinkById(long id);

    public Optional<Link> getLinkByShortUrl(String shortUrl);

    public Link updateLink(Link link);


    public void deleteLink(long id);

}

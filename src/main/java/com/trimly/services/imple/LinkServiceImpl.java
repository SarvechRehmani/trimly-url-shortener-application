package com.trimly.services.imple;

import com.trimly.exceptions.ResourceNotFoundException;
import com.trimly.models.entities.Link;
import com.trimly.repositories.LinkRepo;
import com.trimly.services.LinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LinkServiceImpl implements LinkService {


    private final LinkRepo linkRepo;

    private final Logger logger = LoggerFactory.getLogger(LinkServiceImpl.class);

    public LinkServiceImpl(LinkRepo linkRepo) {
        this.linkRepo = linkRepo;
    }

    @Override
    public Link saveLink(Link link) {
        this.logger.info("Saving Link into the database : {}", link);
        return this.linkRepo.save(link);
    }

    @Override
    public Optional<Link> getLinkById(long id) {
        this.logger.info("Finding Link by id : {}",id);
        return this.linkRepo.findById(id);
    }

    @Override
    public Optional<Link> getLinkByShortUrl(String shortUrl) {
        this.logger.info("Finding Link by short url : {}",shortUrl);
        return this.linkRepo.findByShortUrl(shortUrl);
    }

    @Override
    public Link updateLink(Link link) {
        this.logger.info("Updating Link : {}", link);
        return this.linkRepo.save(link);
    }

    @Override
    public void deleteLink(long id) {
        this.logger.info("Deleting Link by id : {}",id);
        this.linkRepo.deleteById(id);
    }
}

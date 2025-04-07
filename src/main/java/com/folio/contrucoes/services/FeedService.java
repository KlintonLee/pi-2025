package com.folio.contrucoes.services;

import com.folio.contrucoes.models.Feed;
import com.folio.contrucoes.models.Image;
import com.folio.contrucoes.repository.FeedRepository;
import com.folio.contrucoes.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedService {

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private ImageRepository imageRepository;

    public Integer create(Feed feed) {
        Feed savedFeed = feedRepository.save(feed);
        return savedFeed.getId();
    }

    public void updateImagem(Integer idFeed, String url) {
        this.feedRepository.findById(idFeed).ifPresent(feed -> {
            Image image = new Image();
            image.setFeed(feed);
            image.setUrl(url);
            this.imageRepository.save(image);
        });
    }
}

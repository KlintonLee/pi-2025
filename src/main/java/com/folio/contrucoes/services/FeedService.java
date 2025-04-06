package com.folio.contrucoes.services;

import com.folio.contrucoes.models.Feed;
import com.folio.contrucoes.repository.FeedRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedService {

    private FeedRepository feedRepository;

    public Integer create(Feed feed) {
        Feed savedFeed = feedRepository.save(feed);
        return savedFeed.getId();
    }
}

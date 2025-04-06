package com.folio.contrucoes.controllers;

import com.folio.contrucoes.dtos.CriarFeedDto;
import com.folio.contrucoes.models.Feed;
import com.folio.contrucoes.services.FeedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class FeedController {

    private FeedService feedService;

    @PostMapping("/v1/feeds")
    public void criarFeed(CriarFeedDto dto) {
        Feed feed = new Feed();
        feed.setTitle(dto.titulo);
        feed.setDescription(dto.descricao);
        Integer id = feedService.create(feed);

        ResponseEntity.created(URI.create("/v1/feeds/" + id)).build();
    }
}

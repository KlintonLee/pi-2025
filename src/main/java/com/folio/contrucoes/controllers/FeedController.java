package com.folio.contrucoes.controllers;

import com.folio.contrucoes.dtos.AdicionarImagemDto;
import com.folio.contrucoes.dtos.CriarAtualizarFeedDto;
import com.folio.contrucoes.dtos.FeedResponse;
import com.folio.contrucoes.services.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class FeedController implements FeedApi {

    @Autowired
    private FeedService feedService;

    @Override
    public ResponseEntity<List<FeedResponse>> listarFeeds() {
        return ResponseEntity.ok(this.feedService.list());
    }

    @Override
    public ResponseEntity<Object> criarFeed(@RequestBody CriarAtualizarFeedDto dto) {
        Integer id = feedService.create(dto);

        return ResponseEntity.created(URI.create("/v1/feeds/" + id)).build();
    }

    @Override
    public ResponseEntity<Object> atualizarFeed(@PathVariable Integer idFeed, @RequestBody CriarAtualizarFeedDto dto) {
        dto.id = idFeed;
        this.feedService.update(dto);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deletarFeed(@PathVariable Integer idFeed) {
        this.feedService.delete(idFeed);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Object> criarImagem(@RequestBody AdicionarImagemDto dto) {
        this.feedService.addImagem(dto.idFeed, dto.url);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deletarImagem(@PathVariable Integer idFeed) {
        this.feedService.deleteImagem(idFeed);
        return ResponseEntity.noContent().build();
    }
}

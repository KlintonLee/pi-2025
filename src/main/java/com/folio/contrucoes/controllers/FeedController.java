package com.folio.contrucoes.controllers;

import com.folio.contrucoes.dtos.AdicionarImagemDto;
import com.folio.contrucoes.dtos.CriarFeedDto;
import com.folio.contrucoes.dtos.FeedResponse;
import com.folio.contrucoes.models.Feed;
import com.folio.contrucoes.services.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class FeedController {

    @Autowired
    private FeedService feedService;

    @GetMapping("/v1/feeds")
    public ResponseEntity<List<FeedResponse>> listarFeeds() {
        return ResponseEntity.ok(this.feedService.list());
    }

    @PostMapping("/v1/feeds")
    public ResponseEntity<Object> criarFeed(@RequestBody CriarFeedDto dto) {
        Feed feed = new Feed();
        if (dto.titulo == null || dto.titulo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Título não pode ser nulo ou vazio");
        }
        feed.setTitle(dto.titulo);

        if (dto.descricao == null || dto.descricao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Descrição não pode ser nulo ou vazio");
        }
        feed.setDescription(dto.descricao);
        Integer id = feedService.create(feed);

        return ResponseEntity.created(URI.create("/v1/feeds/" + id)).build();
    }

    @PostMapping("/v1/feeds/imagens")
    public ResponseEntity<Object> criarImagem(@RequestBody AdicionarImagemDto dto) {
        if (dto.idFeed == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("O id do feed não pode ser nulo");
        }

        if (dto.url == null || dto.url.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("A url da imagem não pode ser nula ou vazia");
        }

        this.feedService.updateImagem(dto.idFeed, dto.url);

        return ResponseEntity.noContent().build();
    }
}

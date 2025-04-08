package com.folio.contrucoes.controllers;

import com.folio.contrucoes.dtos.AdicionarImagemDto;
import com.folio.contrucoes.dtos.CriarAtualizarFeedDto;
import com.folio.contrucoes.dtos.FeedResponse;
import com.folio.contrucoes.presenter.FeedPresenter;
import com.folio.contrucoes.services.AuthorizationService;
import com.folio.contrucoes.services.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class FeedController implements FeedApi {

    @Autowired
    private FeedService feedService;

    @Autowired
    private AuthorizationService authorizationService;

    @Override
    public ResponseEntity<List<FeedResponse>> listarFeeds(String token) {
        authorizationService.autenticar(token);
        return ResponseEntity.ok(this.feedService.list());
    }

    @Override
    public ResponseEntity<Object> criarFeed(String token, CriarAtualizarFeedDto dto) {
        authorizationService.autenticar(token);
        Integer id = feedService.create(dto);

        return ResponseEntity.created(URI.create("/v1/feeds/" + id)).body(FeedPresenter.present(id));
    }

    @Override
    public ResponseEntity<Object> atualizarFeed(String token, Integer idFeed, CriarAtualizarFeedDto dto) {
        authorizationService.autenticar(token);
        dto.id = idFeed;
        this.feedService.update(dto);

        return ResponseEntity.ok(FeedPresenter.present(idFeed));
    }

    @Override
    public ResponseEntity<Void> deletarFeed(String token, Integer idFeed) {
        authorizationService.autenticar(token);
        this.feedService.delete(idFeed);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Object> criarImagem(String token, AdicionarImagemDto dto) {
        authorizationService.autenticar(token);
        this.feedService.addImagem(dto.idFeed, dto.url);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deletarImagem(String token, Integer idFeed) {
        authorizationService.autenticar(token);
        this.feedService.deleteImagem(idFeed);
        return ResponseEntity.noContent().build();
    }
}

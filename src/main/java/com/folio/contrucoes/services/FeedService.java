package com.folio.contrucoes.services;

import com.folio.contrucoes.dtos.CriarAtualizarFeedDto;
import com.folio.contrucoes.dtos.FeedResponse;
import com.folio.contrucoes.dtos.ImageResponse;
import com.folio.contrucoes.exception.UnprocessableEntityException;
import com.folio.contrucoes.models.Feed;
import com.folio.contrucoes.models.Image;
import com.folio.contrucoes.repository.FeedRepository;
import com.folio.contrucoes.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedService {

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private ImageRepository imageRepository;

    public Integer create(CriarAtualizarFeedDto dto) {
        Feed feed = new Feed();
        if (dto.titulo == null || dto.titulo.isEmpty()) {
            throw new UnprocessableEntityException("Título não pode ser nulo ou vazio");
        }
        feed.setTitle(dto.titulo);

        if (dto.descricao == null || dto.descricao.isEmpty()) {
            throw new UnprocessableEntityException("Descrição não pode ser nulo ou vazio");
        }
        feed.setDescription(dto.descricao);
        Feed savedFeed = feedRepository.save(feed);
        return savedFeed.getId();
    }

    public List<FeedResponse> list() {
        return this.feedRepository.findAll().stream()
                .map(feed -> {
                    List<Image> images = this.imageRepository.findAllByFeedId(feed.getId());
                    return new FeedResponse(
                            feed.getId(),
                            feed.getTitle(),
                            feed.getDescription(),
                            images.stream()
                                    .map(img -> new ImageResponse(img.getId(), img.getUrl()))
                                    .toList()
                    );
                })
                .collect(Collectors.toList());
    }

    public void update(CriarAtualizarFeedDto feedDto) {
        if (feedDto.titulo == null || feedDto.titulo.isEmpty()) {
            throw new UnprocessableEntityException("Título não pode ser nulo ou vazio");
        }

        if (feedDto.descricao == null || feedDto.descricao.isEmpty()) {
            throw new UnprocessableEntityException("Descrição não pode ser nulo ou vazio");
        }

        this.feedRepository.findById(feedDto.id).ifPresent(feedOutput -> {
            feedOutput.setTitle(feedDto.titulo);
            feedOutput.setDescription(feedDto.descricao);
            this.feedRepository.save(feedOutput);
        });
    }

    public void delete(Integer feedId) {
        this.feedRepository.findById(feedId).ifPresent(feedOutput -> {
            this.imageRepository.deleteAllByFeedId(feedOutput.getId());
            this.feedRepository.delete(feedOutput);
        });
    }

    public void addImagem(Integer idFeed, String url) {
        if (idFeed == null) {
            throw new UnprocessableEntityException("O id do feed não pode ser nulo");
        }

        if (url == null || url.isEmpty()) {
            throw new UnprocessableEntityException("A url da imagem não pode ser nula ou vazia");
        }

        this.feedRepository.findById(idFeed).ifPresent(feed -> {
            Image image = new Image();
            image.setFeed(feed);
            image.setUrl(url);
            this.imageRepository.save(image);
        });
    }

    public void deleteImagem(Integer idImagem) {
        this.imageRepository.findById(idImagem).ifPresent(imageOutput -> {
            this.imageRepository.delete(imageOutput);
        });
    }
}

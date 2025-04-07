package com.folio.contrucoes.dtos;

import java.util.List;

public class FeedResponse {

    private Integer id;
    private String titulo;
    private String descricao;
    private List<ImageResponse> imagens;

    public FeedResponse(Integer id, String titulo, String descricao, List<ImageResponse> imagens) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.imagens = imagens;
    }

    public Integer getId() {
        return this.id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public List<ImageResponse> getImagens() {
        return this.imagens;
    }
}

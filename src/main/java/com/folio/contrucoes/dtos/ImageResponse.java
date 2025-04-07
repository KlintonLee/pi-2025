package com.folio.contrucoes.dtos;

public class ImageResponse {

    private Integer id;

    private String url;

    public ImageResponse(Integer id, String url) {
        this.id = id;
        this.url = url;
    }

    public Integer getId() {
        return this.id;
    }

    public String getUrl() {
        return this.url;
    }
}

package com.folio.contrucoes.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdicionarImagemDto {
    @JsonProperty("feed_id")
    public Integer idFeed;
    public String url;
}

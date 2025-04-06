package com.folio.contrucoes.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "imagens")
public class Image {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @JoinColumn(name = "feed_id", referencedColumnName = "id", nullable = false)
    private Feed feed;

    @Column(name = "url", nullable = false)
    private String url;

}

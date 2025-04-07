package com.folio.contrucoes.models;


import jakarta.persistence.*;

@Entity
@Table(name = "imagens")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "feed_id", referencedColumnName = "id", nullable = false)
    private Feed feed;

    @Column(name = "url", nullable = false)
    private String url;

    public Integer getId() {
        return this.id;
    }

    public Feed getFeed() {
        return this.feed;
    }

    public String getUrl() {
        return this.url;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

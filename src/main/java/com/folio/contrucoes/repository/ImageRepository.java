package com.folio.contrucoes.repository;

import com.folio.contrucoes.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query("SELECT i FROM Image i WHERE i.feed.id = ?1")
    List<Image> findAllByFeedId(Integer idFeed);
}

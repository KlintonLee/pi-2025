package com.folio.contrucoes.presenter;

import com.folio.contrucoes.dtos.FeedResponse;
import com.folio.contrucoes.dtos.ImageResponse;
import com.folio.contrucoes.models.Feed;
import com.folio.contrucoes.models.Image;

import java.util.List;

public interface FeedPresenter {

    static FeedResponse present(Feed feedOutput, List<Image> imagesOutput) {
        return new FeedResponse(
                feedOutput.getId(),
                feedOutput.getTitle(),
                feedOutput.getDescription(),
                imagesOutput.stream()
                        .map(img ->
                            new ImageResponse(img.getId(), img.getUrl()))
                        .toList()
        );
    }
}

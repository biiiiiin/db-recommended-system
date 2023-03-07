package com.the_ajou.web.dao.auctionReview;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuctionReviewDAO {
    private String review;
    private int score;
    private String createdAt;

    @Builder
    AuctionReviewDAO(String review, int score, String createdAt){
        this.review = review;
        this.score = score;
        this.createdAt = createdAt;
    }
}

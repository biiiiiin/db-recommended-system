package com.the_ajou.web.dao.productReview;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductReviewDAO {
    private int userId;
    private int productId;
    private int score;
    private String review;
    private String createdAt;

    @Builder
    ProductReviewDAO(int userId, int productId, int score, String review, String createdAt){
        this.userId = userId;
        this.productId = productId;
        this.score = score;
        this.review = review;
        this.createdAt = createdAt;
    }
}

package com.the_ajou.web.dto.productReview;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductReviewCreateDTO {
    private int productId;
    private int userId;
    private String review;
    private int score;

    @Builder
    ProductReviewCreateDTO(int productId, int userId, String review, int score){
        this.productId = productId;
        this.userId = userId;
        this.review = review;
        this.score = score;
    }
}

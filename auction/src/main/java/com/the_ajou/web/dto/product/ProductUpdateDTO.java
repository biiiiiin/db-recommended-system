package com.the_ajou.web.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductUpdateDTO {
    int productId;
    int categoryId;
    String title;
    String description;
    String startTime;
    int startPrice;
    int instant;
    int duration;
    int bidPrice;
    int buyNowPrice;
    private List<String> images;

    @Builder
    ProductUpdateDTO(int productId, int categoryId, String title, String description, String startTime,int buyNowPrice,
                     int startPrice, int duration, int bidPrice,int instant, List<String> images){
        this.productId = productId;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.buyNowPrice = buyNowPrice;
        this.startTime = startTime;
        this.duration = duration;
        this.startPrice = startPrice;
        this.bidPrice = bidPrice;
        this.instant = instant;
        this.images = images;
    }
}

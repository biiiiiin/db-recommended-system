package com.the_ajou.web.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductCreateDTO {
    int userId;
    int categoryId;
    String title;
    String description;
    String startTime;
    int startPrice;
    int duration;
    int bidPrice;
    int instant;
    int buyNowPrice;
    private List<String> productImages;

    @Builder
    ProductCreateDTO(int userId, int categoryId, String title, String description, String startTime, int duration,int bidPrice,
                     int startPrice, int instant, int buyNowPrice,List<String> productImages){
        this.userId = userId;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.duration = duration;
        this.startPrice = startPrice;
        this.bidPrice = bidPrice;
        this.instant = instant;
        this.buyNowPrice = buyNowPrice;
        this.productImages = productImages;
    }
}

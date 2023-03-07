package com.the_ajou.web.dao.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;



@Getter
@NoArgsConstructor
public class ProductResponseDAO {
    int productId;
    String seller;
    String title;
    String description;
    String startTime;
    String endTime;
    int startPrice;
    int instant;
    int buyNowPrice;
    int duration;
    int bidPrice;
    boolean like;
    boolean live;
    private List<String> productImages;

    @Builder
    ProductResponseDAO(int productId, String seller, String title, String description, String startTime, String endTime, int startPrice,int duration, int bidPrice,
                       int instant, int buyNowPrice,boolean like, boolean live, List<String> productImages){
        this.productId = productId;
        this.seller = seller;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPrice = startPrice;
        this.instant = instant;
        this.buyNowPrice = buyNowPrice;
        this.like = like;
        this.live = live;
        this.duration = duration;
        this.bidPrice = bidPrice;
        this.productImages = productImages;
    }
}

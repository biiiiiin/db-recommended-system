package com.the_ajou.web.dao.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductSearchResponseDAO {
    int productId;
    String title;
    int buyNowPrice;
    boolean instance;
    boolean live;
    boolean like;
    String image;

    @Builder
    ProductSearchResponseDAO(int productId, String title, int buyNowPrice, boolean instance,
                       boolean live, boolean like, String image){
        this.productId = productId;
        this.title = title;
        this.buyNowPrice = buyNowPrice;
        this.instance = instance;
        this.like = like;
        this.live = live;
        this.image = image;
    }
}
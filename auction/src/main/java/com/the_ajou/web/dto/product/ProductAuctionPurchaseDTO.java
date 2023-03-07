package com.the_ajou.web.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductAuctionPurchaseDTO {
    int productId;
    int buyerId;
    int endPrice;

    @Builder
    ProductAuctionPurchaseDTO(int productId, int buyerId, int endPrice){
        this.productId = productId;
        this.buyerId = buyerId;
        this.endPrice = endPrice;
    }
}

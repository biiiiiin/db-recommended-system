package com.the_ajou.web.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductInstantPurchaseDTO {
    int productId;
    int buyerId;

    @Builder
    ProductInstantPurchaseDTO(int productId, int buyerId){
        this.productId = productId;
        this.buyerId = buyerId;
    }
}

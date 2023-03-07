package com.the_ajou.web.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductEndPriceUpdateDTO {
    int productId;
    int endPrice;

    @Builder
    ProductEndPriceUpdateDTO(int productId, int endPrice){
        this.productId = productId;
        this.endPrice = endPrice;
    }
}

package com.the_ajou.web.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductInfoDTO {
    int productId;
    int userId;

    @Builder
    ProductInfoDTO(int productId, int userId){
        this.productId = productId;
        this.userId = userId;
    }
}

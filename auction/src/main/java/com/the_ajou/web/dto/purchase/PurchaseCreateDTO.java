package com.the_ajou.web.dto.purchase;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PurchaseCreateDTO {
    int productId;
    int buyerId;
    int price;
    String purchaseAddress;

    @Builder
    PurchaseCreateDTO(int productId, int buyerId, int price, String purchaseAddress){
        this.productId = productId;
        this.buyerId = buyerId;
        this.price = price;
        this.purchaseAddress = purchaseAddress;
    }
}

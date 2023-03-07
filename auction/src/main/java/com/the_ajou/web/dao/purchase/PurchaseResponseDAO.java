package com.the_ajou.web.dao.purchase;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PurchaseResponseDAO {
    int productId;
    int buyerId;
    int price;
    String purchaseAddress;
    String createAt;
    String updateAt;
    char status;

    @Builder
    PurchaseResponseDAO(int productId, int buyerId, int price, String purchaseAddress, String createAt, String updateAt, char status){
        this.productId = productId;
        this.buyerId = buyerId;
        this.price = price;
        this.purchaseAddress = purchaseAddress;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.status = status;
    }
}

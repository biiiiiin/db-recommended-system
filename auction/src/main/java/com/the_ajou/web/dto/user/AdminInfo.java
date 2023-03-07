package com.the_ajou.web.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminInfo {
    private String title;
    private String image;
    private int price;
    private int commission;
    private String seller;
    private String buyer;


    @Builder
    AdminInfo(String title, String image, int price, int commission, String seller, String buyer){
        this.title = title;
        this.image = image;
        this.commission = commission;
        this.price = price;
        this.seller = seller;
        this.buyer = buyer;
    }
}

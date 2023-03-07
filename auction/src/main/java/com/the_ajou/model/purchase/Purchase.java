package com.the_ajou.model.purchase;

import com.the_ajou.model.product.Product;
import com.the_ajou.model.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "buyerId")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Column(name = "price")
    private int price;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "updatedAt")
    private String updatedAt;


    @Builder
    Purchase(User buyer, Product product, int price, String createdAt, String updatedAt){
        this.buyer = buyer;
        this.product = product;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

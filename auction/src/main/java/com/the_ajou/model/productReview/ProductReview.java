package com.the_ajou.model.productReview;

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
@Entity(name = "productReview")
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Column(name = "review")
    private String review;

    @Column(name = "score")
    private int score;

    @Column(name = "createdAt")
    private String createdAt;

    @Builder
    ProductReview(User user, Product product, String review, int score, String createdAt){
        this.user = user;
        this.product = product;
        this.review = review;
        this.score = score;
        this.createdAt = createdAt;
    }
}

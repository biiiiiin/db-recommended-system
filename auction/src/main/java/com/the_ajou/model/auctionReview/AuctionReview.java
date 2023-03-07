package com.the_ajou.model.auctionReview;

import com.the_ajou.model.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "auctionReview")
public class AuctionReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "review")
    private String review;

    @Column(name = "score")
    private int score;

    @Column(name = "createdAt")
    private String createdAt;

    @Builder
    AuctionReview(User user, String review, int score, String createdAt){
        this.user = user;
        this.review = review;
        this.score = score;
        this.createdAt = createdAt;
    }
}

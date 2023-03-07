package com.the_ajou.auction;

import com.the_ajou.model.auctionReview.AuctionReview;
import com.the_ajou.model.auctionReview.AuctionReviewRepository;
import com.the_ajou.model.user.User;
import com.the_ajou.model.user.UserRepository;
import com.the_ajou.web.dao.auctionReview.AuctionReviewDAO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@WebAppConfiguration
class AuctionReviewServiceTest {
    @Autowired
    private AuctionReviewRepository auctionReviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Rollback
    @Test
    void createAuctionReviewTest(){
        User user = userRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));

        AuctionReview auctionReview = AuctionReview.builder()
                .user(user)
                .review("멋있어요.")
                .score(5)
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();

        auctionReviewRepository.save(auctionReview);

        Assertions.assertThat(auctionReview).isNotNull();
    }

    @Transactional
    @Rollback
    @Test
    void getReview(){
        List<AuctionReview> reviews = auctionReviewRepository.findAll();
        List<AuctionReviewDAO> reviewDAOS = new ArrayList<>();

        for(AuctionReview auctionReview : reviews) {
            AuctionReviewDAO auctionReviewDAO = AuctionReviewDAO.builder()
                    .review(auctionReview.getReview())
                    .score(auctionReview.getScore())
                    .createdAt(auctionReview.getCreatedAt())
                    .build();

            reviewDAOS.add(auctionReviewDAO);
        }

        Assertions.assertThat(reviewDAOS).isNotEmpty();
    }
}

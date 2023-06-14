package com.the_ajou.auction;

import com.the_ajou.model.product.Product;
import com.the_ajou.model.product.ProductRepository;
import com.the_ajou.model.productReview.ProductReview;
import com.the_ajou.model.productReview.ProductReviewRepository;
import com.the_ajou.model.user.User;
import com.the_ajou.model.user.UserRepository;
import com.the_ajou.web.dao.productReview.ProductReviewDAO;
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
class ProductReviewServiceTest {
    @Autowired
    private ProductReviewRepository productReviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    @Rollback
    @Test
    void createProductReview(){
        User user = userRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Product product = productRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다."));

        ProductReview productReview = ProductReview.builder()
                .user(user)
                .product(product)
                .review("Test Review")
                .score(5)
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();

        productReviewRepository.save(productReview);

        Assertions.assertThat(productReview).isNotNull();
    }

    @Transactional
    @Rollback
    @Test
    void getReviewByUserId(){
        User user = userRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));

        List<ProductReview> productReviews = productReviewRepository.findByUser(user);
        List<ProductReviewDAO> productReviewDAOS = new ArrayList<>();

        for(ProductReview productReview : productReviews){
            ProductReviewDAO review = ProductReviewDAO.builder()
                    .userId(productReview.getUser().getId())
                    .productId(productReview.getProduct().getId())
                    .review(productReview.getReview())
                    .score(productReview.getScore())
                    .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                    .build();

            productReviewDAOS.add(review);
        }

        //Assertions.assertThat(productReviewDAOS).isNotEmpty();
    }
}

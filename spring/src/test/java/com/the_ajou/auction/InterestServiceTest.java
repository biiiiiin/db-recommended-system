package com.the_ajou.auction;

import com.the_ajou.model.interests.Interest;
import com.the_ajou.model.interests.InterestRepository;
import com.the_ajou.model.product.Product;
import com.the_ajou.model.product.ProductRepository;
import com.the_ajou.model.user.User;
import com.the_ajou.model.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@WebAppConfiguration
class InterestServiceTest {
    @Autowired
    private InterestRepository interestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    @Rollback
    @Test
    void createInterest(){
        User user = userRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Product product = productRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다."));

        Interest interest = Interest.builder()
                .user(user)
                .product(product)
                .build();
        interestRepository.save(interest);

        Assertions.assertThat(interest).isNotNull();
    }

    @Transactional
    @Rollback
    @Test
    void deleteInterest(){
        List<Interest> interest = interestRepository.findAll();

        interestRepository.delete(interest.get(0));

        Assertions.assertThat(interest).isNotNull();
    }
}

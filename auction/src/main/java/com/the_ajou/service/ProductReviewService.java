package com.the_ajou.service;

import com.the_ajou.model.product.Product;
import com.the_ajou.model.product.ProductRepository;
import com.the_ajou.model.productReview.ProductReview;
import com.the_ajou.model.productReview.ProductReviewRepository;
import com.the_ajou.model.user.User;
import com.the_ajou.model.user.UserRepository;
import com.the_ajou.web.dao.productReview.ProductReviewDAO;
import com.the_ajou.web.dto.productReview.ProductReviewCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductReviewService {
    private final ProductReviewRepository productReviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public boolean createProductReview(ProductReviewCreateDTO productReviewCreateDTO){
        User user = userRepository.findById(productReviewCreateDTO.getUserId())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Product product = productRepository.findById(productReviewCreateDTO.getProductId())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다."));

        ProductReview productReview = ProductReview.builder()
                .user(user)
                .product(product)
                .review(productReviewCreateDTO.getReview())
                .score(productReviewCreateDTO.getScore())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();

        productReviewRepository.save(productReview);

        return productReview.getId() != -1;
    }

    @Transactional
    public List<ProductReviewDAO> getReviewByUserId(int userId){
        User user = userRepository.findById(userId)
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

        Collections.reverse(productReviewDAOS);

        return productReviewDAOS;
    }
}

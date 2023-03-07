package com.the_ajou.web.controller;

import com.the_ajou.service.ProductReviewService;
import com.the_ajou.web.dao.productReview.ProductReviewDAO;
import com.the_ajou.web.dto.productReview.ProductReviewCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductReviewController {
    private final ProductReviewService productReviewService;

    @PostMapping("/api/review/product")
    private boolean createReview(@RequestBody ProductReviewCreateDTO productReviewCreateDTO){
        return productReviewService.createProductReview(productReviewCreateDTO);
    }

    @GetMapping("/api/review/product/{userId}")
    private List<ProductReviewDAO> getProductReviewByUserId(@PathVariable("userId") int userId){
        return productReviewService.getReviewByUserId(userId);
    }

}

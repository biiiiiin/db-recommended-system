package com.the_ajou.web.controller;

import com.the_ajou.service.AuctionReviewService;
import com.the_ajou.web.dao.auctionReview.AuctionReviewDAO;
import com.the_ajou.web.dto.auctionReview.AuctionReviewCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuctionReviewController {
    private final AuctionReviewService auctionReviewService;

    @PostMapping("/api/review/auction")
    private boolean createReview(@RequestBody AuctionReviewCreateDTO auctionReviewCreateDTO){
        return auctionReviewService.createAuctionReview(auctionReviewCreateDTO);
    }

    @GetMapping("/api/review/auction/{userId}")
    private List<AuctionReviewDAO> getReviews(@PathVariable("userId")int userId){
        return auctionReviewService.getReviewByUserId(userId);
    }

}

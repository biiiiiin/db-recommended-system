package com.the_ajou.service;

import com.the_ajou.model.auctionReview.AuctionReview;
import com.the_ajou.model.auctionReview.AuctionReviewRepository;
import com.the_ajou.model.user.User;
import com.the_ajou.model.user.UserRepository;
import com.the_ajou.web.dao.auctionReview.AuctionReviewDAO;
import com.the_ajou.web.dto.auctionReview.AuctionReviewCreateDTO;
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
public class AuctionReviewService {
    private final AuctionReviewRepository auctionReviewRepository;
    private final UserRepository userRepository;

    @Transactional
    public boolean createAuctionReview(AuctionReviewCreateDTO auctionReviewCreateDTO){
        User user = userRepository.findById(auctionReviewCreateDTO.getUserId())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));

        AuctionReview auctionReview = AuctionReview.builder()
                .user(user)
                .review(auctionReviewCreateDTO.getReview())
                .score(auctionReviewCreateDTO.getScore())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();

        auctionReviewRepository.save(auctionReview);

        return auctionReview.getId() != -1;
    }

    @Transactional
    public List<AuctionReviewDAO> getReviewByUserId(int userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));
        List<AuctionReview> reviews = auctionReviewRepository.findByUser(user);
        List<AuctionReviewDAO> reviewDAOS = new ArrayList<>();

        for(AuctionReview auctionReview : reviews){
            AuctionReviewDAO auctionReviewDAO = AuctionReviewDAO.builder()
                    .review(auctionReview.getReview())
                    .score(auctionReview.getScore())
                    .createdAt(auctionReview.getCreatedAt())
                    .build();

            reviewDAOS.add(auctionReviewDAO);
        }


        Collections.reverse(reviewDAOS);

        return reviewDAOS;
    }
}

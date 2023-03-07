package com.the_ajou.model.productReview;

import com.the_ajou.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {
    List<ProductReview> findByUser(User user);
}

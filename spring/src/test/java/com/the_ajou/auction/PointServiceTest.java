package com.the_ajou.auction;

import com.the_ajou.model.point.Point;
import com.the_ajou.model.point.PointRepository;
import com.the_ajou.model.user.User;
import com.the_ajou.model.user.UserRepository;
import com.the_ajou.web.dao.point.PointResponseDAO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
@WebAppConfiguration
class PointServiceTest {
    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private UserRepository userRepository;


    @Transactional
    @Rollback
    @Test
    void findPointHistoryByUserId(){
        List<Point> points = pointRepository.findAll();
        List<PointResponseDAO> pointResponseDAOS = new LinkedList<>();

        for(Point point : points){
            PointResponseDAO pointResponseDAO = PointResponseDAO.builder()
                    .createdAt(point.getCreatedAt())
                    .point(point.getPoint())
                    .build();
            pointResponseDAOS.add(pointResponseDAO);
        }
        Assertions.assertThat(pointResponseDAOS).isNotEmpty();
    }

//    @Transactional
//    @Rollback
//    @Test
//    void findHistoryById(){
//        Point point = pointRepository.findById(1)
//                .orElseThrow(()->new IllegalArgumentException("존재하지 않은 포인트 내역입니다."));
//
//        PointResponseDAO pointResponseDAO = PointResponseDAO.builder()
//                .id(point.getId())
//                .userId(point.getUser().getId())
//                .createdAt(point.getCreatedAt())
//                .point(point.getPoint())
//                .build();
//
//        Assertions.assertThat(pointResponseDAO).isNotNull();
//    }

    @Transactional
    @Rollback
    @Test
    void chargePoint(){
        User user = userRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));

        int userPoint = user.getPoint();

        user.updatePoint(1000);
        user.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        Point point = Point.builder()
                .user(user)
                .point(1000)
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();

        pointRepository.save(point);

        Assertions.assertThat(point).isNotNull();
    }

    @Transactional
    @Rollback
    @Test
    void getPointByUserId(){
        User user = userRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Assertions.assertThat(user.getPoint()).isNotZero();
    }
}

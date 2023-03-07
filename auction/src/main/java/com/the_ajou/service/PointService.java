package com.the_ajou.service;

import com.the_ajou.model.point.Point;
import com.the_ajou.model.point.PointRepository;
import com.the_ajou.model.user.User;
import com.the_ajou.model.user.UserRepository;
import com.the_ajou.web.dao.point.PointResponseDAO;
import com.the_ajou.web.dto.point.PointCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PointService {
    private final PointRepository pointRepository;
    private final UserRepository userRepository;


    @Transactional
    public List<PointResponseDAO> findPointHistoryByUserId(int userId){
        List<Point> points = pointRepository.findListByUserId(userId);
        List<PointResponseDAO> pointResponseDAOS = new LinkedList<>();

        for(Point point : points){
            PointResponseDAO pointResponseDAO = PointResponseDAO.builder()
                    .createdAt(point.getCreatedAt())
                    .point(point.getPoint())
                    .build();
            pointResponseDAOS.add(pointResponseDAO);
        }

        Collections.reverse(pointResponseDAOS);

        return pointResponseDAOS;
    }

//    @Transactional
//    public PointResponseDAO findHistoryById(int pointLogId){
//        Point point = pointRepository.findById(pointLogId)
//                .orElseThrow(()->new IllegalArgumentException("존재하지 않은 포인트 내역입니다."));
//
//        return PointResponseDAO.builder()
//                .id(point.getId())
//                .userId(point.getUser().getId())
//                .createdAt(point.getCreatedAt())
//                .point(point.getPoint())
//                .build();
//    }

    @Transactional
    public boolean chargePoint(PointCreateDTO pointCreateDTO){
        User user = userRepository.findById(pointCreateDTO.getUserId())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));

        user.updatePoint(pointCreateDTO.getPoint());
        user.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        Point point = Point.builder()
                .user(user)
                .point(pointCreateDTO.getPoint())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();

        pointRepository.save(point);

        return true;
    }

    @Transactional
    public int getPointByUserId(int userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));

        return user.getPoint();
    }
}

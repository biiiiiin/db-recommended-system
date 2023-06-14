package com.the_ajou.web.controller;

import com.the_ajou.service.PointService;
import com.the_ajou.web.dao.point.PointResponseDAO;
import com.the_ajou.web.dto.point.PointCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @GetMapping("/api/point/history/{userId}")
    List<PointResponseDAO> findPointHistoryByUserId(@PathVariable("userId") int userId){
        return pointService.findPointHistoryByUserId(userId);
    }

//    @GetMapping("/api/point/history/{id}")
//    PointResponseDAO getPointHistory(@PathVariable("id") int id){
//        return pointService.findHistoryById(id);
//    }

    @PostMapping("/api/point/charge")
    boolean updatePoint(@RequestBody PointCreateDTO pointCreateDTO){
        return pointService.chargePoint(pointCreateDTO);
    }

    @GetMapping("/api/point/{userId}")
    int getPoint(@PathVariable("userId") int userId){
        return pointService.getPointByUserId(userId);
    }
}

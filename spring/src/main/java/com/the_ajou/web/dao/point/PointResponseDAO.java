package com.the_ajou.web.dao.point;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PointResponseDAO {
    String createdAt;
    int point;

    @Builder
    PointResponseDAO(String createdAt, int point){
        this.createdAt = createdAt;
        this.point = point;
    }
}

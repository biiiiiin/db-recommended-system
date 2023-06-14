package com.the_ajou.web.dto.point;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PointCreateDTO {
    int userId;
    int point;

    @Builder
    PointCreateDTO(int userId, int point){
        this.userId = userId;
        this.point = point;
    }
}

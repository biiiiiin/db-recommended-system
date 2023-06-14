package com.the_ajou.web.dao.user;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserLoginDAO {
    int userId;
    String nickName;

    @Builder
    UserLoginDAO(int userId, String nickName){
        this.userId = userId;
        this.nickName = nickName;
    }
}

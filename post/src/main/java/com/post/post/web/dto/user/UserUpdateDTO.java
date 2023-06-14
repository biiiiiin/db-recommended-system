package com.the_ajou.web.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserUpdateDTO {
    private int userId;
    private String phoneNum;
    private String address;
    private String nickName;
    private char gender;
    private String birth;
    private String name;

    @Builder
    UserUpdateDTO(String phoneNum, String address, String nickName, char gender, String birth, String name){
        this.phoneNum = phoneNum;
        this.address = address;
        this.nickName = nickName;
        this.gender = gender;
        this.birth = birth;
        this.name = name;
    }
}

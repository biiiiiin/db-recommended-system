package com.the_ajou.web.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserCreateDTO {
    private String email;
    private String password;
    private String phoneNum;
    private String address;
    private String nickName;
    private char gender;
    private String birth;
    private String name;


    @Builder
    public UserCreateDTO(String email, String password, String phoneNum, String address, String nickName, char gender, String birth, String name){
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
        this.address = address;
        this.nickName = nickName;
        this.gender = gender;
        this.birth = birth;
        this.name = name;
    }
}

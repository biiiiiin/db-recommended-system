package com.the_ajou.model.user;

import com.the_ajou.web.dto.user.UserUpdateDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phoneNum")
    private String phoneNum;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private char status;

    @Column(name = "nickName")
    private String nickName;

    @Column(name = "point")
    private int point;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "updatedAt")
    private String updatedAt;


    @Builder
    public User(String email, String password, String phoneNum, String address, String createdAt, String updatedAt, char status, String nickName, int point){
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.nickName = nickName;
        this.point = point;
    }

    public void updateUser(UserUpdateDTO userUpdateDTO){

        if(!Objects.equals(userUpdateDTO.getPhoneNum(), ""))
            this.phoneNum = userUpdateDTO.getPhoneNum();
        if(!Objects.equals(userUpdateDTO.getAddress(), ""))
            this.address = userUpdateDTO.getAddress();
        if(!Objects.equals(userUpdateDTO.getNickName(), ""))
            this.nickName = userUpdateDTO.getNickName();

        this.updatedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}

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

    @Column(name = "gender")
    private char gender;

    @Column(name = "birth")
    private String birth;

    @Column(name = "name")
    private String name;


    @Builder
    public User(String email, String password, String phoneNum, String address, String createdAt, String updatedAt, char status, String nickName, int point, char gender, String birth, String name){
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.nickName = nickName;
        this.point = point;
        this.gender = gender;
        this.birth = birth;
        this.name = name;
    }

    public void updatePoint(int point){
        this.point = this.point + point;
    }

    public void updateUser(UserUpdateDTO userUpdateDTO){

        if(!Objects.equals(userUpdateDTO.getPhoneNum(), ""))
            this.phoneNum = userUpdateDTO.getPhoneNum();
        if(!Objects.equals(userUpdateDTO.getName(), ""))
            this.name = userUpdateDTO.getName();
        if(!Objects.equals(userUpdateDTO.getAddress(), ""))
            this.address = userUpdateDTO.getAddress();
        if(!Objects.equals(userUpdateDTO.getNickName(), ""))
            this.nickName = userUpdateDTO.getNickName();
        if(userUpdateDTO.getGender() == 'M' || userUpdateDTO.getGender() == 'F')
            this.gender = userUpdateDTO.getGender();
        if(!Objects.equals(userUpdateDTO.getBirth(), ""))
            this.birth = userUpdateDTO.getBirth();

        this.updatedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}

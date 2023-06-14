package com.the_ajou.web.controller;

import com.the_ajou.model.user.User;
import com.the_ajou.service.UserService;
import com.the_ajou.web.dao.user.UserLoginDAO;
import com.the_ajou.web.dto.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/api/auth/signup")
    public int signupUser(@RequestBody UserCreateDTO userCreateDTO){
        return userService.signUp(userCreateDTO);
    }

    @GetMapping("/api/user/{id}")
    public User findUserById(@PathVariable("id") int id) {
        return userService.findUserById(id);
    }


    @PatchMapping("/api/user/update")
    public boolean updateUser(@RequestBody UserUpdateDTO userUpdateDTO){
        return userService.updateUser(userUpdateDTO);
    }

    @PatchMapping("/api/user/delete/{id}")
    public int deleteUser(@PathVariable("id") int id){
        return userService.deleteUser(id);
    }


    @GetMapping("/api/user/getId/{nickName}")
    public int getUserId(@PathVariable("nickName") String nickName){
        return userService.getUserId(nickName);
    }

    @GetMapping("/api/user/save/time")
    public long saveUserTime(@PathVariable("num") int num){
        return userService.getSaveTime(num);
    }

    @GetMapping("/api/user/find/time")
    public long getUserById(@PathVariable("num") int num){
        return userService.findByIds(num);
    }
}

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

    @PostMapping("/api/auth/login")
    public Object login(@RequestBody UserLoginDTO userLoginDTO){
        return userService.login(userLoginDTO);
    }

    @PostMapping("/api/auth/pw")
    public boolean changePassword(@RequestBody UserLoginDTO userLoginDTO){
        return userService.changePassword(userLoginDTO);
    }

    @PatchMapping("/api/user/update")
    public boolean updateUser(@RequestBody UserUpdateDTO userUpdateDTO){
        return userService.updateUser(userUpdateDTO);
    }

    @PatchMapping("/api/user/delete/{id}")
    public int deleteUser(@PathVariable("id") int id){
        return userService.deleteUser(id);
    }

    @PostMapping("/api/auth/findEmail")
    public String findEmail(@RequestBody UserFindDTO userFindDTO){
        return userService.findEmail(userFindDTO);
    }

    @PatchMapping("/api/user/registerAddress")
    public boolean updateAddress(@RequestBody UserAddressUpdateDTO userAddressUpdateDTO){
        return userService.updateAddress(userAddressUpdateDTO);
    }

    @GetMapping("/api/user/getId/{nickName}")
    public int getUserId(@PathVariable("nickName") String nickName){
        return userService.getUserId(nickName);
    }
}

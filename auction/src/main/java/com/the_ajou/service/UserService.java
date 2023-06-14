package com.the_ajou.service;
import com.the_ajou.model.user.User;
import com.the_ajou.model.user.UserRepository;
import com.the_ajou.web.dao.user.UserLoginDAO;
import com.the_ajou.web.dto.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public int signUp(UserCreateDTO userCreateDTO){
        User checkUser = userRepository.findByEmail(userCreateDTO.getEmail());
        User nickNameCheck = userRepository.findBynickName(userCreateDTO.getNickName());

        if(checkUser == null && nickNameCheck == null){
            User user = User.builder()
                    .email(userCreateDTO.getEmail())
                    .password(passwordEncoder.encode(userCreateDTO.getPassword()))
                    .phoneNum(userCreateDTO.getPhoneNum())
                    .address(userCreateDTO.getAddress())
                    .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                    .updatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                    .status('N')
                    .nickName(userCreateDTO.getNickName())
                    .point(0)
                    .build();

            userRepository.save(user);
            return user.getId();
        }else if(nickNameCheck != null){
            return -2;
        }
        else{
            return -1;
        }
    }

    @Transactional
    public User findUserById(int id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @Transactional
    public boolean updateUser(UserUpdateDTO userUpdateDTO){
        User user = userRepository.findById(userUpdateDTO.getUserId())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));
        User nickCheck = userRepository.findBynickName(userUpdateDTO.getNickName());

        if(nickCheck != null)
            return false;
        user.updateUser(userUpdateDTO);

        return true;
    }

    @Transactional
    public int deleteUser(int id){
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));
            user.setStatus('Y');
            return user.getId();
        }catch (Exception ignored){

        }
        return 0;
    }

    @Transactional
    public int getUserId(String nickName){
        User user = userRepository.findBynickName(nickName);
        if(user != null)
            return user.getId();
        else
            return 0;
    }


    @Transactional
    public long getSaveTime(int num){
        long startTime = System.currentTimeMillis();

        for(int i=0;i<num;i++){
            User user = User.builder()
                    .email("biiin" + i + "@ajou.ac.kr")
                    .password(passwordEncoder.encode("biiin" + i))
                    .phoneNum("010" + i + i + i + i + "0000")
                    .address("아주대학교 팔달관" + i + "방")
                    .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                    .updatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                    .status('N')
                    .nickName("BIN" + i)
                    .point(0)
                    .build();

            userRepository.save(user);
        }

        long endTime = System.currentTimeMillis();

        long timeDiff = (endTime - startTime) / 1000;

        return timeDiff;
    }

    @Transactional
    public long findByIds(int num){
        long startTime = System.currentTimeMillis();

        for(int i=0;i<num;i++){
            User user = userRepository.findById(i)
                    .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));
        }

        long endTime = System.currentTimeMillis();

        long timeDiff = (endTime - startTime) / 1000;

        return timeDiff;
    }
}

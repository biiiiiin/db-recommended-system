package com.the_ajou.auction;

import com.the_ajou.model.user.User;
import com.the_ajou.model.user.UserRepository;
import com.the_ajou.web.dao.user.UserLoginDAO;
import com.the_ajou.web.dto.user.UserUpdateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@WebAppConfiguration
class UserServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Rollback
    @Test
    void signUp(){
        User user = User.builder()
                .email("Test Email")
                .password("Test Password")
                .phoneNum("010-1234-5678")
                .address("Test Address")
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .updatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .status('N')
                .nickName("Test Nickname")
                .point(0)
                .gender('M')
                .birth("2022-11-11")
                .name("Test Name")
                .build();

        userRepository.save(user);

        assertThat(user).isNotNull();
    }

    @Transactional
    @Rollback
    @Test
    void findUserById(){
        User user = userRepository.findById(1)
                .orElseThrow(() -> new IllegalArgumentException("없는 사용자 입니다"));

        assertThat(user).isNotNull();
    }

    @Transactional
    void login(){
        User user = userRepository.findByEmail("admin");
        UserLoginDAO userLoginDAO;

        if(user.getPassword().equals("qwer1234!")){
            userLoginDAO = UserLoginDAO.builder()
                    .userId(user.getId())
                    .nickName(user.getNickName())
                    .build();
        }else{
            userLoginDAO = UserLoginDAO.builder()
                    .userId(0)
                    .nickName("")
                    .build();
        }

        assertThat(userLoginDAO.getUserId()).isEqualTo(user.getId());
        assertThat(userLoginDAO.getNickName()).isEqualTo(user.getNickName());
    }

    @Transactional
    @Rollback
    @Test
    void changePassword(){
        User user = userRepository.findById(1)
                        .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));

        user.setPassword("Test Password");

        assertThat(user.getPassword()).isEqualTo("Test Password");
    }

    @Transactional
    @Rollback
    @Test
    void updateUser(){
        User user = userRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));

        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
                .address("Test Address")
                .nickName("Test Nickname")
                .gender('F')
                .birth("2022-11-11")
                .name("Test Name")
                .build();

        user.updateUser(userUpdateDTO);

        assertThat(user.getAddress()).isEqualTo("Test Address");
    }

    @Transactional
    @Rollback
    @Test
    void deleteUser(){
        User user = userRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));
        user.setStatus('Y');

        assertThat(user.getStatus()).isEqualTo('Y');
    }

    @Transactional
    @Rollback
    @Test
    void findEmail(){
        User user = userRepository.findByEmail("test1@test1");

        assertThat(user).isNotNull();
    }

    @Transactional
    @Rollback
    @Test
    void updateAddress(){
        User user = userRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));

        user.setAddress("Test Address");

        assertThat(user.getAddress()).isEqualTo("Test Address");
    }

    @Transactional
    @Rollback
    @Test
    void getUserId(){
        User user = userRepository.findBynickName("김영진");

        assertThat(user).isNotNull();
    }
}

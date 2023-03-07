package com.the_ajou.web.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginDTO {
    private String email;
    private String password;

    @Builder
    UserLoginDTO(String email, String password){
        this.email = email;
        this.password = password;
    }
}

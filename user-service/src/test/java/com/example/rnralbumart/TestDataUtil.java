package com.example.rnralbumart;

import com.example.rnralbumart.dto.UserResponseDTO;

public class TestDataUtil {

    public static UserResponseDTO getUserResponseDTO(Integer userId){
        return UserResponseDTO.builder()
                .userId(userId)
                .userName("testUserName")
                .firstName("testFirstName")
                .lastName("testLastName")
                .password("testPassword")
                .build();
    }

}

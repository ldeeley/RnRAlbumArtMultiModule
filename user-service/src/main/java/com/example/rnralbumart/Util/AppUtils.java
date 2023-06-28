package com.example.rnralbumart.Util;

import com.example.rnralbumart.dto.UserRequestDTO;
import com.example.rnralbumart.dto.UserResponseDTO;
import com.example.rnralbumart.model.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AppUtils {

    public static UserEntity mapDTOToEntity(UserRequestDTO userRequestDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userRequestDTO.getUserName());
        userEntity.setPassword(userRequestDTO.getPassword());
        userEntity.setFirstName(userRequestDTO.getFirstName());
        userEntity.setLastName(userRequestDTO.getLastName());
        return userEntity;
    }

    public static UserResponseDTO mapEntityToDTO(UserEntity userEntity){
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUserId(userEntity.getUserId());
        userResponseDTO.setUserName(userEntity.getUserName());
        userResponseDTO.setPassword(userEntity.getPassword());
        userResponseDTO.setFirstName(userEntity.getFirstName());
        userResponseDTO.setLastName(userEntity.getLastName());
        return userResponseDTO;
    }

    public static String convertObjectToJSON(Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

}

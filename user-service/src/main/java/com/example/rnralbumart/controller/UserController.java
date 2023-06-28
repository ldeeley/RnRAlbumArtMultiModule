package com.example.rnralbumart.controller;

import com.example.rnralbumart.Util.AppUtils;
import com.example.rnralbumart.dto.UserRequestDTO;
import com.example.rnralbumart.dto.UserResponseDTO;
import com.example.rnralbumart.dto.UserServiceResponse;
import com.example.rnralbumart.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rnr/user")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping
    public UserServiceResponse<UserResponseDTO> addUser(@RequestBody UserRequestDTO userRequestDTO) {
        log.info("UserController::addUser method execution start : Request payload : {}", AppUtils.convertObjectToJSON(userRequestDTO));
        UserServiceResponse<UserResponseDTO> userServiceResponse = new UserServiceResponse<>();
        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);
        userServiceResponse.setStatus(HttpStatus.CREATED);
        userServiceResponse.setResponse(userResponseDTO);
        log.info("UserController::addUser method execution end ServiceResponse payload : {}", AppUtils.convertObjectToJSON(userServiceResponse));
        return userServiceResponse;
    }

    @GetMapping("/{userId}")
    public UserServiceResponse<UserResponseDTO> getUser(@PathVariable Integer userId){
        UserServiceResponse<UserResponseDTO> userServiceResponse = new UserServiceResponse<>();
        UserResponseDTO userResponseDTO = userService.getUser(userId);
        userServiceResponse.setStatus(HttpStatus.OK);
        userServiceResponse.setResponse(userResponseDTO);
        return userServiceResponse;
    }

    @GetMapping
    public UserServiceResponse<List<UserResponseDTO>> getAllUsers(){
        UserServiceResponse<List<UserResponseDTO>> userServiceResponse = new UserServiceResponse<>();
        List<UserResponseDTO> userServiceAllUsers = userService.getAllUsers();
        userServiceResponse.setStatus(HttpStatus.OK);
        userServiceResponse.setResponse(userServiceAllUsers);
        return userServiceResponse;
    }

    @DeleteMapping("/{userId}")
    public UserServiceResponse<String> deleteUser(@PathVariable Integer userId){
        UserServiceResponse<String> userServiceResponse = new UserServiceResponse<>();
        userServiceResponse.setStatus(HttpStatus.NO_CONTENT);
        userServiceResponse.setResponse(userService.deleteUser(userId));
        return userServiceResponse;
    }

    @PutMapping("/{userId}")
    public UserServiceResponse<UserResponseDTO> updateUser(@PathVariable Integer userId, @RequestBody UserRequestDTO userRequestDTO){
        UserServiceResponse<UserResponseDTO> userServiceResponse = new UserServiceResponse<>();
        // get existing user details
        UserRequestDTO updatedUser = new UserRequestDTO();
        //update them
        updatedUser.setFirstName(userRequestDTO.getFirstName());
        updatedUser.setLastName(userRequestDTO.getLastName());
        updatedUser.setUserName(userRequestDTO.getUserName());
        updatedUser.setPassword(userRequestDTO.getPassword());
        UserResponseDTO updatedUserResponseDTO = userService.updateUser(updatedUser, userId);
        userServiceResponse.setStatus(HttpStatus.OK);
        userServiceResponse.setResponse(updatedUserResponseDTO);
        return userServiceResponse;
    }

}

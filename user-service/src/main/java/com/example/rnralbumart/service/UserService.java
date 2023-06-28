package com.example.rnralbumart.service;

import com.example.rnralbumart.Util.AppUtils;
import com.example.rnralbumart.dto.UserRequestDTO;
import com.example.rnralbumart.dto.UserResponseDTO;
import com.example.rnralbumart.exception.UserServiceException;
import com.example.rnralbumart.model.UserEntity;
import com.example.rnralbumart.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){
        UserEntity userEntity = AppUtils.mapDTOToEntity(userRequestDTO);
        UserEntity newUserEntity = null;
        log.info("UserService::createUser method execution started");
        try {
            newUserEntity = userRepository.save(userEntity);
            log.debug("Saving user {} in UserService::createUser",AppUtils.convertObjectToJSON(newUserEntity));
            log.info("UserService::createUser method execution ended");
            return AppUtils.mapEntityToDTO(newUserEntity);
        } catch (Exception exception){
            log.error("UserService::createUser Exception occurs persisting User to database {}",AppUtils.convertObjectToJSON(userEntity));
            throw new UserServiceException(" failed to save " + userRequestDTO + "to UserRepository");
        }
    }

    public UserResponseDTO getUser(Integer userId) {
        log.info("UserService::getUser method execution started");
        UserEntity newUserEntity = null;
        newUserEntity = userRepository.findById(userId).orElseThrow(() ->
            new UserServiceException("No User with UserId = " + userId + " found"));
        log.debug("Found user {} in UserService::getUser",AppUtils.convertObjectToJSON(newUserEntity));
        log.info("UserService::getUser method execution ended");
        return AppUtils.mapEntityToDTO(newUserEntity);
    }

    public List<UserResponseDTO> getAllUsers(){
        log.info("UserService::getAllUsers method execution started");
        try {
            log.info("UserService::getAllUsers method execution ended");
            return StreamSupport.
                    stream(userRepository.findAll().spliterator(),false)
                    .map(AppUtils::mapEntityToDTO)
                    .collect(Collectors.toList());

        } catch (Exception exception){
            log.error("UserService::getAllUser Exception occurs retrieving User {}",exception.getMessage());
            throw new UserServiceException(" failed to retrieve ALL Users from UserRepository");
        }
    }

    public String deleteUser(Integer userId) {
        log.info("UserService::deleteUser method execution started");
        try{
            log.debug("UserService::deleteUser {}",userId);
            userRepository.deleteById(userId);
            log.info("UserService::deleteUser method execution ended");
            return "user "+userId+" successfully deleted";
        } catch (Exception exception){
            log.error("UserService::createUser Exception occurs deleting User {}",exception.getMessage());
            throw new UserServiceException(" failed to delete User "+userId+"  from UserRepository");
        }
    }

    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO, Integer userId) {
            log.info("UserService::updateUser method execution started");
            UserEntity updatedUser = AppUtils.mapDTOToEntity(userRequestDTO);
            updatedUser.setUserId(userId);
            try {
                log.info("UserService::updateUser method execution ended");
                return AppUtils.mapEntityToDTO(userRepository.save(updatedUser));
            } catch (Exception exception){
                log.error("UserService::updateUser Exception occurs updating User {}",exception.getMessage());
                throw new UserServiceException(" unable to update User "+userId+"  to UserRepository");
            }
    }
}

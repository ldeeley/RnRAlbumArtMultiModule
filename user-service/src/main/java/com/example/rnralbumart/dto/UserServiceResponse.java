package com.example.rnralbumart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceResponse<T> {

    private HttpStatus status;
    private T response;
    private List<ErrorDTO> errorDTOList;

    public UserServiceResponse(HttpStatus status, T response){
        this.status = status;
        this.response = response;
    }

}

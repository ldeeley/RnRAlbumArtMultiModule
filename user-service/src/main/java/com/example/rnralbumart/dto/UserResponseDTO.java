package com.example.rnralbumart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private int userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;

}

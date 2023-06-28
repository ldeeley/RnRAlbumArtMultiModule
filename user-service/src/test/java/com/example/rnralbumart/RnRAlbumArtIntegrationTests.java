package com.example.rnralbumart;

import com.example.rnralbumart.dto.UserRequestDTO;
import com.example.rnralbumart.dto.UserResponseDTO;
import com.example.rnralbumart.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.rnralbumart.TestDataUtil.getUserResponseDTO;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest(properties = {
        "spring.test.database.replace=NONE",
        "spring.datasource.url=jdbc:tc:mysql:latest://rnralbumart/",
        "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver",
        "spring.datasource.username=root",
        "spring.datasource.password=password"
})
@AutoConfigureMockMvc
public class RnRAlbumArtIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateNewUser() throws Exception {
        UserRequestDTO userRequestDTO = getUserRequest();
        String userRequestDTOString = objectMapper.writeValueAsString(userRequestDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/rnr/user")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(userRequestDTOString))
                .andExpect(jsonPath("$.status").value("CREATED"));
    }

    @Test
    void shouldGetUser() throws Exception {

        Mockito.when(userService.getUser(1)).thenReturn(getUserResponseDTO(1));

        mockMvc.perform(MockMvcRequestBuilders.get("/rnr/user/1"))
                .andExpect(jsonPath("$.status").value("OK"));
    }

    private UserRequestDTO getUserRequest(){
        return UserRequestDTO.builder()
                .userName("testUsername")
                .password("testPassword")
                .firstName("testFirstName")
                .lastName("testLastName")
                .build();
    }



}

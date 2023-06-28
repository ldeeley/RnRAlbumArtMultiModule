package com.example.rnralbumart;

import com.example.rnralbumart.controller.UserController;
import com.example.rnralbumart.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
class RnRAlbumArtUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    void shouldReturnUser() throws Exception {

        when(userService.getUser(1))
                .thenReturn(TestDataUtil.getUserResponseDTO(1));
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/rnr/user/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.userName").value("testUserName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.firstName").value("testFirstName"));
    }

}

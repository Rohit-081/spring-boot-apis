package com.example.learning.microservices.microservices.controllers;


import com.example.learning.microservices.microservices.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllUser() throws Exception{
        mockMvc.perform(get("http://localhost:9090/api/v1/user/getAllUsers"))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.*", hasSize(1)));
    }


    public void createUser() throws Exception{
        User user = new User();
        user.setUserId("UBIN002");
        user.setUsername("Rohit");
        user.setEmailId("Bri@brillio.com");
        user.setMobileNumber("9755588849");
        mockMvc.perform(post("http://localhost:9090/api/v1/user/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                        .andDo(print())
                        .andExpect(status().isOk());

    }

    @Test
    public void updateUser() throws Exception{
        User user = new User();
        user.setUsername("Rohit");
        mockMvc.perform(put("http://localhost:9090/api/v1/user/updateUser/{id}", "UBIN002")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void deleteUser() throws Exception{
        mockMvc.perform(delete("http://localhost:9090/api/v1/user/deleteUser/{id}", "UBIN002"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

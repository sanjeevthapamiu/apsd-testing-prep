package edu.miu.apsd.controllertestingusermanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.apsd.controllertestingusermanagementsystem.dto.request.UserRequestDTO;
import edu.miu.apsd.controllertestingusermanagementsystem.dto.response.UserResponseDTO;
import edu.miu.apsd.controllertestingusermanagementsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

@WebMvcTest(UserController.class) // From Spring : It loads application context, but not fully
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // From Spring
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO("john", "doe", "jd");
        UserResponseDTO userResponseDTO = new UserResponseDTO("jd");

        Mockito.when(userService.createUser(userRequestDTO)).thenReturn(Optional.of(userResponseDTO));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(userResponseDTO)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getUsers() throws Exception {
        List<UserResponseDTO> userResponseDTOList = List.of(
                new UserResponseDTO("john"),
                new UserResponseDTO("jane")
        );

        Mockito.when(userService.getAllUsers()).thenReturn(userResponseDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(userResponseDTOList)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void createUser_BadRequest() throws Exception {
        UserRequestDTO userRequestDTO =
                new UserRequestDTO("john", "doe", null);

        Mockito.when(userService.createUser(userRequestDTO)).thenReturn(Optional.empty());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO))
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateUser() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO("John", "Doe", "johnDoeUpdated");
        UserResponseDTO userResponseDTO = new UserResponseDTO("johnDoeUpdated");

        Mockito.when(userService.updateUser("johnDoe", userRequestDTO)).thenReturn(Optional.of(userResponseDTO));

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/users/johnDoe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(userResponseDTO)));
    }

    @Test
    void deleteUser() throws Exception {
        Mockito.doNothing().when(userService).deleteUserByName("johnDoe");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/johnDoe"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
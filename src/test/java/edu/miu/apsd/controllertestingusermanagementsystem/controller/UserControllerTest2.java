package edu.miu.apsd.controllertestingusermanagementsystem.controller;

import edu.miu.apsd.controllertestingusermanagementsystem.dto.request.UserRequestDTO;
import edu.miu.apsd.controllertestingusermanagementsystem.dto.response.UserResponseDTO;
import edu.miu.apsd.controllertestingusermanagementsystem.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

// Here, the Spring application context will not be loaded
@ExtendWith(MockitoExtension.class)
public class UserControllerTest2 {

    // Mock UserService and inject it into UserController
    @Mock
    private UserService userService;

    // Inject the UserService
    @InjectMocks
    private UserController userController;

    @Test
    public void testCreateUser_validInput_returnsCreatedUser() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO("john", "doe", "jd");
        UserResponseDTO userResponseDTO = new UserResponseDTO("jd");

        Mockito.when(userService.createUser(userRequestDTO)).thenReturn(Optional.of(userResponseDTO));

        ResponseEntity<UserResponseDTO> responseEntity = userController.createUser(userRequestDTO);

        assert responseEntity.getStatusCode() == HttpStatus.CREATED;
        assert responseEntity.getBody().equals(userResponseDTO);
    }

    @Test
    public void updateUser_validInput_updatesUser() {
        String username = "jd";
        UserRequestDTO userRequestDTO = new UserRequestDTO("john", "doe", "jds");
        UserResponseDTO userResponseDTO = new UserResponseDTO("jds");

        Mockito.when(userService.updateUser(username, userRequestDTO)).thenReturn(Optional.of(userResponseDTO));

        ResponseEntity<UserResponseDTO> responseEntity = userController.updateUser(username, userRequestDTO);

        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(responseEntity.getBody(), userResponseDTO);
    }

    @Test
    public void updateUser_inValidUsername_noUpdate() {
        UserRequestDTO userRequestDTO = new UserRequestDTO("john", "doe", "jd");
        String username = "invalid";
        Optional<UserResponseDTO> userResponseDTO = Optional.empty();

        Mockito.when(userService.updateUser(username, userRequestDTO)).thenReturn(userResponseDTO);

        ResponseEntity<UserResponseDTO> responseEntity = userController.updateUser(username, userRequestDTO);

        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        Assertions.assertNull(responseEntity.getBody());
    }
}

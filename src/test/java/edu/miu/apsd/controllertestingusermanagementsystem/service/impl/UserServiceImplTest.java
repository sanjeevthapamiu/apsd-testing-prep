package edu.miu.apsd.controllertestingusermanagementsystem.service.impl;

import edu.miu.apsd.controllertestingusermanagementsystem.dto.request.UserRequestDTO;
import edu.miu.apsd.controllertestingusermanagementsystem.dto.response.UserResponseDTO;
import edu.miu.apsd.controllertestingusermanagementsystem.model.User;
import edu.miu.apsd.controllertestingusermanagementsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void createUser() {
        User user = new User("john", "doe", "jd");
        UserRequestDTO userRequestDTO = new UserRequestDTO("john", "doe", "jd");
        UserResponseDTO expectedUserResponseDTO = new UserResponseDTO("jd");

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        Optional<UserResponseDTO> userResponseDTO = userService.createUser(userRequestDTO);

        assertTrue(userResponseDTO.isPresent());
        assertEquals(expectedUserResponseDTO, userResponseDTO.get());
    }

    @Test
    void updateUser() {

    }

    @Test
    void getUserByName() {
    }

    @Test
    void deleteUserByName() {
    }

    @Test
    void getAllUsers() {
        List<User> users = List.of(
                new User("John", "Doe", "jd"),
                new User("Jane", "Smith", "js")
        );

        List<UserResponseDTO> expectedUserResponseDTOList = List.of(
                new UserResponseDTO("jd"),
                new UserResponseDTO("js")
        );

        Mockito.when(userRepository.findAll()).thenReturn(users);

        List<UserResponseDTO> userResponseDTOList = userService.getAllUsers();

        assertEquals(expectedUserResponseDTOList, userResponseDTOList);
    }
}
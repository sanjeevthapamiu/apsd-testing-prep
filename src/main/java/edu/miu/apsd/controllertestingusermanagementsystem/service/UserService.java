package edu.miu.apsd.controllertestingusermanagementsystem.service;

import edu.miu.apsd.controllertestingusermanagementsystem.dto.request.UserRequestDTO;
import edu.miu.apsd.controllertestingusermanagementsystem.dto.response.UserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserResponseDTO> createUser(UserRequestDTO userRequestDTO);
    Optional<UserResponseDTO> updateUser(String username, UserRequestDTO userRequestDTO);
    Optional<UserResponseDTO> getUserByName(String name);
    void deleteUserByName(String name);
    List<UserResponseDTO> getAllUsers();

}

package edu.miu.apsd.controllertestingusermanagementsystem.service.impl;

import edu.miu.apsd.controllertestingusermanagementsystem.dto.request.UserRequestDTO;
import edu.miu.apsd.controllertestingusermanagementsystem.dto.response.UserResponseDTO;
import edu.miu.apsd.controllertestingusermanagementsystem.model.User;
import edu.miu.apsd.controllertestingusermanagementsystem.repository.UserRepository;
import edu.miu.apsd.controllertestingusermanagementsystem.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<UserResponseDTO> createUser(UserRequestDTO userRequestDTO) {
        if (userRepository.findByUsername(userRequestDTO.username()).isPresent()) {
            return Optional.empty();
        }

        User user = new User(userRequestDTO.firstName(), userRequestDTO.lastName(), userRequestDTO.username());
        User savedUser = userRepository.save(user);
        UserResponseDTO userResponseDTO = new UserResponseDTO(savedUser.getUsername());
        return Optional.of(userResponseDTO);
    }

    @Override
    public Optional<UserResponseDTO> updateUser(String username, UserRequestDTO userRequestDTO) {
        Optional<User> userFound = userRepository.findByUsername(username);

        if (userFound.isPresent()) {
            User user = userFound.get();
            user.setFirstName(userRequestDTO.firstName());
            user.setLastName(userRequestDTO.firstName());
            user.setUsername(userRequestDTO.username());
            User savedUser = userRepository.save(user);
            UserResponseDTO userResponseDTO = new UserResponseDTO(savedUser.getUsername());
            return Optional.of(userResponseDTO);
        }

        return Optional.empty();
    }

    @Override
    public Optional<UserResponseDTO> getUserByName(String name) {
        Optional<User> userFound = userRepository.findByUsername(name);

        if (userFound.isPresent()) {
            UserResponseDTO userResponseDTO = new UserResponseDTO(userFound.get().getUsername());
            return Optional.of(userResponseDTO);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteUserByName(String name) {
        getUserByName(name).ifPresent(userResponseDTO -> {
            userRepository.deleteByUsername(userResponseDTO.username());
        });
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDTO(user.getUsername()))
                .collect(Collectors.toList());
    }
}

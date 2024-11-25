package edu.miu.apsd.controllertestingusermanagementsystem.repository;

import edu.miu.apsd.controllertestingusermanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    void deleteByUsername(String username);

}

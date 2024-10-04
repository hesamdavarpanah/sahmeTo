package com.backend.sahmeto.authentication.repositories;

import com.backend.sahmeto.authentication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsUserByPhoneNumber(String phoneNumber);
    Optional<User> findByUsername(String username);
}
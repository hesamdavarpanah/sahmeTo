package com.backend.sahmeto.authentication.services;

import com.backend.sahmeto.authentication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Boolean checkUserExist(String phoneNumber) {
        return userRepository.existsUserByPhoneNumber(phoneNumber);
    }
}

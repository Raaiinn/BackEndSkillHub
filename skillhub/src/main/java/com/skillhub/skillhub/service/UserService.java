package com.skillhub.skillhub.service;

import com.skillhub.skillhub.domain.User;
import com.skillhub.skillhub.domain.responses.UserResponse;
import com.skillhub.skillhub.repository.UserRepository;
import com.skillhub.skillhub.security.JWTBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private JWTBuilder jwtBuilder;

    public UserService(UserRepository userRepository, JWTBuilder jwtBuilder) {
        this.userRepository = userRepository;
        this.jwtBuilder = jwtBuilder;
    }

    public UserResponse login(User user){
        User aux = userRepository.findOneByEmail(user.getEmail()).orElse(null);
        if(aux == null | !aux.getActive()){
            return null;
        }
        if(!new BCryptPasswordEncoder().matches(user.getPassword(), aux.getPassword())){
            return null;
        }
        return convertUser(aux);
    }

    public UserResponse convertUser(User user){
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), jwtBuilder.getToken(user.getId(), user.getEmail()), user.getRole(), user.getActive());
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }
}

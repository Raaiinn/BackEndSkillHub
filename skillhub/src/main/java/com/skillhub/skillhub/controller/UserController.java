package com.skillhub.skillhub.controller;

import com.skillhub.skillhub.domain.User;
import com.skillhub.skillhub.domain.responses.ErrorResponse;
import com.skillhub.skillhub.domain.responses.UserResponse;
import com.skillhub.skillhub.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="${request-mapping.controller.client}")
@RestController
@RequestMapping("${request-mapping.controller.user}")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User user) {
        if(user == null){
            return new ResponseEntity<>(new ErrorResponse("Correo o contraseña faltante", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
        UserResponse aux = userService.login(user);
        if(aux == null){
            return new ResponseEntity<>(new ErrorResponse("Correo o contraseña incorrecta, por favor, verifica las credenciales e intenta nuevamente",  HttpStatus.UNAUTHORIZED.value()),  HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(aux, HttpStatus.OK);
    }
}
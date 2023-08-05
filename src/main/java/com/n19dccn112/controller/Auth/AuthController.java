package com.n19dccn112.controller.Auth;

import com.n19dccn112.model.Auth.ChangePassByEmailRequest;
import com.n19dccn112.model.Auth.JwtResponse;
import com.n19dccn112.model.Auth.LoginRequest;
import com.n19dccn112.model.Auth.RegisterRequest;
import com.n19dccn112.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600) //cho phép http, get post..
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.checkLogin(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        return userService.register(signUpRequest);
    }

    @PostMapping("/changePassByEmail")
    public ResponseEntity<?> changePassByEmail(@Valid @RequestBody ChangePassByEmailRequest signUpRequest) {
        return userService.changePassByEmail(signUpRequest);
    }
}

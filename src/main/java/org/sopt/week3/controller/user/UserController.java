package org.sopt.week3.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.week3.dto.request.LoginRequest;
import org.sopt.week3.dto.request.UserRegisterRequest;
import org.sopt.week3.dto.response.LoginResponse;
import org.sopt.week3.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/users/register")
    public ResponseEntity<Void> register(
            @RequestBody @Valid final UserRegisterRequest userRegisterRequest
    ) {
        userService.register(userRegisterRequest.username(), userRegisterRequest.password(), userRegisterRequest.nickname());
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PostMapping("/auth/users/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest loginRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginRequest.username(), loginRequest.password()));
    }
}

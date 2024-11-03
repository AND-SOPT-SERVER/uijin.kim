package org.sopt.week3.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.week3.dto.request.SignInRequest;
import org.sopt.week3.dto.request.SignUpRequest;
import org.sopt.week3.dto.response.SignInResponse;
import org.sopt.week3.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<Void> signUp(
            @RequestBody @Valid final SignUpRequest signUpRequest
    ) {
        userService.signUp(signUpRequest.username(), signUpRequest.password(), signUpRequest.nickname());
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<SignInResponse> signIn(
            @RequestBody @Valid SignInRequest signInRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.signIn(signInRequest.username(), signInRequest.password()));
    }
}

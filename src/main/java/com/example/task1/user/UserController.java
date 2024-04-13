package com.example.task1.user;

import com.example.task1.security.jwt.JwtService;
import com.example.task1.user.dto.ForgotPassword;
import com.example.task1.user.dto.UserCreateDto;
import com.example.task1.user.dto.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;


    @PostMapping()
    @PreAuthorize("hasAuthority('CREATE')")
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserCreateDto userCreateDto) {

        UserResponseDto userResponseDto = userService.create(userCreateDto);
        String token = jwtService.generateToken(userCreateDto.getEmail(), Collections.emptyMap());
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.AUTHORIZATION, token)
                .body(userResponseDto);
    }

    @PostMapping("/forgot-password")
    @PreAuthorize("hasAnyAuthority('FORGOT_PASSWORD')")
    public ResponseEntity<UserResponseDto> forgotPassword(@RequestBody @Valid ForgotPassword forgotPassword) {
        UserResponseDto userResponseDto = userService.forgotPassword(forgotPassword);
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('GET_ALL')")
    public ResponseEntity<Page<UserResponseDto>> getAll(Pageable pageable) {
        Page<UserResponseDto> page = userService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }


    @DeleteMapping
    @PreAuthorize("hasAuthority('DELETE')")
    public ResponseEntity<?> delete(@RequestParam("id") Integer id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("delete");
    }
}

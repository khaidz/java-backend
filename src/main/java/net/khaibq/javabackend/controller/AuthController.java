package net.khaibq.javabackend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.khaibq.javabackend.config.jwt.JwtUtils;
import net.khaibq.javabackend.dto.BaseResponse;
import net.khaibq.javabackend.dto.auth.ChangePasswordRequestDto;
import net.khaibq.javabackend.dto.auth.ForgotPasswordRequestDto;
import net.khaibq.javabackend.dto.auth.LoginRequestDto;
import net.khaibq.javabackend.dto.auth.LoginResponseDto;
import net.khaibq.javabackend.dto.auth.RegisterRequestDto;
import net.khaibq.javabackend.dto.auth.RegisterResponseDto;
import net.khaibq.javabackend.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @PostMapping("/login")
    public BaseResponse<LoginResponseDto> handleLogin(@RequestBody LoginRequestDto dto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        if (authentication.isAuthenticated()) {
            LoginResponseDto result = LoginResponseDto.builder()
                    .accessToken(jwtUtils.generateToken(authentication)).build();
            return BaseResponse.success(result);
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @PostMapping("/register")
    public BaseResponse<RegisterResponseDto> handleRegister(@RequestBody @Valid RegisterRequestDto dto) {
        return BaseResponse.success(userService.handleRegister(dto));
    }

    @PostMapping("/forgot-password")
    public BaseResponse handleForgotPassword(@RequestBody @Valid ForgotPasswordRequestDto dto) {
        userService.handleForgotPassword(dto);
        return BaseResponse.success(null);
    }

    @GetMapping("/validate-key")
    public BaseResponse validKey(@RequestParam String key) {
        return BaseResponse.success(userService.validateKey(key));
    }

    @PostMapping("/change-password")
    public BaseResponse handleChangePassword(@RequestBody @Valid ChangePasswordRequestDto dto) {
        userService.handleChangePassword(dto);
        return BaseResponse.success(null);
    }
}

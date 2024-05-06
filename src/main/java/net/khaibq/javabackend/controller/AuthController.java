package net.khaibq.javabackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.khaibq.javabackend.config.jwt.JwtUtils;
import net.khaibq.javabackend.dto.BaseResponse;
import net.khaibq.javabackend.dto.auth.LoginRequestDto;
import net.khaibq.javabackend.dto.auth.LoginResponseDto;
import net.khaibq.javabackend.dto.auth.RegisterRequestDto;
import net.khaibq.javabackend.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserService userService;

    @Value("${app.token.jwtExpirationMs}")
    private int jwtExpirationMs;

    @PostMapping("/login")
    public BaseResponse<LoginResponseDto> handleLogin(@RequestBody @Valid LoginRequestDto dto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        if (authentication.isAuthenticated()) {
            String token = jwtUtils.generateToken(authentication);
            redisTemplate.opsForValue().set("TOKEN_" + dto.getUsername().toUpperCase(), token, jwtExpirationMs, TimeUnit.MILLISECONDS);
            LoginResponseDto result = LoginResponseDto.builder()
                    .accessToken(token).build();
            return BaseResponse.success(result);
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @PostMapping("/register")
    public BaseResponse<Long> handleRegister(@RequestBody @Valid RegisterRequestDto dto) {
        return BaseResponse.success(userService.registerUser(dto));
    }
}

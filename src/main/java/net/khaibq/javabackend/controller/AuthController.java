package net.khaibq.javabackend.controller;

import lombok.AllArgsConstructor;
import net.khaibq.javabackend.config.jwt.JwtUtils;
import net.khaibq.javabackend.dto.BaseResponse;
import net.khaibq.javabackend.dto.auth.LoginRequestDto;
import net.khaibq.javabackend.dto.auth.LoginResponseDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

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
}

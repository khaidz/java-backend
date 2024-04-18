package net.khaibq.javabackend.service;

import net.khaibq.javabackend.dto.auth.ChangePasswordRequestDto;
import net.khaibq.javabackend.dto.auth.ForgotPasswordRequestDto;
import net.khaibq.javabackend.dto.auth.RegisterRequestDto;
import net.khaibq.javabackend.dto.auth.RegisterResponseDto;
import net.khaibq.javabackend.dto.user.UserDto;

public interface UserService {
    RegisterResponseDto handleRegister(RegisterRequestDto dto);

    void handleForgotPassword(ForgotPasswordRequestDto dto);

    String validateKey(String key);

    void handleChangePassword(ChangePasswordRequestDto dto);

    UserDto getUserInfo();
}

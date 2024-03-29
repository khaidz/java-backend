package net.khaibq.javabackend.controller;


import lombok.AllArgsConstructor;
import net.khaibq.javabackend.dto.BaseResponse;
import net.khaibq.javabackend.dto.user.UserDto;
import net.khaibq.javabackend.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/getUserInfo")
    public BaseResponse<UserDto> getUserInfo() {
        return BaseResponse.success(null);
    }
}

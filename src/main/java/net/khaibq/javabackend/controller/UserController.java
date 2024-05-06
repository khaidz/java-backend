package net.khaibq.javabackend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.khaibq.javabackend.config.annotation.CheckLevel;
import net.khaibq.javabackend.dto.BaseResponse;
import net.khaibq.javabackend.dto.PageDataDto;
import net.khaibq.javabackend.dto.user.CreateDto;
import net.khaibq.javabackend.dto.user.UpdateDto;
import net.khaibq.javabackend.dto.user.UserDto;
import net.khaibq.javabackend.exception.BaseException;
import net.khaibq.javabackend.service.UserService;
import net.khaibq.javabackend.ultis.SecurityUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/getUserInfo")
    public BaseResponse<UserDto> getUserInfo() {
        return BaseResponse.success(userService.getUserInfo());
    }

    @CheckLevel(value = 100)
    @GetMapping
    public BaseResponse<PageDataDto<UserDto>> getListUser(Pageable pageable) {
        return BaseResponse.success(userService.getList(pageable));
    }

    @GetMapping("/{id}")
    public BaseResponse<UserDto> getDetailUser(@PathVariable Long id) {
        return BaseResponse.success(userService.getDetail(id));
    }

    @PostMapping
    public BaseResponse<UserDto> createUser(@RequestBody @Valid CreateDto dto) {
        return BaseResponse.success(userService.create(dto));
    }

    @PutMapping("/{id}")
    public BaseResponse<UserDto> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateDto dto) {
        if (!Objects.equals(id, dto.getId())) {
            throw new BaseException("id is invalid");
        }
        return BaseResponse.success(userService.update(dto));
    }
}

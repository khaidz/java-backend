package net.khaibq.javabackend.controller;


import lombok.AllArgsConstructor;
import net.khaibq.javabackend.dto.BaseResponse;
import net.khaibq.javabackend.dto.PageDataDto;
import net.khaibq.javabackend.dto.user.UserDto;
import net.khaibq.javabackend.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public BaseResponse<PageDataDto<UserDto>> getListUser(Pageable pageable) {
        return BaseResponse.success(userService.getList(pageable));
    }

    @GetMapping("/{id}")
    public BaseResponse<UserDto> getDetailUser(@PathVariable Long id) {
        return BaseResponse.success(userService.getDetail(id));
    }
}

package net.khaibq.javabackend.controller;

import lombok.AllArgsConstructor;
import net.khaibq.javabackend.dto.BaseResponse;
import net.khaibq.javabackend.dto.role.RoleDto;
import net.khaibq.javabackend.service.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@AllArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public BaseResponse<List<RoleDto>> getListRole() {
        return BaseResponse.success(roleService.getListRole());
    }
}

package net.khaibq.javabackend.service;

import net.khaibq.javabackend.dto.PageDataDto;
import net.khaibq.javabackend.dto.user.CreateDto;
import net.khaibq.javabackend.dto.user.UpdateDto;
import net.khaibq.javabackend.dto.user.UserDto;
import org.springframework.data.domain.Pageable;

public interface UserService {
    PageDataDto<UserDto> getList(Pageable pageable);

    UserDto getDetail(Long id);

    UserDto create(CreateDto dto);

    UserDto update(UpdateDto dto);
}

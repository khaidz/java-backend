package net.khaibq.javabackend.service;

import net.khaibq.javabackend.dto.PageDataDto;
import net.khaibq.javabackend.dto.user.UserDto;
import org.springframework.data.domain.Pageable;

public interface UserService {
    PageDataDto<UserDto> getList(Pageable pageable);

    UserDto getDetail(Long id);
}

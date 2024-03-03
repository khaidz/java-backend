package net.khaibq.javabackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.khaibq.javabackend.dto.PageDataDto;
import net.khaibq.javabackend.dto.user.UserDto;
import net.khaibq.javabackend.exception.BaseException;
import net.khaibq.javabackend.repository.UserRepository;
import net.khaibq.javabackend.service.UserService;
import net.khaibq.javabackend.ultis.CommonUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageDataDto<UserDto> getList(Pageable pageable) {
        Page<UserDto> page = userRepository.findAll(pageable)
                .map(entity -> modelMapper.map(entity, UserDto.class));
        return CommonUtils.convertPageData(page);
    }

    @Override
    public UserDto getDetail(Long id) {
        return userRepository.findById(id)
                .map(entity -> modelMapper.map(entity, UserDto.class))
                .orElseThrow(() -> new BaseException("User does not exist"));
    }
}

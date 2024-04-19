package net.khaibq.javabackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.khaibq.javabackend.dto.PageDataDto;
import net.khaibq.javabackend.dto.user.CreateDto;
import net.khaibq.javabackend.dto.user.UpdateDto;
import net.khaibq.javabackend.dto.user.UserDto;
import net.khaibq.javabackend.entity.User;
import net.khaibq.javabackend.exception.BaseException;
import net.khaibq.javabackend.repository.UserRepository;
import net.khaibq.javabackend.service.UserService;
import net.khaibq.javabackend.ultis.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
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
                .orElseThrow(() -> new BaseException("Người dùng không tồn tại"));
    }

    @Override
    public UserDto create(CreateDto dto) {
        if (userRepository.existsByUsernameIgnoreCase(dto.getUsername())) {
            throw new BaseException("Tên đăng nhập đã được sử dụng");
        }
        if (userRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new BaseException("Email đã được sử dụng");
        }

        User user = new User();
        user.setUsername(dto.getUsername().toLowerCase());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setIsDeleted(0);
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto update(UpdateDto dto) {
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new BaseException("Không tìm thấy người dùng với id = " + dto.getId()));
        String email = dto.getEmail();
        if (StringUtils.isNotEmpty(dto.getEmail()) && !StringUtils.equalsIgnoreCase(user.getEmail(), dto.getEmail())
            && userRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new BaseException("Email đã được sử dụng");
        }
        user.setEmail(email);
        if (StringUtils.isNotEmpty(dto.getPassword()) && !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        user.setIsDeleted(dto.getIsDeleted());
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }
}

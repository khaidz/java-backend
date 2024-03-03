package net.khaibq.javabackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.khaibq.javabackend.dto.PageDataDto;
import net.khaibq.javabackend.dto.user.CreateDto;
import net.khaibq.javabackend.dto.user.UpdateDto;
import net.khaibq.javabackend.dto.user.UserDto;
import net.khaibq.javabackend.entity.Department;
import net.khaibq.javabackend.entity.Role;
import net.khaibq.javabackend.entity.User;
import net.khaibq.javabackend.exception.BaseException;
import net.khaibq.javabackend.repository.DepartmentRepository;
import net.khaibq.javabackend.repository.RoleRepository;
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
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
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
                .orElseThrow(() -> new BaseException("User does not exist"));
    }

    @Override
    public UserDto create(CreateDto dto) {
        if (userRepository.existsByUsernameIgnoreCase(dto.getUsername())) {
            throw new BaseException("Username already exists");
        }
        if (userRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new BaseException("Email already exists");
        }
        Department department = null;
        if (StringUtils.isNotEmpty(dto.getDepartmentCode())) {
            department = departmentRepository.findByCode(dto.getDepartmentCode())
                    .orElseThrow(() -> new BaseException("Department does not exist"));
        }

        List<Role> roles = dto.getRoles()
                .stream()
                .map(x -> roleRepository.findByCode(x)
                        .orElseThrow(() -> new BaseException("role '" + x + "' does not exist")))
                .toList();

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setDepartment(department);
        user.setRoles(roles);
        user.setIsDeleted(0);
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto update(UpdateDto dto) {
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new BaseException("Not found user with id = " + dto.getId()));
        String email = dto.getEmail();
        if (StringUtils.isNotEmpty(dto.getEmail()) && !StringUtils.equalsIgnoreCase(user.getEmail(), dto.getEmail())
            && userRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new BaseException("Email is already in use by another user");
        }
        user.setEmail(email);

        Department department = user.getDepartment();
        if (StringUtils.isEmpty(dto.getDepartmentCode())) {
            department = null;
        } else if (department == null || !Objects.equals(department.getCode(), dto.getDepartmentCode())) {
            department = departmentRepository.findByCode(dto.getDepartmentCode())
                    .orElseThrow(() -> new BaseException("Department does not exist"));
        }
        user.setDepartment(department);

        if (StringUtils.isNotEmpty(dto.getPassword()) && !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        List<Role> roles = user.getRoles()
                .stream()
                .filter(x -> dto.getRoles().contains(x.getCode()))
                .collect(Collectors.toList());

        List<String> strRoles = roles.stream().map(Role::getCode).toList();

        dto.getRoles()
                .stream()
                .filter(x -> !strRoles.contains(x))
                .forEach(x -> {
                    Role newRole = roleRepository.findByCode(x)
                            .orElseThrow(() -> new BaseException("role '" + x + "' does not exist"));
                    roles.add(newRole);
                });
        user.setRoles(roles);
        user.setIsDeleted(dto.getIsDeleted());
        userRepository.save(user);

        return modelMapper.map(user, UserDto.class);
    }
}

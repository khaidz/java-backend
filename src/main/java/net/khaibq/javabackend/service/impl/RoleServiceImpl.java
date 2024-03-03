package net.khaibq.javabackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.khaibq.javabackend.dto.role.RoleDto;
import net.khaibq.javabackend.repository.RoleRepository;
import net.khaibq.javabackend.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<RoleDto> getListRole() {
        return roleRepository.findAll().stream().map(x -> modelMapper.map(x, RoleDto.class)).toList();
    }
}

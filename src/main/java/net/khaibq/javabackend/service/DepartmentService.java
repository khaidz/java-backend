package net.khaibq.javabackend.service;

import net.khaibq.javabackend.dto.PageDataDto;
import net.khaibq.javabackend.dto.department.CreateDto;
import net.khaibq.javabackend.dto.department.DepartmentDto;
import net.khaibq.javabackend.dto.department.DepartmentTreeDto;
import net.khaibq.javabackend.dto.department.UpdateDto;
import org.springframework.data.domain.Pageable;

public interface DepartmentService {
    PageDataDto<DepartmentDto> getList(Pageable pageable);

    DepartmentDto getDetail(Long id);

    DepartmentDto create(CreateDto dto);

    DepartmentDto update(UpdateDto dto);

    DepartmentTreeDto getDepartmentTree(String code);

    byte[] exportExcel();
}

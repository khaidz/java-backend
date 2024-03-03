package net.khaibq.javabackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.khaibq.javabackend.dto.PageDataDto;
import net.khaibq.javabackend.dto.department.CreateDto;
import net.khaibq.javabackend.dto.department.DepartmentDto;
import net.khaibq.javabackend.dto.department.DepartmentTreeDto;
import net.khaibq.javabackend.dto.department.UpdateDto;
import net.khaibq.javabackend.entity.Department;
import net.khaibq.javabackend.entity.User;
import net.khaibq.javabackend.exception.BaseException;
import net.khaibq.javabackend.repository.CustomRepository;
import net.khaibq.javabackend.repository.DepartmentRepository;
import net.khaibq.javabackend.repository.UserRepository;
import net.khaibq.javabackend.service.DepartmentService;
import net.khaibq.javabackend.ultis.CommonUtils;
import net.khaibq.javabackend.ultis.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final CustomRepository customRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageDataDto<DepartmentDto> getList(Pageable pageable) {
        Page<DepartmentDto> page = departmentRepository.findAll(pageable)
                .map(entity -> modelMapper.map(entity, DepartmentDto.class));
        return CommonUtils.convertPageData(page);
    }

    @Override
    public DepartmentDto getDetail(Long id) {
        return departmentRepository.findById(id)
                .map(entity -> modelMapper.map(entity, DepartmentDto.class))
                .orElseThrow(() -> new BaseException("Department does not exist"));
    }

    @Override
    public DepartmentDto create(CreateDto dto) {
        Department parent = null;
        User manager = null;
        if (StringUtils.isNotEmpty(dto.getParentCode())) {
            parent = departmentRepository.findByCode(dto.getParentCode())
                    .orElseThrow(() -> new BaseException("Parent department does not exist"));
        }

        if (StringUtils.isNotEmpty(dto.getManagerUsername())) {
            manager = userRepository.findByUsernameIgnoreCase(dto.getManagerUsername());
            if (manager == null) {
                throw new BaseException("Manager does not exist");
            }
        }

        Department department = new Department();
        department.setCode("DED" + customRepository.getSequence() + "E");
        department.setName(dto.getName());
        department.setParent(parent);
        department.setManager(manager);
        department.setIsDeleted(0);
        departmentRepository.save(department);
        return modelMapper.map(department, DepartmentDto.class);
    }

    @Override
    public DepartmentDto update(UpdateDto dto) {
        Department department = departmentRepository.findById(dto.getId())
                .orElseThrow(() -> new BaseException("Not found department with id = " + dto.getId()));

        Department parent = department.getParent();
        User manager = department.getManager();

        if (StringUtils.isEmpty(dto.getParentCode())) {
            parent = null;
        } else if (parent == null || !Objects.equals(parent.getCode(), dto.getParentCode())) {
            parent = departmentRepository.findByCode(dto.getParentCode())
                    .orElseThrow(() -> new BaseException("Parent department does not exist"));

            //Kiểm tra parentCode có phải thuộc dept con hay không -> tránh vòng lặp
            List<String> lstDeptCodeTree = departmentRepository.getDepartmentTree(department.getCode())
                    .stream()
                    .map(Department::getCode)
                    .toList();
            if (lstDeptCodeTree.contains(dto.getParentCode())) {
                throw new BaseException("Cannot set this department or a child department to become a parent department");
            }
        }

        if (StringUtils.isEmpty(dto.getManagerUsername())) {
            manager = null;
        } else if (manager == null || !StringUtils.equalsIgnoreCase(manager.getUsername(), dto.getManagerUsername())) {
            manager = userRepository.findByUsernameIgnoreCase(dto.getManagerUsername());
            if (manager == null) {
                throw new BaseException("Manager does not exist");
            }
        }

        department.setName(dto.getName());
        department.setParent(parent);
        department.setManager(manager);
        department.setIsDeleted(dto.getIsDeleted());
        departmentRepository.save(department);

        return modelMapper.map(department, DepartmentDto.class);
    }

    @Override
    public DepartmentTreeDto getDepartmentTree(String code) {
        List<DepartmentTreeDto> list = departmentRepository.getDepartmentTree(code)
                .stream()
                .map(entity -> modelMapper.map(entity, DepartmentTreeDto.class))
                .toList();

        DepartmentTreeDto dto = list.stream()
                .filter(x -> Objects.equals(x.getCode(), code))
                .findFirst()
                .orElseThrow(() -> new BaseException("Not found department with code = '" + code + "'"));
        dto.setLevel(1);
        setDepartmentWithChildren(list, dto);

        return dto;
    }

    @Override
    public byte[] exportExcel() {
        List<DepartmentDto> list = departmentRepository.findAll()
                .stream()
                .map(x -> modelMapper.map(x, DepartmentDto.class))
                .toList();
        return FileUtils.exportExcel(list, null, "templates/excel/template_export_department.xlsx");
    }

    private void setDepartmentWithChildren(List<DepartmentTreeDto> list, DepartmentTreeDto dto) {
        List<DepartmentTreeDto> children = list.stream()
                .filter(x -> Objects.equals(x.getParentCode(), dto.getCode()))
                .map(x -> {
                    x.setLevel(dto.getLevel() + 1);
                    setDepartmentWithChildren(list, x);
                    return x;
                })
                .toList();
        dto.setChildren(children);
    }
}

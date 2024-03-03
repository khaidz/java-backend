package net.khaibq.javabackend.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.khaibq.javabackend.dto.BaseResponse;
import net.khaibq.javabackend.dto.PageDataDto;
import net.khaibq.javabackend.dto.department.CreateDto;
import net.khaibq.javabackend.dto.department.DepartmentDto;
import net.khaibq.javabackend.dto.department.DepartmentTreeDto;
import net.khaibq.javabackend.dto.department.UpdateDto;
import net.khaibq.javabackend.exception.BaseException;
import net.khaibq.javabackend.service.DepartmentService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/departments")
@AllArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    public BaseResponse<PageDataDto<DepartmentDto>> getListDepartment(Pageable pageable) {
        return BaseResponse.success(departmentService.getList(pageable));
    }

    @GetMapping("/{id}")
    public BaseResponse<DepartmentDto> getDetailDepartment(@PathVariable Long id) {
        return BaseResponse.success(departmentService.getDetail(id));
    }

    @PostMapping
    public BaseResponse<DepartmentDto> createDepartment(@RequestBody @Valid CreateDto dto) {
        return BaseResponse.success(departmentService.create(dto));
    }

    @PutMapping("/{id}")
    public BaseResponse<DepartmentDto> updateDepartment(@PathVariable Long id, @RequestBody @Valid UpdateDto dto) {
        if (!Objects.equals(id, dto.getId())) {
            throw new BaseException("id is invalid");
        }
        return BaseResponse.success(departmentService.update(dto));
    }

    @GetMapping("/tree")
    public BaseResponse<DepartmentTreeDto> getTreeDepartment(@RequestParam String code) {
        return BaseResponse.success(departmentService.getDepartmentTree(code));
    }

    @GetMapping("/export-excel")
    public ResponseEntity<byte[]> exportRole() {
        byte[] data = departmentService.exportExcel();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=data_department.xlsx")
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
//                .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
                .contentLength(data.length)
                .body(data);
    }
}

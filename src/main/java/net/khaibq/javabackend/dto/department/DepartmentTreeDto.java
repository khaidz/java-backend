package net.khaibq.javabackend.dto.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentTreeDto {
    private Long id;
    private String code;
    private String name;
    private Integer level;
    private String parentCode;
    private Integer isDeleted;
    private String managerUsername;
    private List<DepartmentTreeDto> children = new ArrayList<>();
}

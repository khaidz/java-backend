package net.khaibq.javabackend.dto.department;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentTreeDto {
    private Long id;
    private String code;
    private String name;
    private Integer level;
    private String parentCode;
    private String parentName;
    private String managerUsername;
    private Integer isDeleted;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Date createdDate;
    private String lastModifiedBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Date lastModifiedDate;
    private List<DepartmentTreeDto> children = new ArrayList<>();
}

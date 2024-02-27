package net.khaibq.javabackend.dto.department;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateDto {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    private String parentCode;
    private String managerUsername;
    private Integer isDeleted;
}

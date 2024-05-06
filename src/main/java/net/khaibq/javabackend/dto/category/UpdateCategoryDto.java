package net.khaibq.javabackend.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCategoryDto {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    private Long parentId;
    private Integer priority;
    private Integer isDeleted;
}

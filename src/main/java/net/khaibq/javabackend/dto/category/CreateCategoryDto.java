package net.khaibq.javabackend.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryDto {
    @NotBlank
    private String name;
    private Long parentId;
    private Integer priority;
    private Integer isDeleted;
}

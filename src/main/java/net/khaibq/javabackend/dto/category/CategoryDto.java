package net.khaibq.javabackend.dto.category;

import lombok.Data;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private String slug;
    private Long parentId;
    private String parentName;
    private Integer priority;
}

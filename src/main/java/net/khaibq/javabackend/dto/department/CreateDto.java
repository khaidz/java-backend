package net.khaibq.javabackend.dto.department;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateDto {
    @NotEmpty
    private String name;
    private String parentCode;
    private String managerUsername;
}

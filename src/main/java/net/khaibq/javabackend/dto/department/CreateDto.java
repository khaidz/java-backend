package net.khaibq.javabackend.dto.department;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateDto {
    @NotNull
    private String name;
    private String parentCode;
    private String managerUsername;
}

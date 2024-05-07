package net.khaibq.javabackend.dto.fileInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponseDto {
    private Integer code;
    private String originFileName;
    private String error;
    private String path;
}

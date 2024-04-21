package net.khaibq.javabackend.dto.fileInfo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileUploadRequestDto {
    @NotNull(message = "Chưa chọn file")
    private MultipartFile file;
    private String type;
}

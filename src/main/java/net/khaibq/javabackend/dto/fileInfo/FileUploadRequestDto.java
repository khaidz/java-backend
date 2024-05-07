package net.khaibq.javabackend.dto.fileInfo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileUploadRequestDto {
    private MultipartFile[] files;
    private String type;
}
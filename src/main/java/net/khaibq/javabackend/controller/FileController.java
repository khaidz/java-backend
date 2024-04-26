package net.khaibq.javabackend.controller;

import lombok.RequiredArgsConstructor;
import net.khaibq.javabackend.dto.BaseResponse;
import net.khaibq.javabackend.dto.fileInfo.FileUploadRequestDto;
import net.khaibq.javabackend.dto.fileInfo.FileUploadResponseDto;
import net.khaibq.javabackend.service.FileInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    private final FileInfoService fileInfoService;

    @PostMapping("/upload")
    public BaseResponse<List<FileUploadResponseDto>> uploadFile(@RequestPart("files") MultipartFile[] files) {
        FileUploadRequestDto dto = new FileUploadRequestDto();
        dto.setFiles(files);
        return BaseResponse.success(fileInfoService.uploadFile(dto));
    }
}

package net.khaibq.javabackend.service;

import net.khaibq.javabackend.dto.fileInfo.FileUploadRequestDto;
import net.khaibq.javabackend.dto.fileInfo.FileUploadResponseDto;

import java.util.List;

public interface FileService {
    List<FileUploadResponseDto> uploadFile(FileUploadRequestDto dto);
}

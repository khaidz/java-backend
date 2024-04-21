package net.khaibq.javabackend.service;

import net.khaibq.javabackend.dto.fileInfo.FileUploadRequestDto;

public interface FileInfoService {
    String uploadFile(FileUploadRequestDto dto);
}

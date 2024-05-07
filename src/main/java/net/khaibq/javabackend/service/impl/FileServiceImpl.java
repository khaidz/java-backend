package net.khaibq.javabackend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.khaibq.javabackend.dto.fileInfo.FileUploadRequestDto;
import net.khaibq.javabackend.dto.fileInfo.FileUploadResponseDto;
import net.khaibq.javabackend.exception.BaseException;
import net.khaibq.javabackend.service.FileService;
import net.khaibq.javabackend.ultis.CommonUtils;
import org.apache.tomcat.jni.FileInfo;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
    private final Environment env;

    @Override
    public List<FileUploadResponseDto> uploadFile(FileUploadRequestDto dto) {
        MultipartFile[] files = dto.getFiles();
        if (files == null || files.length == 0) {
            throw new BaseException("Vui lòng chọn file để upload");
        }
        String uploadDir = env.getProperty("app.file.uploadDir");
        List<String> listExtensionAllow = env.getProperty("app.file.extensions", List.class);
        List<FileUploadResponseDto> result = new LinkedList<>();
        for (MultipartFile file : files) {
            String extension = CommonUtils.getFileExtension(file.getOriginalFilename());
            if (!CommonUtils.isValidExtension(listExtensionAllow, extension)) {
                result.add(new FileUploadResponseDto(0, file.getOriginalFilename(), "Định dạng file không hỗ trợ", null));
                continue;
            }
            try {
                byte[] bytes = file.getBytes();
                String fileName = UUID.randomUUID() + "." + extension;
                assert uploadDir != null;
                Path path = Paths.get(uploadDir, fileName);
                if (dto.getType() != null) {
                    path = Paths.get(uploadDir, dto.getType(), fileName);
                }

                if (!Files.exists(path.getParent())) {
                    Files.createDirectories(path.getParent());
                }
                Files.write(path, bytes);

                result.add(new FileUploadResponseDto(1, file.getOriginalFilename(), null, fileName));
            } catch (IOException e) {
                result.add(new FileUploadResponseDto(0, file.getOriginalFilename(), e.getMessage(), null));
            }
        }
        return result;
    }
}

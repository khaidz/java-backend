package net.khaibq.javabackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.khaibq.javabackend.dto.fileInfo.FileUploadRequestDto;
import net.khaibq.javabackend.entity.FileInfo;
import net.khaibq.javabackend.exception.BaseException;
import net.khaibq.javabackend.repository.FileInfoRepository;
import net.khaibq.javabackend.service.FileInfoService;
import net.khaibq.javabackend.ultis.CommonUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FileInfoServiceImpl implements FileInfoService {
    private final FileInfoRepository fileInfoRepository;
    private final Environment env;

    @Override
    public String uploadFile(FileUploadRequestDto dto) {
        MultipartFile file = dto.getFile();
        if (file.isEmpty()) {
            throw new BaseException("Vui lòng chọn file để upload");
        }
        String uploadDir = env.getProperty("app.file.uploadDir");
        List<String> listExtensionAllow = env.getProperty("app.file.extensions", List.class);
        String extension = CommonUtils.getFileExtension(file.getOriginalFilename()).toLowerCase();
        if (!CommonUtils.isValidExtension(listExtensionAllow, extension)) {
            throw new BaseException("Định dạng file không hỗ trợ");
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

            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(fileName);
            fileInfo.setOriginFileName(file.getOriginalFilename());
            fileInfo.setSize(file.getSize());
            fileInfo.setExtension(extension);
            fileInfo.setPath(fileName);
            fileInfoRepository.save(fileInfo);
            return fileInfo.getPath();
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new BaseException("Upload thất bại. Vui lòng kiểm tra và thực hiện lại");
        }
    }
}

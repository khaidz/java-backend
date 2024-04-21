package net.khaibq.javabackend.repository;

import net.khaibq.javabackend.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
}

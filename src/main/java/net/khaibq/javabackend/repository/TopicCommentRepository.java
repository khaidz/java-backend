package net.khaibq.javabackend.repository;

import net.khaibq.javabackend.entity.TopicComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicCommentRepository extends JpaRepository<TopicComment, Long> {
}

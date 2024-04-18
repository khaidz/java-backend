package net.khaibq.javabackend.repository;

import net.khaibq.javabackend.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
}

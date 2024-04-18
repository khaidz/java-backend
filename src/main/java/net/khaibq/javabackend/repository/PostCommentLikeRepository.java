package net.khaibq.javabackend.repository;

import net.khaibq.javabackend.entity.PostCommentLike;
import net.khaibq.javabackend.entity.UserPostCommentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostCommentLikeRepository extends JpaRepository<PostCommentLike, UserPostCommentLikeId> {
    @Query("select x from PostCommentLike x where x.id.userId = ?1 and x.id.postCommentId = ?2")
    PostCommentLike findByUserIdAndPostCommentTd(Long userId, Long postCommentId);
}

package net.khaibq.javabackend.repository;

import net.khaibq.javabackend.entity.TopicCommentLike;
import net.khaibq.javabackend.entity.UserTopicCommentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicCommentLikeRepository extends JpaRepository<TopicCommentLike, UserTopicCommentLikeId> {
    @Query("select x from TopicCommentLike x where x.id.userId = ?1 and x.id.topicCommentId = ?2")
    TopicCommentLike findByUserIdAndTopicCommentTd(Long userId, Long topicCommentId);
}

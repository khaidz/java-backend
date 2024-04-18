package net.khaibq.javabackend.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tbl_topic_comment_like")
@Data
public class TopicCommentLike extends AbstractAuditingEntity {
    @EmbeddedId
    private UserTopicCommentLikeId id;
    private Integer type;//Like, heart, sad,...
}

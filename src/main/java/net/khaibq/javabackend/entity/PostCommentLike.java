package net.khaibq.javabackend.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tbl_post_comment_like")
@Data
public class PostCommentLike extends AbstractAuditingEntity {
    @EmbeddedId
    private UserPostCommentLikeId id;
    private Integer type;//Like, heart, sad,...
}

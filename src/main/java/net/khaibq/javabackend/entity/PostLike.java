package net.khaibq.javabackend.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tbl_post_like")
@Data
public class PostLike extends AbstractAuditingEntity {
    @EmbeddedId
    private UserPostLikeId id;
    private Integer type;//Like, heart, sad,...
}

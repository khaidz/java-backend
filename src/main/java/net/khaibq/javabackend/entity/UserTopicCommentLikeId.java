package net.khaibq.javabackend.entity;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UserTopicCommentLikeId implements Serializable {
    private Long userId;
    private Long topicCommentId;
}

package net.khaibq.javabackend.entity;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UserPostLikeId implements Serializable {
    private Long postId;
    private Long userId;
}

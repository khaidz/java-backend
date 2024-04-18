package net.khaibq.javabackend.dto.post_comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentDto {
    private Long id;
    private String message;
    private Long postId;
    private Long parentId;
    private Date createdDate;
    private String createdBy;
    private Long countLike;
    private List<PostCommentDto> children;
}

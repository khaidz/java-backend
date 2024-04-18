package net.khaibq.javabackend.dto.topic_comment;

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
public class TopicCommentDto {
    private Long id;
    private String message;
    private Long parentId;
    private Integer topicType;
    private Date createdDate;
    private String createdBy;
    private Long countLike;
    private List<TopicCommentDto> children;
}

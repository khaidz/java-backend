package net.khaibq.javabackend.service;

import net.khaibq.javabackend.dto.topic_comment.TopicCommentDto;
import net.khaibq.javabackend.dto.topic_comment.TopicCommentRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TopicCommentService {
    void handleCreateTopicComment(TopicCommentRequestDto dto);

    Page<TopicCommentDto> getListTopicComment(Integer topicType, Pageable pageable);

    Integer handleLikeTopicComment(Long id);

    Integer deleteTopicComment(Long id);
}

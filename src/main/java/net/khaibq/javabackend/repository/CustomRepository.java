package net.khaibq.javabackend.repository;

import net.khaibq.javabackend.dto.post.PostDetailDto;
import net.khaibq.javabackend.dto.post.PostDto;
import net.khaibq.javabackend.dto.post_comment.PostCommentDto;
import net.khaibq.javabackend.dto.rating.RatingDto;
import net.khaibq.javabackend.dto.topic_comment.TopicCommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomRepository {
    Page<TopicCommentDto> getPageTopicCommentDto(Integer topicType, Pageable pageable);

    Page<PostCommentDto> getPagePostCommentDto(Long postId, Pageable pageable);

    RatingDto getDataRating();

    List<PostDto> getListPostNewest();

    PostDetailDto getPostDetail(Long postId);
}

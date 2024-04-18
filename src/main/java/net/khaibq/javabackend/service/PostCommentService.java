package net.khaibq.javabackend.service;

import net.khaibq.javabackend.dto.post_comment.PostCommentDto;
import net.khaibq.javabackend.dto.post_comment.PostCommentRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostCommentService {
    void handleCreatePostComment(PostCommentRequestDto dto);

    Page<PostCommentDto> getListPostComment(Long postId, Pageable pageable);

    Long handleLikePostComment(Long postCommentId);
    Long deletePostComment(Long postCommentId);
}

package net.khaibq.javabackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.khaibq.javabackend.config.security.CustomUser;
import net.khaibq.javabackend.dto.post_comment.PostCommentDto;
import net.khaibq.javabackend.dto.post_comment.PostCommentRequestDto;
import net.khaibq.javabackend.entity.PostComment;
import net.khaibq.javabackend.entity.PostCommentLike;
import net.khaibq.javabackend.entity.User;
import net.khaibq.javabackend.entity.UserPostCommentLikeId;
import net.khaibq.javabackend.exception.BaseException;
import net.khaibq.javabackend.repository.CustomRepository;
import net.khaibq.javabackend.repository.PostCommentLikeRepository;
import net.khaibq.javabackend.repository.PostCommentRepository;
import net.khaibq.javabackend.repository.PostRepository;
import net.khaibq.javabackend.repository.UserRepository;
import net.khaibq.javabackend.service.PostCommentService;
import net.khaibq.javabackend.ultis.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {
    private final CustomRepository customRepository;
    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;
    private final PostCommentLikeRepository postCommentLikeRepository;
    private final UserRepository userRepository;

    @Override
    public void handleCreatePostComment(PostCommentRequestDto dto) {
        postRepository.findById(dto.getPostId()).orElseThrow(() -> new BaseException("Không tìm thấy bài viết"));
        if (dto.getParentId() != null) {
            postCommentRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new BaseException("Không tìm thấy bình luận"));
        }
        PostComment postComment = new PostComment();
        postComment.setMessage(dto.getMessage());
        postComment.setPostId(dto.getPostId());
        postComment.setParentId(dto.getParentId());
        postCommentRepository.save(postComment);
    }

    @Override
    public Page<PostCommentDto> getListPostComment(Long postId, Pageable pageable) {
        return customRepository.getPagePostCommentDto(postId, pageable);
    }

    @Override
    public Long handleLikePostComment(Long postCommentId) {
        PostComment postComment = postCommentRepository.findById(postCommentId).orElseThrow(() -> new BaseException("Không tìm thấy comment"));
        CustomUser customUser = (CustomUser) SecurityUtils.getCurrentUser().orElseThrow(() -> new BaseException("Có lỗi xảy ra"));
        Long userId = customUser.getId();
        PostCommentLike postCommentLike = postCommentLikeRepository.findByUserIdAndPostCommentTd(userId, postCommentId);
        if (postCommentLike != null) {
            throw new BaseException("Đã like bình luận từ trước đó");
        }
        postCommentLike = new PostCommentLike();
        postCommentLike.setId(new UserPostCommentLikeId(userId, postCommentId));
        postCommentLike.setType(0);
        postCommentLikeRepository.save(postCommentLike);

        return postComment.getPostId();
    }

    @Override
    public Long deletePostComment(Long postCommentId) {
        String username = SecurityUtils.getCurrentUsername().orElseThrow(() -> new BaseException("Không tìm thấy người dùng"));
        User user = userRepository.findByUsernameIgnoreCase(username);
        if (user.getLevel() == null || user.getLevel() < 100) {
            throw new BaseException("Truy cập không được phép");
        }
        PostComment postComment = postCommentRepository.findById(postCommentId).orElseThrow(() -> new BaseException("Không tìm thấy comment"));
        Long postId = postComment.getPostId();
        postCommentRepository.delete(postComment);
        postCommentRepository.deleteByParentId(postCommentId);
        return postId;
    }
}

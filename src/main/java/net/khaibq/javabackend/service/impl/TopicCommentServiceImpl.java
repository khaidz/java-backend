package net.khaibq.javabackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.khaibq.javabackend.config.security.CustomUser;
import net.khaibq.javabackend.dto.topic_comment.TopicCommentDto;
import net.khaibq.javabackend.dto.topic_comment.TopicCommentRequestDto;
import net.khaibq.javabackend.entity.TopicComment;
import net.khaibq.javabackend.entity.TopicCommentLike;
import net.khaibq.javabackend.entity.UserTopicCommentLikeId;
import net.khaibq.javabackend.exception.BaseException;
import net.khaibq.javabackend.repository.CustomRepository;
import net.khaibq.javabackend.repository.TopicCommentLikeRepository;
import net.khaibq.javabackend.repository.TopicCommentRepository;
import net.khaibq.javabackend.service.TopicCommentService;
import net.khaibq.javabackend.ultis.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TopicCommentServiceImpl implements TopicCommentService {
    private final TopicCommentRepository topicCommentRepository;
    private final TopicCommentLikeRepository topicCommentLikeRepository;
    private final CustomRepository customRepository;

    @Override
    public void handleCreateTopicComment(TopicCommentRequestDto dto) {
        if (dto.getParentId() != null) {
            topicCommentRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new BaseException("Không thể xử lý yêu cầu do không tìm thấy bái viết tham chiếu"));
        }
        TopicComment topicComment = new TopicComment();
        topicComment.setMessage(dto.getMessage());
        topicComment.setTopicType(dto.getTopicType());
        topicComment.setParentId(dto.getParentId());
        topicCommentRepository.save(topicComment);
    }

    @Override
    public Page<TopicCommentDto> getListTopicComment(Integer topicType, Pageable pageable) {
        return customRepository.getPageTopicCommentDto(topicType, pageable);
    }

    @Override
    public Integer handleLikeTopicComment(Long topicCommentId) {
        TopicComment topicComment = topicCommentRepository.findById(topicCommentId).orElseThrow(() -> new BaseException("Không tìm thấy comment"));
        CustomUser customUser = (CustomUser) SecurityUtils.getCurrentUser().orElseThrow(() -> new BaseException("Có lỗi xảy ra"));
        Long userId = customUser.getId();
        TopicCommentLike topicCommentLike = topicCommentLikeRepository.findByUserIdAndTopicCommentTd(userId, topicCommentId);
        if (topicCommentLike != null) {
            throw new BaseException("Đã like bình luận từ trước đó");
        }
        topicCommentLike = new TopicCommentLike();
        topicCommentLike.setId(new UserTopicCommentLikeId(userId, topicCommentId));
        topicCommentLike.setType(0);
        topicCommentLikeRepository.save(topicCommentLike);

        return topicComment.getTopicType();
    }
}

package net.khaibq.javabackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.khaibq.javabackend.dto.BaseResponse;
import net.khaibq.javabackend.dto.PageDataDto;
import net.khaibq.javabackend.dto.topic_comment.TopicCommentDto;
import net.khaibq.javabackend.dto.topic_comment.TopicCommentRequestDto;
import net.khaibq.javabackend.service.TopicCommentService;
import net.khaibq.javabackend.ultis.CommonUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/topic-comment")
@RequiredArgsConstructor
public class TopicCommentController {
    private final TopicCommentService topicCommentService;

    @GetMapping("/{topicType}")
    public BaseResponse<PageDataDto<TopicCommentDto>> getListTopicComment(
            @PathVariable Integer topicType,
            @PageableDefault(sort = {"createdDate"}, direction = Sort.Direction.DESC, value = 20) Pageable pageable) {
        return BaseResponse.success(CommonUtils.convertPageData(topicCommentService.getListTopicComment(topicType, pageable)));
    }

    @PostMapping
    public BaseResponse<PageDataDto<TopicCommentDto>> addNewTopicComment(@RequestBody @Valid TopicCommentRequestDto dto) {
        topicCommentService.handleCreateTopicComment(dto);

        return BaseResponse.success(CommonUtils.convertPageData(
                topicCommentService.getListTopicComment(dto.getTopicType(), PageRequest.of(0, 20, Sort.by("createdDate").descending()))
        ));
    }

    @PostMapping("/like/{topicCommentId}")
    public BaseResponse<PageDataDto<TopicCommentDto>> likeTopicComment(@PathVariable Long topicCommentId) {
        Integer topicType = topicCommentService.handleLikeTopicComment(topicCommentId);
        return BaseResponse.success(CommonUtils.convertPageData(
                topicCommentService.getListTopicComment(topicType, PageRequest.of(0, 20, Sort.by("createdDate").descending()))
        ));
    }
}

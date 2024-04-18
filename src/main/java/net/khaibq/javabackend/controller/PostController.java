package net.khaibq.javabackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.khaibq.javabackend.dto.BaseResponse;
import net.khaibq.javabackend.dto.PageDataDto;
import net.khaibq.javabackend.dto.post.PostDetailDto;
import net.khaibq.javabackend.dto.post.PostDto;
import net.khaibq.javabackend.dto.post.PostRequestDto;
import net.khaibq.javabackend.dto.post_comment.PostCommentDto;
import net.khaibq.javabackend.dto.post_comment.PostCommentRequestDto;
import net.khaibq.javabackend.service.PostCommentService;
import net.khaibq.javabackend.service.PostService;
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

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostCommentService postCommentService;

    @PostMapping
    public BaseResponse<PostDto> createPost(@RequestBody @Valid PostRequestDto dto) {
        return BaseResponse.success(postService.createPost(dto));
    }

    @SneakyThrows
    @GetMapping("/newst")
    public BaseResponse<List<PostDto>> getListPostNewest() {
        return BaseResponse.success(postService.getListPostNewest());
    }

    @GetMapping("/{categoryId}")
    public BaseResponse<PageDataDto<PostDto>> getListPostByCategory(@PathVariable Long categoryId,
                                                                    @PageableDefault(sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return BaseResponse.success(postService.getListPostByCategory(categoryId, pageable));
    }

    @GetMapping("/detail/{postId}")
    public BaseResponse<PostDetailDto> getPostDetail(@PathVariable Long postId) {
        return BaseResponse.success(postService.getPostDetail(postId));
    }

    @GetMapping("/comments/{postId}")
    public BaseResponse<PageDataDto<PostCommentDto>> getListPostComment(
            @PathVariable Long postId,
            @PageableDefault(sort = {"createdDate"}, direction = Sort.Direction.DESC, value = 10) Pageable pageable) {
        return BaseResponse.success(CommonUtils.convertPageData(postCommentService.getListPostComment(postId, pageable)));
    }

    @PostMapping("/comments")
    public BaseResponse<PageDataDto<PostCommentDto>> addNewPostComment(@RequestBody @Valid PostCommentRequestDto dto) {
        postCommentService.handleCreatePostComment(dto);

        return BaseResponse.success(CommonUtils.convertPageData(
                postCommentService.getListPostComment(dto.getPostId(), PageRequest.of(0, 20, Sort.by("createdDate").descending()))
        ));
    }

    @PostMapping("/comments/like/{postCommentId}")
    public BaseResponse<PageDataDto<PostCommentDto>> likePostComment(@PathVariable Long postCommentId) {
        Long postId = postCommentService.handleLikePostComment(postCommentId);
        return BaseResponse.success(CommonUtils.convertPageData(
                postCommentService.getListPostComment(postId, PageRequest.of(0, 20, Sort.by("createdDate").descending()))
        ));
    }
}

package net.khaibq.javabackend.service;

import net.khaibq.javabackend.dto.PageDataDto;
import net.khaibq.javabackend.dto.post.PostDetailDto;
import net.khaibq.javabackend.dto.post.PostDto;
import net.khaibq.javabackend.dto.post.PostRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostDto createPost(PostRequestDto dto);

    PageDataDto<PostDto> getListPostByCategory(Long categoryId, Pageable pageable);

    List<PostDto> getListPostNewest();

    PostDetailDto getPostDetail(Long postId);
}

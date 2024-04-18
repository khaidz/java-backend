package net.khaibq.javabackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.khaibq.javabackend.dto.PageDataDto;
import net.khaibq.javabackend.dto.post.PostDetailDto;
import net.khaibq.javabackend.dto.post.PostDto;
import net.khaibq.javabackend.dto.post.PostRequestDto;
import net.khaibq.javabackend.entity.Post;
import net.khaibq.javabackend.exception.BaseException;
import net.khaibq.javabackend.repository.CategoryRepository;
import net.khaibq.javabackend.repository.CustomRepository;
import net.khaibq.javabackend.repository.PostRepository;
import net.khaibq.javabackend.service.PostService;
import net.khaibq.javabackend.ultis.CommonUtils;
import net.khaibq.javabackend.ultis.UnicodeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;
    private final CustomRepository customRepository;


    @Override
    public PostDto createPost(PostRequestDto dto) {
        categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new BaseException("Không tìm thấy chuyên mục"));

        Post post = new Post();
        post.setCategoryId(dto.getCategoryId());
        post.setTitle(dto.getTitle());
        post.setSlug(CommonUtils.convertToSlug(dto.getTitle()));
        post.setContent(dto.getContent());
        postRepository.save(post);

        return mapper.map(post, PostDto.class);
    }

    @Override
    public PageDataDto<PostDto> getListPostByCategory(Long categoryId, Pageable pageable) {
        Page<PostDto> page = postRepository.findByCategoryId(categoryId, pageable)
                .map(x -> mapper.map(x, PostDto.class));
        return CommonUtils.convertPageData(page);
    }

    @Override
    public List<PostDto> getListPostNewest() {
        return customRepository.getListPostNewest();
    }

    @Override
    public PostDetailDto getPostDetail(Long postId) {
        return customRepository.getPostDetail(postId);
    }
}

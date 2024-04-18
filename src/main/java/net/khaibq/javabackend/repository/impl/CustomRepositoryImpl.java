package net.khaibq.javabackend.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import net.khaibq.javabackend.dto.post.PostDetailDto;
import net.khaibq.javabackend.dto.post.PostDto;
import net.khaibq.javabackend.dto.post_comment.PostCommentDto;
import net.khaibq.javabackend.dto.rating.RatingDto;
import net.khaibq.javabackend.dto.topic_comment.TopicCommentDto;
import net.khaibq.javabackend.exception.BaseException;
import net.khaibq.javabackend.repository.CustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CustomRepositoryImpl implements CustomRepository {
    private final EntityManager entityManager;

    @Override
    public Page<TopicCommentDto> getPageTopicCommentDto(Integer topicType, Pageable pageable) {
        String sqlQuery = """
                        with like_count as(
                            select topic_comment_id, count(topic_comment_id) cnt from tbl_topic_comment_like group by topic_comment_id
                        ), topic_parent as (
                            select parent.id, parent.topic_type, parent.message, parent.parent_id, parent.created_by, parent.created_date, coalesce(lc.cnt, 0) cnt
                            from tbl_topic_comment parent
                            left join like_count lc on parent.id = lc.topic_comment_id
                            where parent_id is null and topic_type = :topicType
                            order by created_date desc
                            limit :limit offset :offset
                        ), topic_child as (
                            select child.id, child.topic_type, child.message, child.parent_id, child.created_by, child.created_date, coalesce(lc.cnt, 0) cnt
                            from tbl_topic_comment child
                            left join like_count lc on child.id = lc.topic_comment_id
                            where parent_id in (select id from topic_parent)
                            order by created_date asc
                        )
                        select * from topic_parent
                        union all
                        select * from topic_child;
                """;

        String sqlCount = """
                      select count(*) from tbl_topic_comment where parent_id is null and topic_type = :topicType 
                """;

        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("topicType", topicType);
        query.setParameter("limit", pageable.getPageSize());
        query.setParameter("offset", (pageable.getPageNumber()) * pageable.getPageSize());
        List<Object[]> queryResultList = query.getResultList();

        List<TopicCommentDto> convertList = queryResultList
                .stream()
                .map(this::convertToDto).toList();

        List<TopicCommentDto> parentList = convertList.stream()
                .filter(x -> x.getParentId() == null)
                .collect(Collectors.toList());

        parentList = parentList.stream()
                .map(item -> {
                    List<TopicCommentDto> children = convertList.stream()
                            .filter(x -> Objects.equals(x.getParentId(), item.getId()))
                            .toList();
                    item.setChildren(children);
                    return item;
                }).toList();

        Query queryCount = entityManager.createNativeQuery(sqlCount);
        queryCount.setParameter("topicType", topicType);
        List<Long> countList = queryCount.getResultList();
        long count = 0;
        if (!countList.isEmpty()) {
            count = countList.get(0);
        }
        return new PageImpl<>(parentList, pageable, count);
    }

    @Override
    public Page<PostCommentDto> getPagePostCommentDto(Long postId, Pageable pageable) {
        String sqlQuery = """
                    with like_count as(
                          select post_comment_id, count(post_comment_id) cnt from tbl_post_comment_like group by post_comment_id
                      ), topic_parent as (
                          select parent.id, parent.post_id, parent.message, parent.parent_id, parent.created_by, parent.created_date, coalesce(lc.cnt, 0) cnt
                          from tbl_post_comment parent
                          left join like_count lc on parent.id = lc.post_comment_id
                          where parent_id is null and post_id = :postId
                          order by created_date desc
                          limit :limit offset :offset
                      ), topic_child as (
                          select child.id, child.post_id, child.message, child.parent_id, child.created_by, child.created_date, coalesce(lc.cnt, 0) cnt
                          from tbl_post_comment child
                          left join like_count lc on child.id = lc.post_comment_id
                          where parent_id in (select id from topic_parent)
                          order by created_date asc
                      )
                      select * from topic_parent
                      union all
                      select * from topic_child;
                """;

        String sqlCount = """
                      select count(*) from tbl_post_comment where parent_id is null and post_id = :postId
                """;

        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("postId", postId);
        query.setParameter("limit", pageable.getPageSize());
        query.setParameter("offset", (pageable.getPageNumber()) * pageable.getPageSize());
        List<Object[]> queryResultList = query.getResultList();

        List<PostCommentDto> convertList = queryResultList
                .stream()
                .map(this::convertPostCommentDto).toList();

        List<PostCommentDto> parentList = convertList.stream()
                .filter(x -> x.getParentId() == null)
                .collect(Collectors.toList());

        parentList = parentList.stream()
                .map(item -> {
                    List<PostCommentDto> children = convertList.stream()
                            .filter(x -> Objects.equals(x.getParentId(), item.getId()))
                            .toList();
                    item.setChildren(children);
                    return item;
                }).toList();

        Query queryCount = entityManager.createNativeQuery(sqlCount);
        queryCount.setParameter("postId", postId);
        List<Long> countList = queryCount.getResultList();
        long count = 0;
        if (!countList.isEmpty()) {
            count = countList.get(0);
        }
        return new PageImpl<>(parentList, pageable, count);
    }
    @Override
    public RatingDto getDataRating() {
        String sql = "select count(*) cnt, avg(value) from tbl_rating;";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> queryResultList = query.getResultList();

        List<RatingDto> convertList = queryResultList
                .stream()
                .map(item -> {
                    RatingDto ratingDto = new RatingDto();
                    ratingDto.setCountRating(item[0] != null ? Integer.valueOf(String.valueOf(item[0])) : null);
                    ratingDto.setAverage(item[1] != null ? Double.valueOf(String.valueOf(item[1])) : null);
                    return ratingDto;
                }).toList();
        return convertList.get(0);
    }

    @Override
    public List<PostDto> getListPostNewest() {
        String sql = """
                    select id, title, slug, category_id, created_by, created_date
                    from (select p.*, row_number() over (partition by category_id ORDER BY created_date DESC) AS row_num
                          from tbl_post p) as subquery
                    where row_num <= 3
                """;
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> queryResultList = query.getResultList();
        List<PostDto> convertList = queryResultList
                .stream()
                .map(item -> {
                    PostDto dto = new PostDto();
                    dto.setId(item[0] != null ? Long.valueOf(String.valueOf(item[0])) : null);
                    dto.setTitle(item[1] != null ? String.valueOf(item[1]) : null);
                    dto.setSlug(item[2] != null ? String.valueOf(item[2]) : null);
                    dto.setCategoryId(item[3] != null ? Long.valueOf(String.valueOf(item[3])) : null);
                    dto.setCreatedBy(item[4] != null ? String.valueOf(item[4]) : null);
                    dto.setCreatedDate(item[5] != null ? (Date) item[5] : null);
                    return dto;
                }).toList();
        return convertList;
    }

    @Override
    public PostDetailDto getPostDetail(Long postId) {
        String sql = """
                select p.id, p.title, p.slug, p.content, p.created_date, p.created_by,
                       p.category_id, c.slug categorySlug, c.title categoryTitle
                       from tbl_post p
                left join tbl_category c
                on p.category_id = c.id
                where p.id = :postId
            """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("postId", postId);
        List<Object[]> queryResultList = query.getResultList();
        PostDetailDto result = queryResultList
                .stream()
                .map(item -> {
                    PostDetailDto dto = new PostDetailDto();
                    dto.setId(item[0] != null ? Long.valueOf(String.valueOf(item[0])) : null);
                    dto.setTitle(item[1] != null ? String.valueOf(item[1]) : null);
                    dto.setSlug(item[2] != null ? String.valueOf(item[2]) : null);
                    dto.setContent(item[3] != null ? String.valueOf(item[3]) : null);
                    dto.setCreatedDate(item[4] != null ? (Date) item[4] : null);
                    dto.setCreatedBy(item[5] != null ? String.valueOf(item[5]) : null);
                    dto.setCategoryId(item[6] != null ? Long.valueOf(String.valueOf(item[6])) : null);
                    dto.setCategorySlug(item[7] != null ? String.valueOf(item[7]) : null);
                    dto.setCategoryTitle(item[8] != null ? String.valueOf(item[8]) : null);
                    return dto;
                }).findFirst().orElseThrow(() -> new BaseException("Không tìm thấy bài viết"));
        return result;
    }

    private TopicCommentDto convertToDto(Object[] item) {
        TopicCommentDto dto = new TopicCommentDto();
        dto.setId(item[0] != null ? Long.valueOf(String.valueOf(item[0])) : null);
        dto.setTopicType(item[1] != null ? Integer.valueOf(String.valueOf(item[1])) : null);
        dto.setMessage(item[2] != null ? String.valueOf(item[2]) : null);
        dto.setParentId(item[3] != null ? Long.valueOf(String.valueOf(item[3])) : null);
        dto.setCreatedBy(item[4] != null ? String.valueOf(item[4]) : null);
        dto.setCreatedDate(item[5] != null ? (Date) item[5] : null);
        dto.setCountLike(item[6] != null ? Long.valueOf(String.valueOf(item[6])) : null);
        return dto;
    }

    private PostCommentDto convertPostCommentDto(Object[] item) {
        PostCommentDto dto = new PostCommentDto();
        dto.setId(item[0] != null ? Long.valueOf(String.valueOf(item[0])) : null);
        dto.setPostId(item[1] != null ? Long.valueOf(String.valueOf(item[1])) : null);
        dto.setMessage(item[2] != null ? String.valueOf(item[2]) : null);
        dto.setParentId(item[3] != null ? Long.valueOf(String.valueOf(item[3])) : null);
        dto.setCreatedBy(item[4] != null ? String.valueOf(item[4]) : null);
        dto.setCreatedDate(item[5] != null ? (Date) item[5] : null);
        dto.setCountLike(item[6] != null ? Long.valueOf(String.valueOf(item[6])) : null);
        return dto;
    }
}

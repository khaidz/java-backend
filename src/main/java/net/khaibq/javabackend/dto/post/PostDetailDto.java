package net.khaibq.javabackend.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailDto {
    private Long id;
    private String title;
    private String content;
    private String slug;
    private Long categoryId;
    private String categoryTitle;
    private String categorySlug;
    private Date createdDate;
    private String createdBy;
}

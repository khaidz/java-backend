package net.khaibq.javabackend.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    @NotBlank(message = "Tiêu đề bài viết không được để trống")
    @Length(min = 3, max = 500, message = "Tiêu đề bài viết có độ dài từ 3-2000 ký tự")
    private String title;

    @NotBlank(message = "Nội dung bài viết không được để trống")
    @Length(min = 3, max = 5000, message = "Nội dung bài viết có độ dài từ 3-5000 ký tự")
    private String content;

    @NotNull(message = "Chuyên mục không được để trống")
    private Long categoryId;
}

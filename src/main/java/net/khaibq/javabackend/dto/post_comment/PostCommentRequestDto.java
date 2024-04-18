package net.khaibq.javabackend.dto.post_comment;

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
public class PostCommentRequestDto {
    @NotBlank(message = "Nội dung tin nhắn không được để trống")
    @Length(min = 2, max = 2000, message = "Nội dung có độ dài từ 3-2000 ký tự")
    private String message;
    @NotNull(message = "Yêu cầu không hợp lệ")
    private Long postId;
    private Long parentId;
}

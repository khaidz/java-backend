package net.khaibq.javabackend.dto.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDto {
    @NotBlank
    @Length(min = 3, max = 50, message = "Tên đăng nhập từ 3-50 ký tự")
    private String username;

    @NotNull
    @Length(min = 3, message = "Mật khẩu tối thiếu 3 ký tự")
    private String password;

    @NotNull
    @Email(message = "Định dạng email không hợp lệ")
    private String email;

    @NotNull(message = "Chưa chọn giới tính")
    private Integer gender;
}

package net.khaibq.javabackend.dto.rating;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequestDto {
    @NotNull(message = "Giá trị không hợp lệ")
    @Min(value = 1, message = "Giá trị nhỏ nhất cho phép là 1")
    @Max(value = 5, message = "Giá trị lớn nhất cho phép là 5")
    private Integer value;
}

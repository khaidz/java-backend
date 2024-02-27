package net.khaibq.javabackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.khaibq.javabackend.ultis.Constant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BaseResponse<T> {
    private Integer status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(Constant.SUCCESSED_STATUS, data, null);
    }

    public static <T> BaseResponse<T> fail(String message) {
        return new BaseResponse<>(Constant.FAILED_STATUS, null, message);
    }
}

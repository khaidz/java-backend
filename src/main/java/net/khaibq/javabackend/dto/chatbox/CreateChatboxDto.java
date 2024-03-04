package net.khaibq.javabackend.dto.chatbox;

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
public class CreateChatboxDto {
    @NotBlank
    @Length(min = 2, max = 1000)
    private String message;

    @NotNull
    private String username;
}

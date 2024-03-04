package net.khaibq.javabackend.dto.chatbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatboxDto {
    private Long id;
    private String message;
    private String createdBy;
    private Date createdDate;
}

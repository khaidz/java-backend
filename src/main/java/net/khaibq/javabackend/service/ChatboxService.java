package net.khaibq.javabackend.service;

import net.khaibq.javabackend.dto.PageDataDto;
import net.khaibq.javabackend.dto.chatbox.ChatboxDto;
import net.khaibq.javabackend.dto.chatbox.CreateChatboxDto;
import org.springframework.data.domain.Pageable;

public interface ChatboxService {
    PageDataDto<ChatboxDto> getList(Pageable pageable);

    ChatboxDto create(CreateChatboxDto dto);
}

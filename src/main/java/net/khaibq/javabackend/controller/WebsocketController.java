package net.khaibq.javabackend.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.khaibq.javabackend.dto.PageDataDto;
import net.khaibq.javabackend.dto.chatbox.ChatboxDto;
import net.khaibq.javabackend.dto.chatbox.CreateChatboxDto;
import net.khaibq.javabackend.service.ChatboxService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class WebsocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatboxService chatboxService;

    @MessageMapping("/send-message")
    public void chat(@Payload CreateChatboxDto dto) {
        chatboxService.create(dto);
        PageDataDto<ChatboxDto> data = chatboxService.getList(PageRequest.of(0, 10, Sort.Direction.DESC, "createdDate"));
        simpMessagingTemplate.convertAndSend("/topic/chatbox", data);
    }
}

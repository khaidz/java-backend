package net.khaibq.javabackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.khaibq.javabackend.dto.PageDataDto;
import net.khaibq.javabackend.dto.chatbox.ChatboxDto;
import net.khaibq.javabackend.dto.chatbox.CreateChatboxDto;
import net.khaibq.javabackend.entity.Chatbox;
import net.khaibq.javabackend.entity.User;
import net.khaibq.javabackend.exception.BaseException;
import net.khaibq.javabackend.repository.ChatboxRepository;
import net.khaibq.javabackend.repository.UserRepository;
import net.khaibq.javabackend.service.ChatboxService;
import net.khaibq.javabackend.ultis.CommonUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Transactional
@AllArgsConstructor
public class ChatboxServiceImpl implements ChatboxService {
    private final ChatboxRepository chatboxRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageDataDto<ChatboxDto> getList(Pageable pageable) {
        return CommonUtils.convertPageData(chatboxRepository.findAll(pageable)
                .map(x -> modelMapper.map(x, ChatboxDto.class)));
    }

    @Override
    public ChatboxDto create(CreateChatboxDto dto) {
        User user = userRepository.findByUsernameIgnoreCase(dto.getUsername());
        if (user == null){
            throw new BaseException("User is invalid");
        }
        Chatbox chatbox = new Chatbox();
        chatbox.setMessage(dto.getMessage());
        chatbox.setCreatedBy(user.getUsername());
        chatbox.setCreatedDate(new Date());
        chatbox.setLastModifiedBy(user.getUsername());
        chatbox.setLastModifiedDate(new Date());
        chatboxRepository.save(chatbox);
        return modelMapper.map(chatbox, ChatboxDto.class);
    }
}

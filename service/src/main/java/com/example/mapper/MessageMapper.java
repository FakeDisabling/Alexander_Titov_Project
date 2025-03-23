package com.example.mapper;

import com.example.model.Message;
import com.example.model.MessageDto;
import com.example.model.User;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public Message toMessage(MessageDto dto) {
        if (dto == null) {
            return null;
        }
        return new Message(
                dto.getId(),
                new User(dto.getSender()),
                new User(dto.getReceiver()),
                dto.getContent(),
                dto.getSentAt()
        );
    }

    public MessageDto toMessageDto(Message entity) {
        if (entity == null) {
            return null;
        }
        return new MessageDto(
                entity.getId(),
                entity.getSender().getId(),
                entity.getReceiver().getId(),
                entity.getContent(),
                entity.getSentAt()
        );
    }
}

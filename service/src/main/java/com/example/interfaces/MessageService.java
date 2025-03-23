package com.example.interfaces;

import com.example.model.Message;
import com.example.model.MessageDto;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService extends InterfaceService<MessageDto> {

    List<MessageDto> findMessageByUserId(Long user1Id, Long user2Id);
}

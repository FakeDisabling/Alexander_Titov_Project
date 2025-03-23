package com.example.impl;

import com.example.interfaces.MessageDao;
import com.example.interfaces.MessageService;
import com.example.mapper.MessageMapper;
import com.example.model.Message;
import com.example.model.MessageDto;
import com.example.model.Role;
import com.example.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageImpl implements MessageService {

    private final MessageDao messageDao;
    private final MessageMapper messageMapper;

    @Override
    public MessageDto add(MessageDto object)  {
        try {
            Message message = new Message();
            BeanUtils.copyProperties(object, message);
            message.setReceiver(new User(object.getReceiver()));
            message.setSender(new User(object.getSender()));
            Message savedMessage = messageDao.save(message);
            object.setId(savedMessage.getId());
            log.info("Adding message: {}", object);
            return object;
        } catch (Exception e) {
            log.error("Add message error:", e);
        }
        return object;
    }

    @Override
    public MessageDto get(Long id) {
        try {
            Message message = messageDao.findById(id).orElse(null);
            log.info("Getting message: {}", message);
            return messageMapper.toMessageDto(message);
        } catch (Exception e) {
            log.error("Get message error:", e);
            return null;
        }
    }

    @Override
    public boolean update(MessageDto object, Long id) {
        try {
            Message message = messageDao.findById(id).orElse(null);
            if (message == null) {
                log.warn("message not found");
                return false;
            }
            messageDao.save(messageMapper.toMessage(object));
            log.info("Updating message: {}", object);
            return true;
        } catch (Exception e) {
            log.error("Update message error:", e);
            return false;
        }
    }

    @Override
    public boolean remove(Long id)  {
        try {
            messageDao.deleteById(id);
            log.info("Removing message: {}", id);
            return true;
        } catch (Exception e) {
            log.error("Removing message error:", e);
            return false;
        }
    }

    @Override
    public List<MessageDto> getAll()  {
        try {
            List<Message> messages = messageDao.findAll();
            log.info("Getting all messages: {}", messages);
            return messages.stream().map(messageMapper::toMessageDto).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Getting all message error:", e);
            return null;
        }
    }

    @Override
    public List<MessageDto> findMessageByUserId(Long user1Id, Long user2Id) {
        try {
            List<Message> messages = messageDao.findMessageByUserId(user1Id, user2Id);
            log.info("Getting all messages from users: {}", messages);
            return messages.stream().map(messageMapper::toMessageDto).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Getting all message error:", e);
            return null;
        }
    }
}

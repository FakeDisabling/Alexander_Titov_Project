package com.example.impl;

import com.example.interfaces.MessageDao;
import com.example.mapper.MessageMapper;
import com.example.model.Message;
import com.example.model.MessageDto;
import com.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MessageImplTest {

    private MessageImpl messageService;
    private MessageDao messageDao;

    @BeforeEach
    void init() {
        messageDao = Mockito.spy(MessageDao.class);
        MessageMapper messageMapper = Mockito.mock(MessageMapper.class);
        messageService = new MessageImpl(messageDao, messageMapper);
    }

    @Test
    void testUnitDeleteById() {
        Long messageId = 1L;

        Mockito.doNothing()
                .when(messageDao)
                .deleteById(messageId);

        boolean isDeleted = messageService.remove(messageId);
        assertTrue(isDeleted);
    }

    @Test
    void testUnitGetAll() {
        List<Message> messageList = new ArrayList<>();
        Mockito.doReturn(messageList)
                .when(messageDao)
                .findAll();

        List<MessageDto> messageDtoList = messageService.getAll();
        assertEquals(messageList.size(), messageDtoList.size());
    }

    @Test
    void testUnitSave() {
        MessageDto messageDto = new MessageDto(1L, 1L, 1L, "Test", new Date());

        Message message = new Message(1L, new User(1L), new User(1L), "Test", new Date());

        Mockito.when(messageDao.findById(Mockito.anyLong())).thenReturn(Optional.of(message));
        Mockito.when(messageDao.save(Mockito.any(Message.class))).thenReturn(message);

        MessageDto savedPost = messageService.add(messageDto);
        assertNotNull(savedPost);
    }

    @Test
    void testUnitUpdate(){
        MessageDto messageDto = new MessageDto(1L, 1L, 1L, "Test", new Date());

        Message message = new Message(1L, new User(1L), new User(1L), "Test", new Date());

        Mockito.when(messageDao.findById(Mockito.anyLong())).thenReturn(Optional.of(message));
        Mockito.when(messageDao.save(Mockito.any(Message.class))).thenReturn(message);

        assertTrue(messageService.update(messageDto, messageDto.getId()));
    }

    @Test
    void testUnitGetById() {
        Long id = 1L;
        Message message = new Message();
        Mockito.when(messageDao.findById(id)).thenReturn(Optional.of(message));

        MessageDto result = messageService.get(id);
        assertNull(result);
    }
}

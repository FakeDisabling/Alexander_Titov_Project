package com.example.impl;

import com.example.interfaces.CommentDao;
import com.example.mapper.CommentMapper;
import com.example.model.*;
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
public class CommentImplTest {

    private CommentImpl commentService;
    private CommentDao commentDao;

    @BeforeEach
    void init() {
        commentDao = Mockito.spy(CommentDao.class);
        CommentMapper commentMapper = Mockito.mock(CommentMapper.class);
        commentService = new CommentImpl(commentDao, commentMapper);
    }

    @Test
    void testUnitDeleteById() {
        Long commentId = 1L;

        Mockito.doNothing()
                .when(commentDao)
                .deleteById(commentId);

        boolean isDeleted = commentService.remove(commentId);
        assertTrue(isDeleted);
    }

    @Test
    void testUnitGetAll() {
        List<Comment> commentList = new ArrayList<>();
        Mockito.doReturn(commentList)
                .when(commentDao)
                .findAll();

        List<CommentDto> commentDtos = commentService.getAll();
        assertEquals(commentList.size(), commentDtos.size());
    }

    @Test
    void testUnitSave() {
        CommentDto commentDto = new CommentDto(1L, 1L, 1L, "test", new Date());

        Comment comment = new Comment(1L, new User(1L), new Listing(1L), "test", new Date());

        Mockito.when(commentDao.findById(Mockito.anyLong())).thenReturn(Optional.of(comment));
        Mockito.when(commentDao.save(Mockito.any(Comment.class))).thenReturn(comment);

        CommentDto savedComment = commentService.add(commentDto);
        assertNotNull(savedComment);
    }

    @Test
    void testUnitUpdate(){
        CommentDto commentDto = new CommentDto(1L, 1L, 1L, "test", new Date());

        Comment comment = new Comment(1L, new User(1L), new Listing(1L), "test", new Date());

        Mockito.when(commentDao.findById(Mockito.anyLong())).thenReturn(Optional.of(comment));
        Mockito.when(commentDao.save(Mockito.any(Comment.class))).thenReturn(comment);

        assertTrue(commentService.update(commentDto, commentDto.getId()));
    }

    @Test
    void testUnitGetById() {
        Long id = 1L;
        Comment comment = new Comment();
        Mockito.when(commentDao.findById(id)).thenReturn(Optional.of(comment));

        CommentDto result = commentService.get(id);
        assertNull(result);
    }
}

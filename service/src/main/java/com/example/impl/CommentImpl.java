package com.example.impl;

import com.example.interfaces.CommentDao;
import com.example.interfaces.CommentService;
import com.example.mapper.CommentMapper;
import com.example.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentImpl implements CommentService {

    private final CommentDao commentDao;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto add(CommentDto object) {
        try {
            Comment comment = new Comment();
            comment.setId(null);
            BeanUtils.copyProperties(object, comment);
            comment.setUser(new User(object.getUser()));
            comment.setListing(new Listing(object.getListing()));
            comment.setCreatedAt(new Date());
            Comment savedComment = commentDao.save(comment);
            object.setId(savedComment.getId());
            log.info("Adding comment {}", object);
            return object;
        } catch (Exception e) {
            log.error("Add comment error", e);
        }
        return object;
    }

    @Override
    public CommentDto get(Long id) {
        try {
            Comment comment = commentDao.findById(id).orElse(null);
            log.info("Getting comment {}", id);
            return commentMapper.toCommentDto(comment);
        } catch (Exception e) {
            log.error("Get comment error", e);
            return null;
        }
    }

    @Override
    public boolean update(CommentDto object, Long id) {
        try {
            Comment comment = commentDao.findById(id).orElse(null);
            if (comment == null) {
                log.warn("comment not found");
                return false;
            }
            commentDao.save(commentMapper.toComment(object));
            log.info("Updating comment {}", object);
            return true;
        } catch (Exception e) {
            log.error("Update comment error", e);
            return false;
        }
    }

    @Override
    public boolean remove(Long id)  {
        try {
            commentDao.deleteById(id);
            log.info("Removing comment {}", id);
            return true;
        } catch (Exception e) {
            log.error("Removing comment error", e);
            return false;
        }
    }

    @Override
    public List<CommentDto> getAll()  {
        try {
            List<Comment> comments = commentDao.findAll();
            log.info("Getting all comment {}", comments.size());
            return comments.stream().map(commentMapper::toCommentDto).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Getting all comment error", e);
            return null;
        }
    }

    @Override
    public List<CommentDto> findByListingId(Long listingId) {
        try {
            List<Comment> comments = commentDao.findByListingId(listingId);
            log.info("Getting comment for listing {}", listingId);
            return comments.stream().map(commentMapper::toCommentDto).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Getting comment error", e);
            return null;
        }
    }
}

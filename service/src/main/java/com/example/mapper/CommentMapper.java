package com.example.mapper;

import com.example.model.Comment;
import com.example.model.CommentDto;
import com.example.model.Listing;
import com.example.model.User;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment toComment(CommentDto dto) {
        if (dto == null) {
            return null;
        }
        return new Comment(
                dto.getId(),
                new User(dto.getUser()),
                new Listing(dto.getListing()),
                dto.getContent(),
                dto.getCreateAt()
        );
    }

    public CommentDto toCommentDto(Comment entity) {
        if (entity == null) {
            return null;
        }
        return new CommentDto(
                entity.getId(),
                entity.getUser().getId(),
                entity.getListing().getId(),
                entity.getContent(),
                entity.getCreatedAt()
        );
    }
}

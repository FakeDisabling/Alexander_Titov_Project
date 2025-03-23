package com.example.interfaces;

import com.example.model.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CommentService extends InterfaceService<CommentDto> {

    List<CommentDto> findByListingId(Long listingId);
}

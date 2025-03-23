package com.example.interfaces;

import com.example.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentDao extends JpaRepository<Comment, Long> {

    @Transactional
    @Query(value = "SELECT * FROM comments WHERE listing_id = :listingId", nativeQuery = true)
    List<Comment> findByListingId(@Param("listingId") Long listingId);
}

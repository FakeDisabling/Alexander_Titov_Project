package com.example.interfaces;

import com.example.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MessageDao extends JpaRepository<Message, Long> {

    @Transactional
    @Query(value = "SELECT * FROM messages " +
            "WHERE (sender_id = :user1Id AND receiver_id = :user2Id)" +
            "OR (sender_id = :user2Id AND receiver_id = :user1Id)", nativeQuery = true)
    List<Message> findMessageByUserId(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);
}

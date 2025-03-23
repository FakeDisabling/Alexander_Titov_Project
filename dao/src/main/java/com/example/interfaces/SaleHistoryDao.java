package com.example.interfaces;

import com.example.model.SaleHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SaleHistoryDao extends JpaRepository<SaleHistory, Long> {

    @Transactional
    @Query(value = "SELECT listing_id FROM sales_history WHERE seller_id = :sellerId", nativeQuery = true)
    List<Long> findBySellerId(@Param("sellerId") Long sellerId);
}

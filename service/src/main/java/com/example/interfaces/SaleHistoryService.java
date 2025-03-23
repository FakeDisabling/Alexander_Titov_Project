package com.example.interfaces;

import com.example.model.SaleHistoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SaleHistoryService extends InterfaceService<SaleHistoryDto> {

    List<Long> findBySellerId(Long sellerId);
}

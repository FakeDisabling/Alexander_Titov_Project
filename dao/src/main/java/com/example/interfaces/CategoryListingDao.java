package com.example.interfaces;

import com.example.model.CategoryListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryListingDao extends JpaRepository<CategoryListing, Long> {
}

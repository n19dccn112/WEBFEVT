package com.n19dccn112.repository;

import com.n19dccn112.model.entity.Image;
import com.n19dccn112.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query(value = "select * from image where product_id = ?1", nativeQuery = true)
    List<Image> findAllByProduct_ProductId(Long productId);
}

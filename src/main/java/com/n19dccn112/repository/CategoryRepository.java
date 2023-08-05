package com.n19dccn112.repository;

import com.n19dccn112.model.entity.Category;
import com.n19dccn112.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select * from product where product.category_id=?1", nativeQuery = true)
    List<Product> findAllProductByCategory(Long category_id);
}

package com.n19dccn112.repository;

import com.n19dccn112.model.entity.Order;
import com.n19dccn112.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "Select * from product where product_id = " +
            "(SELECT MAX(product_id) FROM product " +
            "WHERE  CONVERT(date, create_date) = CONVERT(date, GETDATE()) and name = ?1  and description = ?2)", nativeQuery = true)
    Optional<Product> findProductByName(String name, String description);
}

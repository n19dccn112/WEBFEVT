package com.n19dccn112.repository;

import com.n19dccn112.model.entity.Order;
import com.n19dccn112.model.entity.Rate;
import com.n19dccn112.model.key.RateId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, RateId> {
    @Query(value = "select * from rate where product_id = ?1", nativeQuery = true)
    List<Rate> findAllByRateIdProduct_ProductId(Long productId);
}

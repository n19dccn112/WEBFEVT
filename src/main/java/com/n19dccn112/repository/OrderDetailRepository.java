package com.n19dccn112.repository;

import com.n19dccn112.model.entity.Order;
import com.n19dccn112.model.entity.OrderDetail;
import com.n19dccn112.model.key.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    @Query(value = "Select * from order_detail where order_id = ?1", nativeQuery = true)
    List<OrderDetail> findAllByOrderOrderId(Long orderId);
}

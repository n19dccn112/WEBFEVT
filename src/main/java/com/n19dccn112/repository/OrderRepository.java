package com.n19dccn112.repository;

import com.n19dccn112.model.entity.Order;
import com.n19dccn112.model.entity.OrderDetail;
import com.n19dccn112.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "Select * from orders where user_id = ?1", nativeQuery = true)
    List<Order> findAllByUserUserId(Long userId);
    @Query(value = "Select * from orders where user_id = ?1 and status = ?2", nativeQuery = true)
    List<Order> findAllByUserUserIdAndOrderStatus(Long userId, String status);

    @Query(value = "Select * from orders where order_id = (SELECT MAX(order_id) FROM orders WHERE phone = ?1)", nativeQuery = true)
    Optional<Order> findOrderByPhone(String phone);
}

package com.n19dccn112.repository;

import com.n19dccn112.model.entity.EventProduct;
import com.n19dccn112.model.key.EventProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventProductRepository extends JpaRepository<EventProduct, EventProductId> {
    List<EventProduct> findAllByEventProductId_Event(Long eventId);
}

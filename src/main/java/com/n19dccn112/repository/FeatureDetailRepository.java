package com.n19dccn112.repository;

import com.n19dccn112.model.entity.FeatureDetail;
import com.n19dccn112.model.key.FeatureDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureDetailRepository extends JpaRepository<FeatureDetail, FeatureDetailId> {

    @Query(value = "Select * from feature_detail where product_id = ?1", nativeQuery = true)
    List<FeatureDetail> findAllByFeatureDetailsByProductId (Long productId);
}

package com.n19dccn112.repository;

import com.n19dccn112.model.entity.Feature;
import com.n19dccn112.model.entity.FeatureType;
import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureTypeRepository extends JpaRepository<FeatureType, Long> {
    @Query(value = "select * from feature_type where feature_type_id = (SELECT MAX(feature_type_id) FROM feature_type)", nativeQuery = true)
    FeatureType findByMaxId();
}

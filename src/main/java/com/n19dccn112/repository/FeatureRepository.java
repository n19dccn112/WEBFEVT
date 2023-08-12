package com.n19dccn112.repository;

import com.n19dccn112.model.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    @Query(value = "SELECT * From feature f where f.feature_id in " +
            "(SELECT fd.feature_id From feature_detail fd where fd.product_id=?1)", nativeQuery = true)
    List<Feature> findAllByProductId(Long productId);
    List<Feature> findAllByFeaturetype_FeatureTypeId(Long featureTypeId);
}

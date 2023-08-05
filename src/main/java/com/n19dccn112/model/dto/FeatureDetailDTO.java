package com.n19dccn112.model.dto;

import com.n19dccn112.model.entity.Feature;
import com.n19dccn112.model.entity.Product;
import com.n19dccn112.model.key.FeatureDetailId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ManyToOne;

@Getter
@Setter
public class FeatureDetailDTO {
    private Long productId;
    private Long featureId;
}

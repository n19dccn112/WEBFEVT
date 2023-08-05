package com.n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;

@Getter
@Setter
public class FeatureDTO {
    private Long featureId;
    @DecimalMin(value = "0")
    private int point;
    private String specific;
    private Long featureTypeId;
}

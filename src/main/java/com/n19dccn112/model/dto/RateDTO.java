package com.n19dccn112.model.dto;

import com.n19dccn112.model.entity.Product;
import com.n19dccn112.model.entity.User;
import com.n19dccn112.model.key.RateId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RateDTO {
    @NotNull
    private Long productId;
    @NotNull
    private Long userId;
    @NotNull
    private String comment;
    @DecimalMin(value = "1")
    @DecimalMax(value = "5")
    private Integer point;
}

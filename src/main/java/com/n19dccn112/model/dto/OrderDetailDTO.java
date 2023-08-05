package com.n19dccn112.model.dto;

import com.n19dccn112.model.entity.Order;
import com.n19dccn112.model.entity.Product;
import com.n19dccn112.model.key.OrderDetailId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;

@Getter
@Setter
public class OrderDetailDTO {
    private Long productId;
    private Long orderId;
    @DecimalMin(value = "1")
    private int amount;
}

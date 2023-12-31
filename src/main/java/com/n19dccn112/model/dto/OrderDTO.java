package com.n19dccn112.model.dto;

import com.n19dccn112.model.enumeration.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
public class OrderDTO {
    private Long orderId;
    private String address;
    @Size(min = 10, max = 10)
    private String phone;
    private String status;
    private Date time;
    private Long userId;
    private String name;
    private Map<Long, Integer> details;
}

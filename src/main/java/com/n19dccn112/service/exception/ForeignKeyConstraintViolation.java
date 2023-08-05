package com.n19dccn112.service.exception;

import com.n19dccn112.model.dto.EventProductDTO;
import com.n19dccn112.model.dto.FeatureDetailDTO;
import com.n19dccn112.model.dto.OrderDetailDTO;
import com.n19dccn112.model.dto.RateDTO;
import com.n19dccn112.model.key.EventProductId;
import com.n19dccn112.model.key.FeatureDetailId;
import com.n19dccn112.model.key.OrderDetailId;
import com.n19dccn112.model.key.RateId;

public class ForeignKeyConstraintViolation extends RuntimeException {
    public ForeignKeyConstraintViolation(Class<?> clazz, Long id) {
        super(clazz.getSimpleName() + " has foreign key constraint violation id = " + id);
    }
    public ForeignKeyConstraintViolation(Class<EventProductDTO> clazz, EventProductId eventProductId) {
        super(clazz.getSimpleName() + "Table Event and Product has foreign key constraint violation id = " + eventProductId);
    }

    public ForeignKeyConstraintViolation(Class<OrderDetailDTO> clazz, OrderDetailId orderDetailsId) {
        super(clazz.getSimpleName() + "Table order details has foreign key constraint violation id = " + orderDetailsId);
    }
    public ForeignKeyConstraintViolation(Class<RateDTO> clazz, RateId ratesId) {
        super(clazz.getSimpleName() + "Table rate has foreign key constraint violation id = " + ratesId);
    }
    public ForeignKeyConstraintViolation(Class<FeatureDetailDTO> clazz, FeatureDetailId featureDetailId) {
        super(clazz.getSimpleName() + "Table feature detailId has foreign key constraint violation id = " + featureDetailId);
    }
}

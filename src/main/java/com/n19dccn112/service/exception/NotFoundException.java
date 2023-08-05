package com.n19dccn112.service.exception;

import com.n19dccn112.model.key.EventProductId;
import com.n19dccn112.model.key.FeatureDetailId;
import com.n19dccn112.model.key.OrderDetailId;
import com.n19dccn112.model.key.RateId;

public class NotFoundException extends RuntimeException { // lỗi logic
    public NotFoundException(Class<?> clazz, Long id) {
        super(clazz.getSimpleName() + " has id = " + id + " not found!");
    }

    public NotFoundException(EventProductId eventProductId) {
        super("Event of Product has id = " + eventProductId + " not found!");
    }

    public NotFoundException(FeatureDetailId featureDetailsId) {
        super("featureDetails has id = " + featureDetailsId + " not found!");
    }

    public NotFoundException(OrderDetailId orderDetailsId) {
        super("orderDetails has id = " + orderDetailsId + " not found!");
    }

    public NotFoundException(RateId ratesId) {
        super("Rate has id = " + ratesId + " not found!");
    }
}
package com.n19dccn112.model.enumeration;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PREPARE,
    SUCCESS,
    SHIPPING,
    CANCELED
}
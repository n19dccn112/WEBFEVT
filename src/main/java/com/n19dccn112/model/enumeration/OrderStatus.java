package com.n19dccn112.model.enumeration;

import lombok.Getter;

@Getter
public enum OrderStatus {
    ON_CART,
    SELECT,
    ORDER_PLACED,
    ORDER_CONFIRMED,
    ORDER_PROCESSED,
    ORDER_SHIPPED,
    ORDER_OUT_FOR_DELIVERY,
    ORDER_DELIVERED,
    ORDER_CANCELLED
}
/**
public enum TrangThaiDonHang {
    DA_DAT_HANG,
    DA_XAC_NHAN,
    DA_XU_LY,
    DA_VAN_CHUYEN,
    DANG_GIAO_HANG,
    DA_GIAO_HANG,
    DA_HUY
}
 */

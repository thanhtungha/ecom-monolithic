package com.be.monolithic.service.impl;

import com.be.monolithic.dto.order.*;
import com.be.monolithic.service.IOrderService;
import org.springframework.http.ResponseEntity;

public class OrderServiceImpl implements IOrderService {
    @Override
    public ResponseEntity<?> create(OrRqCreateOrderArgs createOrderArgs) {
        return null;
    }

    @Override
    public ResponseEntity<?> update(OrRqUpdateOrderArgs updateOrderArgs) {
        return null;
    }

    @Override
    public ResponseEntity<?> cancel(OrRqCancelOrderArgs cancelOrderArgs) {
        return null;
    }

    @Override
    public ResponseEntity<?> getOrder(OrRqGetOrderArgs getOrderArgs) {
        return null;
    }

    @Override
    public ResponseEntity<?> checkout(OrRqCheckOutArgs checkOutArgs) {
        return null;
    }
}

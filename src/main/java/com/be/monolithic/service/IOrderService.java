package com.be.monolithic.service;

import com.be.monolithic.dto.order.*;
import org.springframework.http.ResponseEntity;

public interface IOrderService {
    ResponseEntity<?> create(OrRqCreateOrderArgs createOrderArgs);
    ResponseEntity<?> update(OrRqUpdateOrderArgs updateOrderArgs);
    ResponseEntity<?> cancel(OrRqCancelOrderArgs cancelOrderArgs);
    ResponseEntity<?> getOrder(OrRqGetOrderArgs getOrderArgs);
    ResponseEntity<?> checkout(OrRqCheckOutArgs checkOutArgs);

}

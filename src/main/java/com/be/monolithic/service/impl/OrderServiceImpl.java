package com.be.monolithic.service.impl;

import com.be.monolithic.dto.order.*;
import com.be.monolithic.model.UserInfo;
import com.be.monolithic.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
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

    @Override
    public boolean deleteUserData(UserInfo userInfo) {
        return false;
    }
}

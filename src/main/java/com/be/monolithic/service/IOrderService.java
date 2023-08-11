package com.be.monolithic.service;

import com.be.monolithic.model.Order;
import com.be.monolithic.model.OrderItem;
import com.be.monolithic.model.UserInfo;

import java.util.List;

public interface IOrderService extends IBaseService {
    Order create(UserInfo userInfo, List<OrderItem> orderItems);
    Order update(UserInfo userInfo, String orderId, List<OrderItem> orderItems);
    boolean cancel(UserInfo userInfo, String orderId);
    Order getOrder(UserInfo userInfo, String orderId);
}

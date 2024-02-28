package com.be.monolithic.service;

import com.be.monolithic.model_old.Order;
import com.be.monolithic.model_old.OrderItem;
import com.be.monolithic.model_old.User;

import java.util.List;

public interface IOrderService extends IBaseService {
    Order create(User userInfo, List<OrderItem> orderItems);
    Order update(User userInfo, String orderId, List<OrderItem> orderItems);
    boolean cancel(User userInfo, String orderId);
    Order getOrder(User userInfo, String orderId);
}

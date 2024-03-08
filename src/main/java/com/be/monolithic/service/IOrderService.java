package com.be.monolithic.service;

import com.be.monolithic.model.Order;
import com.be.monolithic.model.OrderItem;
import com.be.monolithic.model.User;

import java.util.List;

public interface IOrderService {
    Order create(User user);
    Order update(User user, String orderId, List<OrderItem> orderItems);
    void cancelBuyingOrder(User buyer, String orderId);
    Order getBuyingOrder(User buyer, String orderId);
}

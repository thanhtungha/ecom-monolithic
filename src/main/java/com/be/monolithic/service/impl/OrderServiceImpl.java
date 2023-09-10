package com.be.monolithic.service.impl;

import com.be.monolithic.exception.RestExceptions;
import com.be.monolithic.model.Order;
import com.be.monolithic.model.OrderItem;
import com.be.monolithic.model.User;
import com.be.monolithic.repository.OrderRepository;
import com.be.monolithic.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;

    @Override
    public Order create(User userInfo, List<OrderItem> orderItems) {
        Order order = new Order();
        order.setCreateDate(new Date());
        order.setUpdateDate(new Date());
        order.setOwner(userInfo);
        order.setOrderItems(orderItems);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order update(User userInfo, String orderId,
                        List<OrderItem> orderItems) {
        Optional<Order> storedModel =
                orderRepository.findByIdAndOwner(UUID.fromString(orderId),
                        userInfo);
        if (storedModel.isEmpty()) {
            throw new RestExceptions.NotFound("Order does not existed!");
        }

        Order order = storedModel.get();
        order.setUpdateDate(new Date());
        order.getOrderItems().clear();
        order.getOrderItems().addAll(orderItems);
        orderRepository.save(order);
        return order;
    }

    @Override
    public boolean cancel(User userInfo, String orderId) {
        Optional<Order> storedModel =
                orderRepository.findByIdAndOwner(UUID.fromString(orderId),
                        userInfo);
        if (storedModel.isPresent()) {
            orderRepository.delete(storedModel.get());
            return true;
        } else {
            throw new RestExceptions.NotFound("Order does not existed!");
        }
    }

    @Override
    public Order getOrder(User userInfo, String orderId) {
        Optional<Order> storedModel =
                orderRepository.findByIdAndOwner(UUID.fromString(orderId),
                        userInfo);
        if (storedModel.isEmpty()) {
            throw new RestExceptions.NotFound("Order does not existed!");
        }

        return storedModel.get();
    }

    @Override
    public boolean deleteUserData(User owner) {
        Optional<List<Order>> storedModel = orderRepository.findByOwner(owner);
        storedModel.ifPresent(orderRepository::deleteAll);
        return true;
    }
}

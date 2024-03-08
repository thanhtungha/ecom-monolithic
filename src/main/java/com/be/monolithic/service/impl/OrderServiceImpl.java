package com.be.monolithic.service.impl;

import com.be.monolithic.exception.RestExceptions;
import com.be.monolithic.model.Order;
import com.be.monolithic.model.OrderItem;
import com.be.monolithic.model.User;
import com.be.monolithic.repository.IOrderRepository;
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
    private final IOrderRepository orderRepository;

    @Override
    public Order create(User user) {
        Order order = new Order();
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());
        order.setBuyer(user);
        return orderRepository.save(order);
    }

    @Override
    public Order update(User user, String orderId, List<OrderItem> orderItems) {
        Optional<Order> optional = orderRepository.findByIdAndBuyer(
                UUID.fromString(orderId), user);
        if (optional.isPresent()) {
            Order order = optional.get();
            order.setUpdatedAt(new Date());
            orderItems.forEach(orderItem -> orderItem.setOrder(order));
            order.setItemList(orderItems);
            return orderRepository.save(order);
        } else {
            throw new RestExceptions.NotFound("Order does not existed!");
        }
    }

    @Override
    public void cancelBuyingOrder(User buyer, String orderId) {
        Optional<Order> optional = orderRepository.findByIdAndBuyer(
                UUID.fromString(orderId), buyer);
        optional.ifPresentOrElse(orderRepository::delete, () -> {
            throw new RestExceptions.NotFound("Order does not existed!");
        });
    }

    @Override
    public Order getBuyingOrder(User buyer, String orderId) {
        Optional<Order> storedModel = orderRepository.findByIdAndBuyer(
                UUID.fromString(orderId), buyer);
        if (storedModel.isEmpty()) {
            throw new RestExceptions.NotFound("Order does not existed!");
        }

        return storedModel.get();
    }
}

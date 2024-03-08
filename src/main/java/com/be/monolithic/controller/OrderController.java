package com.be.monolithic.controller;

import com.be.monolithic.dto.BaseResponse;
import com.be.monolithic.dto.order.*;
import com.be.monolithic.exception.BaseException;
import com.be.monolithic.exception.RestExceptions;
import com.be.monolithic.mappers.OrderMapper;
import com.be.monolithic.model.Order;
import com.be.monolithic.model.OrderItem;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.User;
import com.be.monolithic.service.IAuthService;
import com.be.monolithic.service.IOrderService;
import com.be.monolithic.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/order")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(
            OrderController.class);
    private final IOrderService orderService;
    private final IAuthService authService;
    private final IProductService productService;
    private final OrderMapper orderMapper;

    @GetMapping(path = "/greeting")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> greeting() {
        return ResponseEntity.ok(
                new BaseResponse("Hello! This is Order Service."));
    }

    @PostMapping(path = "/create-order")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createOrder(
            @RequestHeader("Authorization") String authorizationHeader, @RequestBody OrRqCreateOrderArgs orderArgs) {
        try {
            List<OrderItem> orderItems = new ArrayList<>();
            orderArgs.getProducts().forEach(productArgs -> {
                Product product = productService.getProduct(productArgs.getId());
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(productArgs.getQuantity());
                orderItems.add(orderItem);
            });
            User user = authService.getUser(authorizationHeader);
            Order order = orderService.create(user, orderItems);
            return new ResponseEntity<>(orderMapper.OrderToDTO(order),
                    HttpStatus.CREATED);
        } catch (BaseException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @PostMapping(path = "/update-order")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateOrder(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody OrRqUpdateOrderArgs orderArgs) {
        try {
            User user = authService.getUser(authorizationHeader);
            List<OrderItem> orderItems = new ArrayList<>();
            for (ProductArgs productArgs : orderArgs.getProducts()) {
                Product product = productService.getProduct(
                        productArgs.getId());
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(productArgs.getQuantity());
                orderItems.add(orderItem);
            }
            Order order = orderService.update(user, orderArgs.getId(),
                    orderItems);
            return new ResponseEntity<>(orderMapper.OrderToDTO(order),
                    HttpStatus.OK);
        } catch (BaseException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @PostMapping(path = "/cancelBuyingOrder-order")
    @ResponseStatus(HttpStatus.OK)
    public void cancelOrder(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody OrRqCancelOrderArgs orderArgs) {
        try {
            User userInfo = authService.getUser(authorizationHeader);
            orderService.cancelBuyingOrder(userInfo, orderArgs.getId());
        } catch (BaseException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @GetMapping(path = "/get-order")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getOrder(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody OrRqGetOrderArgs orderArgs) {
        try {
            User userInfo = authService.getUser(authorizationHeader);
            Order order = orderService.getBuyingOrder(userInfo,
                    orderArgs.getId());
            return new ResponseEntity<>(orderMapper.OrderToDTO(order),
                    HttpStatus.OK);
        } catch (BaseException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }
}

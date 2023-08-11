package com.be.monolithic.controller;

import com.be.monolithic.dto.BaseResponse;
import com.be.monolithic.dto.cart.CtRqProductArgs;
import com.be.monolithic.dto.inventory.IvRqRemoveProductArgs;
import com.be.monolithic.exception.BaseException;
import com.be.monolithic.exception.RestExceptions;
import com.be.monolithic.mappers.CartMapper;
import com.be.monolithic.model.Cart;
import com.be.monolithic.model.Inventory;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.UserInfo;
import com.be.monolithic.service.IAuthService;
import com.be.monolithic.service.ICartService;
import com.be.monolithic.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/cart")
public class CartController {
    private static final Logger logger =
            LoggerFactory.getLogger(CartController.class);
    private final ICartService cartService;
    private final IAuthService authService;
    private final IProductService productService;
    private final CartMapper cartMapper;

    @PostMapping(path = "/greeting")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> greeting() {
        return ResponseEntity.ok(new BaseResponse("Hello! This is Cart " +
                "Service."));
    }

    @PostMapping(path = "/add-product")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> addProduct(@RequestHeader("Authorization") String authorizationHeader, @Valid @RequestBody CtRqProductArgs productArgs) {
        try {
            UserInfo owner = authService.getUserInfo(authorizationHeader);
            Product product = productService.getProduct(productArgs.getId());
            Cart cart = cartService.addProduct(owner, product);
            return new ResponseEntity<>(cartMapper.CartToDTO(cart),
                    HttpStatus.OK);
        } catch (Exception ex) {
            if (ex instanceof BaseException) {
                throw ex;
            }
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @PostMapping(path = "/remove-product")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> removeProduct(@RequestHeader("Authorization") String authorizationHeader, @Valid @RequestBody CtRqProductArgs productArgs) {
        try {
            UserInfo owner = authService.getUserInfo(authorizationHeader);
            Product product = productService.getProduct(productArgs.getId());
            Cart cart = cartService.removeProduct(owner, product);
            return new ResponseEntity<>(cartMapper.CartToDTO(cart),
                    HttpStatus.OK);
        } catch (Exception ex) {
            if (ex instanceof BaseException) {
                throw ex;
            }
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @GetMapping(path = "/get-cart")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCart(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            UserInfo owner = authService.getUserInfo(authorizationHeader);
            Cart cart = cartService.getCart(owner);
            return new ResponseEntity<>(cartMapper.CartToDTO(cart),
                    HttpStatus.OK);
        } catch (Exception ex) {
            if (ex instanceof BaseException) {
                throw ex;
            }
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }
}

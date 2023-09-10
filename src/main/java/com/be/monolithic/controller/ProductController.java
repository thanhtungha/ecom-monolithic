package com.be.monolithic.controller;

import com.be.monolithic.dto.BaseResponse;
import com.be.monolithic.dto.product.*;
import com.be.monolithic.exception.BaseException;
import com.be.monolithic.exception.RestExceptions;
import com.be.monolithic.mappers.ProductMapper;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.User;
import com.be.monolithic.service.IAuthService;
import com.be.monolithic.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/product")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(
            ProductController.class);
    private final IProductService productService;
    private final IAuthService authService;
    private final ProductMapper productMapper;

    @GetMapping(path = "/greeting")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> greeting() {
        return ResponseEntity.ok(
                new BaseResponse("Hello! This is Product " + "Service."));
    }

    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody PdRqRegisterArgs registerArgs) {
        try {
            User userInfo = authService.getUserInfo(authorizationHeader);
            Product product = productService.register(userInfo, registerArgs);
            return new ResponseEntity<>(productMapper.ProductToDTO(product),
                    HttpStatus.CREATED);
        } catch (Exception ex) {
            if (ex instanceof BaseException) {
                throw ex;
            }
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @PostMapping(path = "/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> update(@RequestBody PdRqUpdateArgs updateArgs) {
        try {
            Product product = productService.update(updateArgs);
            return new ResponseEntity<>(productMapper.ProductToDTO(product),
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
    public void remove(@Valid @RequestBody PdRqProductArgs productArgs) {
        try {
            productService.remove(productArgs.getId());
        } catch (Exception ex) {
            if (ex instanceof BaseException) {
                throw ex;
            }
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @GetMapping(path = "/get-product")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getProduct(
            @RequestBody PdRqProductArgs productArgs) {
        try {
            if (productArgs != null) {
                Product product = productService.getProduct(
                        productArgs.getId());
                return new ResponseEntity<>(productMapper.ProductToDTO(product),
                        HttpStatus.OK);
            } else {
                List<Product> listProduct = productService.getProductList();
                if (listProduct.isEmpty()) {
                    return new ResponseEntity<>(new ProductListDTO(),
                            HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new ProductListDTO(
                            listProduct.stream()
                                    .map(productMapper::ProductToDTO)
                                    .toList()), HttpStatus.OK);
                }
            }
        } catch (Exception ex) {
            if (ex instanceof BaseException) {
                throw ex;
            }
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @PostMapping(path = "/add-review")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> addReview(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody PdRqAddReviewArgs reviewArgs) {
        try {
            User buyer = authService.getUserInfo(authorizationHeader);
            Product product = productService.addReview(buyer, reviewArgs);
            return new ResponseEntity<>(productMapper.ProductToDTO(product),
                    HttpStatus.OK);
        } catch (Exception ex) {
            if (ex instanceof BaseException) {
                throw ex;
            }
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }
}

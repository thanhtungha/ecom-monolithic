package com.be.monolithic.controller;

import com.be.monolithic.dto.BaseResponse;
import com.be.monolithic.dto.auth.AuRqLoginArgs;
import com.be.monolithic.dto.auth.AuRqUpdateArgs;
import com.be.monolithic.dto.product.PdRqAddReviewArgs;
import com.be.monolithic.dto.product.PdRqProductArgs;
import com.be.monolithic.dto.product.PdRqRegisterArgs;
import com.be.monolithic.dto.product.PdRqUpdateArgs;
import com.be.monolithic.exception.BaseException;
import com.be.monolithic.exception.RestExceptions;
import com.be.monolithic.mappers.ProductMapper;
import com.be.monolithic.model.ProductModel;
import com.be.monolithic.model.UserInfo;
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
@RequestMapping(path = "/api/product")
public class ProductController {
    private static final Logger logger =
            LoggerFactory.getLogger(ProductController.class);
    private final IProductService productService;
    private final ProductMapper productMapper;

    @PostMapping(path = "/greeting")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> greeting() {
        return ResponseEntity.ok(new BaseResponse("Hello! This is Product " + "Service."));
    }

    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@RequestHeader("Authorization") String authorizationHeader, @Valid @RequestBody PdRqRegisterArgs registerArgs) {
        try {
            ProductModel productModel =
                    productService.register(authorizationHeader, registerArgs);
            return new ResponseEntity<>(productMapper.ProductModelToResponse(productModel), HttpStatus.CREATED);
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
            ProductModel productModel = productService.update(updateArgs);
            return new ResponseEntity<>(productMapper.ProductModelToResponse(productModel), HttpStatus.OK);
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

    @PostMapping(path = "/get-product")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getProduct(@Valid @RequestBody PdRqProductArgs productArgs) {
        try {
            ProductModel productModel =
                    productService.getProduct(productArgs.getId());
            return new ResponseEntity<>(productMapper.ProductModelToResponse(productModel), HttpStatus.OK);
        } catch (Exception ex) {
            if (ex instanceof BaseException) {
                throw ex;
            }
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @PostMapping(path = "/add-review")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> addReview(@RequestBody PdRqAddReviewArgs reviewArgs) {
        try {
            ProductModel productModel = productService.addReview(reviewArgs);
            return new ResponseEntity<>(productMapper.ProductModelToResponse(productModel), HttpStatus.OK);
        } catch (Exception ex) {
            if (ex instanceof BaseException) {
                throw ex;
            }
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }
}

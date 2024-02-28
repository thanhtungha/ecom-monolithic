package com.be.monolithic.controller;

import com.be.monolithic.dto.BaseResponse;
import com.be.monolithic.dto.inventory.IvRqAddProductArgs;
import com.be.monolithic.dto.inventory.IvRqRemoveProductArgs;
import com.be.monolithic.dto.inventory.IvRqUpdateProductArgs;
import com.be.monolithic.dto.product.PdRqRegisterArgs;
import com.be.monolithic.dto.product.PdRqUpdateArgs;
import com.be.monolithic.exception.BaseException;
import com.be.monolithic.exception.RestExceptions;
import com.be.monolithic.mappers.InventoryMapper;
import com.be.monolithic.model_old.Inventory;
import com.be.monolithic.model_old.Product;
import com.be.monolithic.model_old.User;
import com.be.monolithic.service.IAuthService;
import com.be.monolithic.service.IInventoryService;
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
@RequestMapping(path = "/api/inventory")
public class InventoryController {
    private static final Logger logger =
            LoggerFactory.getLogger(InventoryController.class);
    private final IInventoryService inventoryService;
    private final IAuthService authService;
    private final IProductService productService;
    private final InventoryMapper inventoryMapper;

    @GetMapping(path = "/greeting")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> greeting() {
        return ResponseEntity.ok(new BaseResponse("Hello! This is Inventory " + "Service."));
    }

    @PostMapping(path = "/add-product")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addProduct(@RequestHeader("Authorization") String authorizationHeader, @Valid @RequestBody IvRqAddProductArgs addProductArgs) {
        try {
            User seller = authService.getUserInfo(authorizationHeader);
            PdRqRegisterArgs registerArgs =
                    inventoryMapper.GenerateRegisterArgs(addProductArgs);
            Product product = productService.register(seller, registerArgs);
            Inventory inventory = inventoryService.addProduct(seller, product);
            return new ResponseEntity<>(inventoryMapper.InventoryToDTO(inventory), HttpStatus.CREATED);
        } catch (Exception ex) {
            if (ex instanceof BaseException) {
                throw ex;
            }
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @PostMapping(path = "/remove-product")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> removeProduct(@RequestHeader("Authorization") String authorizationHeader, @Valid @RequestBody IvRqRemoveProductArgs removeProductArgs) {
        try {
            User seller = authService.getUserInfo(authorizationHeader);
            Product product =
                    productService.getProduct(removeProductArgs.getId());
            Inventory inventory = inventoryService.removeProduct(seller,
                    product);
            return new ResponseEntity<>(inventoryMapper.InventoryToDTO(inventory), HttpStatus.OK);
        } catch (Exception ex) {
            if (ex instanceof BaseException) {
                throw ex;
            }
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @PostMapping(path = "/update-product")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateProduct(@RequestHeader("Authorization") String authorizationHeader, @Valid @RequestBody IvRqUpdateProductArgs updateProductArgs) {
        try {
            User seller = authService.getUserInfo(authorizationHeader);
            PdRqUpdateArgs registerArgs =
                    inventoryMapper.GenerateUpdateArgs(updateProductArgs);
            productService.update(registerArgs);
            Inventory inventory = inventoryService.getInventory(seller);
            return new ResponseEntity<>(inventoryMapper.InventoryToDTO(inventory), HttpStatus.OK);
        } catch (Exception ex) {
            if (ex instanceof BaseException) {
                throw ex;
            }
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @GetMapping(path = "/get-inventory")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getInventory(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            User seller = authService.getUserInfo(authorizationHeader);
            Inventory inventory = inventoryService.getInventory(seller);
            return new ResponseEntity<>(inventoryMapper.InventoryToDTO(inventory), HttpStatus.OK);
        } catch (Exception ex) {
            if (ex instanceof BaseException) {
                throw ex;
            }
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }
}

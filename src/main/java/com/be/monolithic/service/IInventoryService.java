package com.be.monolithic.service;

import com.be.monolithic.dto.inventory.IvRqAddProductArgs;
import com.be.monolithic.dto.inventory.IvRqGetInventoryArgs;
import com.be.monolithic.dto.inventory.IvRqRemoveProductArgs;
import com.be.monolithic.dto.inventory.IvRqUpdateQuantityArgs;
import org.springframework.http.ResponseEntity;

public interface IInventoryService {
    ResponseEntity<?> addProduct(IvRqAddProductArgs addProductArgs);
    ResponseEntity<?> removeProduct(IvRqRemoveProductArgs removeProductArgs);
    ResponseEntity<?> updateQuantity(IvRqUpdateQuantityArgs updateQuantityArgs);
    ResponseEntity<?> getInventory(IvRqGetInventoryArgs getInventoryArgs);
}

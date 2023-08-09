package com.be.monolithic.service.impl;

import com.be.monolithic.dto.inventory.IvRqAddProductArgs;
import com.be.monolithic.dto.inventory.IvRqGetInventoryArgs;
import com.be.monolithic.dto.inventory.IvRqRemoveProductArgs;
import com.be.monolithic.dto.inventory.IvRqUpdateQuantityArgs;
import com.be.monolithic.service.IInventoryService;
import org.springframework.http.ResponseEntity;

public class InventoryServiceImpl implements IInventoryService {
    @Override
    public ResponseEntity<?> addProduct(IvRqAddProductArgs addProductArgs) {
        return null;
    }

    @Override
    public ResponseEntity<?> removeProduct(IvRqRemoveProductArgs removeProductArgs) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateQuantity(IvRqUpdateQuantityArgs updateQuantityArgs) {
        return null;
    }

    @Override
    public ResponseEntity<?> getInventory(IvRqGetInventoryArgs getInventoryArgs) {
        return null;
    }
}

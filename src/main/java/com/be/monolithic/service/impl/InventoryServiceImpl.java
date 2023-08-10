package com.be.monolithic.service.impl;

import com.be.monolithic.dto.inventory.IvRqAddProductArgs;
import com.be.monolithic.dto.inventory.IvRqGetInventoryArgs;
import com.be.monolithic.dto.inventory.IvRqRemoveProductArgs;
import com.be.monolithic.dto.inventory.IvRqUpdateQuantityArgs;
import com.be.monolithic.exception.RestExceptions;
import com.be.monolithic.model.Inventory;
import com.be.monolithic.model.ProductModel;
import com.be.monolithic.model.UserInfo;
import com.be.monolithic.repository.InventoryRepository;
import com.be.monolithic.service.IInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class InventoryServiceImpl implements IInventoryService {
    private final InventoryRepository inventoryRepository;

    @Override
    public void saveInventoryToDB(Inventory inventory) {
        inventoryRepository.save(inventory);
    }

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

    @Override
    public boolean deleteUserData(UserInfo seller) {
        Optional<Inventory> storedModel =
                inventoryRepository.findBySeller(seller);
        storedModel.ifPresent(inventoryRepository::delete);
        return true;
    }
}

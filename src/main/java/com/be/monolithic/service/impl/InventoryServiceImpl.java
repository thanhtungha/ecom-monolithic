package com.be.monolithic.service.impl;

import com.be.monolithic.exception.RestExceptions;
import com.be.monolithic.model.Inventory;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.User;
import com.be.monolithic.repository.InventoryRepository;
import com.be.monolithic.service.IInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class InventoryServiceImpl implements IInventoryService {
    private final InventoryRepository inventoryRepository;

    @Override
    public void createInventory(User userInfo) {
        Optional<Inventory> created =
                inventoryRepository.findByOwner(userInfo);
        if (created.isEmpty()) {
            Inventory inventory = new Inventory();
            inventory.setOwner(userInfo);
            inventory.setCreateDate(new Date());
            inventory.setUpdateDate(new Date());
            inventoryRepository.save(inventory);
        }
    }

    @Override
    public Inventory addProduct(User userInfo, Product product) {
        Optional<Inventory> storedModel =
                inventoryRepository.findByOwner(userInfo);
        if (storedModel.isEmpty()) {
            throw new RestExceptions.InternalServerError("Can not find " +
                    "user's inventory!");
        }
        Inventory dbInventory = storedModel.get();
        dbInventory.getProducts().add(product);
        inventoryRepository.save(dbInventory);
        return dbInventory;
    }

    @Override
    public Inventory removeProduct(User userInfo,
                                   Product product) {
        Optional<Inventory> storedModel =
                inventoryRepository.findByOwner(userInfo);
        if (storedModel.isEmpty()) {
            throw new RestExceptions.InternalServerError("Can not find " +
                    "user's inventory!");
        }
        Inventory dbInventory = storedModel.get();
        Product dbProduct = null;
        for(Product prd : dbInventory.getProducts()) {
            if(prd.getId().equals(product.getId())) {
                dbProduct = prd;
                break;
            }
        }
        if(dbProduct != null) {
            dbInventory.getProducts().remove(dbProduct);
            inventoryRepository.save(dbInventory);
        }
        return dbInventory;
    }

    @Override
    public Inventory getInventory(User userInfo) {
        Optional<Inventory> storedModel =
                inventoryRepository.findByOwner(userInfo);
        if (storedModel.isEmpty()) {
            throw new RestExceptions.InternalServerError("Can not find " +
                    "user's inventory!");
        }
        return storedModel.get();
    }

    @Override
    public boolean deleteUserData(User seller) {
        Optional<Inventory> storedModel =
                inventoryRepository.findByOwner(seller);
        storedModel.ifPresent(inventoryRepository::delete);
        return true;
    }
}

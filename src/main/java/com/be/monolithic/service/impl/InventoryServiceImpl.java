package com.be.monolithic.service.impl;

import com.be.monolithic.exception.RestExceptions;
import com.be.monolithic.mappers.ProductMapper;
import com.be.monolithic.model.Inventory;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.UserInfo;
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
    private final ProductMapper productMapper;

    @Override
    public void createInventory(UserInfo userInfo) {
        Optional<Inventory> created =
                inventoryRepository.findBySeller(userInfo);
        if (created.isEmpty()) {
            Inventory inventory = new Inventory();
            inventory.setSeller(userInfo);
            inventory.setCreateDate(new Date());
            inventory.setUpdateDate(new Date());
            inventoryRepository.save(inventory);
        }
    }

    @Override
    public Inventory addProduct(UserInfo userInfo, Product product) {
        Optional<Inventory> storedModel =
                inventoryRepository.findBySeller(userInfo);
        if (storedModel.isEmpty()) {
            throw new RestExceptions.InternalServerError("Can not find " +
                    "user's inventory!");
        }
        Inventory dbInventory = storedModel.get();
        product.setInventory(dbInventory);
        dbInventory.getProducts().add(product);
        inventoryRepository.save(dbInventory);
        return dbInventory;
    }

    @Override
    public Inventory removeProduct(UserInfo userInfo,
                                   Product product) {
        Optional<Inventory> storedModel =
                inventoryRepository.findBySeller(userInfo);
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
    public Inventory getInventory(UserInfo userInfo) {
        Optional<Inventory> storedModel =
                inventoryRepository.findBySeller(userInfo);
        if (storedModel.isEmpty()) {
            throw new RestExceptions.InternalServerError("Can not find " +
                    "user's inventory!");
        }
        return storedModel.get();
    }

    @Override
    public boolean deleteUserData(UserInfo seller) {
        Optional<Inventory> storedModel =
                inventoryRepository.findBySeller(seller);
        storedModel.ifPresent(inventoryRepository::delete);
        return true;
    }
}

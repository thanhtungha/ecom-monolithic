package com.be.monolithic.service;

import com.be.monolithic.model.Inventory;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.User;

public interface IInventoryService extends IBaseService {
    void createInventory(User userInfo);
    Inventory addProduct(User userInfo, Product product);
    Inventory removeProduct(User userInfo, Product product);
    Inventory getInventory(User userInfo);
}

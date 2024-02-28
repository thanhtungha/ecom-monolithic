package com.be.monolithic.service;

import com.be.monolithic.model_old.Inventory;
import com.be.monolithic.model_old.Product;
import com.be.monolithic.model_old.User;

public interface IInventoryService extends IBaseService {
    void createInventory(User userInfo);
    Inventory addProduct(User userInfo, Product product);
    Inventory removeProduct(User userInfo, Product product);
    Inventory getInventory(User userInfo);
}

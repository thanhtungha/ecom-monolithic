package com.be.monolithic.service;

import com.be.monolithic.model.Inventory;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.UserInfo;

public interface IInventoryService extends IBaseService {
    void createInventory(UserInfo userInfo);
    Inventory addProduct(UserInfo userInfo, Product product);
    Inventory removeProduct(UserInfo userInfo, Product product);
    Inventory getInventory(UserInfo userInfo);
}

package com.be.monolithic.service.impl;

import com.be.monolithic.AbstractContainerBaseTest;
import com.be.monolithic.model_old.Inventory;
import com.be.monolithic.model_old.Product;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InventoryServiceImplTest extends AbstractContainerBaseTest {
    @BeforeEach
    void setUp() {
        if (userInfo == null) {
            registerTestUser();
        }
        if (testProductInventory == null) {
            registerTestProduct();
        }
    }

    @Test
    @Order(0)
    void addProduct() {
        Inventory inventory = inventoryService.addProduct(userInfo, testProductInventory);
        Optional<Inventory> createdInventory = inventoryRepository.findByOwner(userInfo);
        if (createdInventory.isPresent()) {
            Inventory dbInventory = createdInventory.get();
            List<Product> productList = dbInventory.getProducts();
            assertEquals(testProductInventory.getId(), productList.get(0).getId());
            assertEquals(testProductInventory.getName(), productList.get(0).getName());

            productList = inventory.getProducts();
            assertEquals(testProductInventory.getId(), productList.get(0).getId());
            assertEquals(testProductInventory.getName(), productList.get(0).getName());
        } else {
            fail("test case failed!");
        }
    }

    @Test
    @Order(2)
    void removeProduct() {
        Inventory inventory = inventoryService.removeProduct(userInfo, testProductInventory);
        Optional<Inventory> createdInventory = inventoryRepository.findByOwner(userInfo);
        if (createdInventory.isPresent()) {
            Inventory dbInventory = createdInventory.get();
            assertTrue(dbInventory.getProducts().isEmpty());
            assertTrue(inventory.getProducts().isEmpty());
        } else {
            fail("test case failed!");
        }
    }

    @Test
    @Order(1)
    void getInventory() {
        Inventory inventory = inventoryService.getInventory(userInfo);
        Optional<Inventory> createdInventory = inventoryRepository.findByOwner(userInfo);
        if (createdInventory.isPresent()) {
            Inventory dbInventory = createdInventory.get();
            List<Product> productList = dbInventory.getProducts();
            assertEquals(testProductInventory.getId(), productList.get(0).getId());
            assertEquals(testProductInventory.getName(), productList.get(0).getName());

            productList = inventory.getProducts();
            assertEquals(testProductInventory.getId(), productList.get(0).getId());
            assertEquals(testProductInventory.getName(), productList.get(0).getName());
        } else {
            fail("test case failed!");
        }
    }
}
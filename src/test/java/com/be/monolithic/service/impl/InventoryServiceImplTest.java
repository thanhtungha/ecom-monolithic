package com.be.monolithic.service.impl;

import com.be.monolithic.dto.auth.AuRqLoginArgs;
import com.be.monolithic.dto.auth.AuRqRegisterArgs;
import com.be.monolithic.dto.product.PdRqRegisterArgs;
import com.be.monolithic.model.Inventory;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.UserInfo;
import com.be.monolithic.repository.InventoryRepository;
import com.be.monolithic.service.IAuthService;
import com.be.monolithic.service.IInventoryService;
import com.be.monolithic.service.IProductService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InventoryServiceImplTest {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private IInventoryService inventoryService;
    @Autowired
    private IProductService productService;
    private static Product product;
    @Autowired
    private IAuthService authService;
    private static UserInfo user;

    @BeforeEach
    void setUp() {
        if (user == null) {
            AuRqRegisterArgs registerArgs = new AuRqRegisterArgs("userName",
                    "userPassword", "0123456789");
            authService.register(registerArgs);
            AuRqLoginArgs loginArgs = new AuRqLoginArgs("userName",
                    "userPassword");
            user = authService.login(loginArgs);
            inventoryService.createInventory(user);
        }
        if (product == null) {
            PdRqRegisterArgs registerArgs = new PdRqRegisterArgs("productName"
                    , 300);
            product = productService.register(user, registerArgs);
        }
    }

    @Test
    @Order(0)
    void addProduct() {
        Inventory inventory = inventoryService.addProduct(user, product);
        Optional<Inventory> createdInventory = inventoryRepository.findByOwner(user);
        if (createdInventory.isPresent()) {
            Inventory dbInventory = createdInventory.get();
            List<Product> productList = dbInventory.getProducts();
            assertEquals(product.getId(), productList.get(0).getId());
            assertEquals(product.getName(), productList.get(0).getName());

            productList = inventory.getProducts();
            assertEquals(product.getId(), productList.get(0).getId());
            assertEquals(product.getName(), productList.get(0).getName());
        } else {
            fail("test case failed!");
        }
    }

    @Test
    @Order(2)
    void removeProduct() {
        Inventory inventory = inventoryService.removeProduct(user , product);
        Optional<Inventory> createdInventory = inventoryRepository.findByOwner(user);
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
        Inventory inventory = inventoryService.getInventory(user);
        Optional<Inventory> createdInventory = inventoryRepository.findByOwner(user);
        if (createdInventory.isPresent()) {
            Inventory dbInventory = createdInventory.get();
            List<Product> productList = dbInventory.getProducts();
            assertEquals(product.getId(), productList.get(0).getId());
            assertEquals(product.getName(), productList.get(0).getName());

            productList = inventory.getProducts();
            assertEquals(product.getId(), productList.get(0).getId());
            assertEquals(product.getName(), productList.get(0).getName());
        } else {
            fail("test case failed!");
        }
    }
}
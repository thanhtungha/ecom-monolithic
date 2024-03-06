package com.be.monolithic;

import com.be.monolithic.dto.auth.AuRqLoginArgs;
import com.be.monolithic.dto.auth.AuRqRegisterArgs;
import com.be.monolithic.dto.product.PdRqRegisterArgs;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.User;
import com.be.monolithic.repository.*;
import com.be.monolithic.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractContainerBaseTest {
    public static final MySQLContainer MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer("mysql:latest");
        MY_SQL_CONTAINER.start();
    }

    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public ObjectMapper objectMapper;

    @Autowired
    public IProductRepository productRepository;
    @Autowired
    public IAuthRepository authRepository;
    @Autowired
    public ICartRepository cartRepository;
    @Autowired
    public IOrderRepository orderRepository;

    @Autowired
    public IAuthService authService;
    @Autowired
    public ICartService cartService;
    @Autowired
    public IProductService productService;
    @Autowired
    public IOrderService orderService;

    public static User seller;
    public static User buyer;
    public static Product testProduct1;
    public static Product testProduct2;

    public void registerTestUser() {
        seller = new User();
        seller.setUserName("seller");
        seller.setUserPassword("sellerPassword");
        seller.setPhoneNumber("0123456789");
        seller = processCreateUserData(seller);

        buyer = new User();
        buyer.setUserName("buyer");
        buyer.setUserPassword("buyerPassword");
        buyer.setPhoneNumber("9876543210");
        buyer = processCreateUserData(buyer);
    }

    public User processCreateUserData(User user) {
        AuRqRegisterArgs registerArgs = new AuRqRegisterArgs(user.getUserName(),
                user.getUserPassword(), user.getPhoneNumber());
        authService.register(registerArgs);

        AuRqLoginArgs loginArgs = new AuRqLoginArgs(user.getUserName(),
                user.getUserPassword());
        user = authService.login(loginArgs);

        cartService.createCart(user);
        return user;
    }

    public void registerTestProduct() {
        testProduct1 = productService.register(seller,
                new PdRqRegisterArgs("testProduct1", 300));
        testProduct2 = productService.register(seller,
                new PdRqRegisterArgs("testProduct2", 200));
    }
}

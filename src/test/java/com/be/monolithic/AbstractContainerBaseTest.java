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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;

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
    public ProductRepository productRepository;
    @Autowired
    public AuthRepository authRepository;
    @Autowired
    public CartRepository cartRepository;
    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public IAuthService authService;
    @Autowired
    public ICartService cartService;
    @Autowired
    public IProductService productService;
    @Autowired
    public IOrderService orderService;

    public static User userInfo;
    public static String authorizationHeader;

    public static String productId;
    public static Product testProduct1;
    public static Product testProduct2;
    public static Product testProductInventory;
    public User getUserInfo() {
        Optional<User> createdUser = authRepository.findByUserName(
                "userName");
        if (createdUser.isEmpty()) {
            fail("test case failed!");
        }
        return createdUser.get();
    }

    public void registerTestUser() {
        AuRqRegisterArgs registerArgs = new AuRqRegisterArgs("testUser",
                "userPassword", "0123456789");
        try {
            authService.register(registerArgs);
        } catch (Exception e) {
            //do nothing
        }
        AuRqLoginArgs loginArgs = new AuRqLoginArgs("testUser", "userPassword");
        userInfo = authService.login(loginArgs);
        cartService.createCart(userInfo);
        authorizationHeader = "Bearer " + userInfo.getAccessToken();
    }

    public void registerTestProduct() {
        PdRqRegisterArgs registerArgs = new PdRqRegisterArgs("testProduct1", 300);
        testProduct1 = productService.register(userInfo, registerArgs);
        registerArgs = new PdRqRegisterArgs("testProduct2", 200);
        testProduct2 = productService.register(userInfo, registerArgs);
        registerArgs = new PdRqRegisterArgs("testProduct3", 300);
        testProductInventory = productService.register(userInfo, registerArgs);
    }
}

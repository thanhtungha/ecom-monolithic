package com.be.monolithic.service;

import com.be.monolithic.dto.cart.CtRqAddProductArgs;
import com.be.monolithic.dto.cart.CtRqGetCartArgs;
import com.be.monolithic.dto.cart.CtRqRemoveProductArgs;
import com.be.monolithic.model.UserInfo;
import org.springframework.http.ResponseEntity;

public interface ICartService extends IBaseService {
    void createCart(UserInfo buyer);
    ResponseEntity<?> addProduct(CtRqAddProductArgs addProductArgs);

    ResponseEntity<?> removeProduct(CtRqRemoveProductArgs removeProductArgs);

    ResponseEntity<?> getCart(CtRqGetCartArgs getCartArgs);
}

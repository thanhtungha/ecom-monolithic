package com.be.monolithic.service.impl;

import com.be.monolithic.dto.cart.CtRqAddProductArgs;
import com.be.monolithic.dto.cart.CtRqGetCartArgs;
import com.be.monolithic.dto.cart.CtRqRemoveProductArgs;
import com.be.monolithic.model.UserInfo;
import com.be.monolithic.service.ICartService;
import org.springframework.http.ResponseEntity;

public class CartServiceImpl implements ICartService {
    @Override
    public ResponseEntity<?> addProduct(CtRqAddProductArgs addProductArgs) {
        return null;
    }

    @Override
    public ResponseEntity<?> removeProduct(CtRqRemoveProductArgs removeProductArgs) {
        return null;
    }

    @Override
    public ResponseEntity<?> getCart(CtRqGetCartArgs getCartArgs) {
        return null;
    }

    @Override
    public boolean deleteUserData(UserInfo userInfo) {
        return false;
    }
}

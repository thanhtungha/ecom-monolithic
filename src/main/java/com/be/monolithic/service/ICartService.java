package com.be.monolithic.service;

import com.be.monolithic.dto.cart.CtRqAddProductArgs;
import com.be.monolithic.dto.cart.CtRqGetCartArgs;
import com.be.monolithic.dto.cart.CtRqRemoveProductArgs;
import org.springframework.http.ResponseEntity;

public interface ICartService extends IBaseService {
    ResponseEntity<?> addProduct(CtRqAddProductArgs addProductArgs);

    ResponseEntity<?> removeProduct(CtRqRemoveProductArgs removeProductArgs);

    ResponseEntity<?> getCart(CtRqGetCartArgs getCartArgs);
}

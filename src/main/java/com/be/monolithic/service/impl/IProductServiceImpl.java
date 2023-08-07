package com.be.monolithic.service.impl;

import com.be.monolithic.dto.product.*;
import com.be.monolithic.service.IProductService;
import org.springframework.http.ResponseEntity;

public class IProductServiceImpl implements IProductService {
    @Override
    public ResponseEntity<?> register(PdRqRegisterArgs registerArgs) {
        return null;
    }

    @Override
    public ResponseEntity<?> update(PdRqUpdateArgs updateArgs) {
        return null;
    }

    @Override
    public ResponseEntity<?> remove(PdRqRemoveArgs removeArgs) {
        return null;
    }

    @Override
    public ResponseEntity<?> getProduct(PdRqGetProductArgs getProductArgs) {
        return null;
    }

    @Override
    public ResponseEntity<?> rate(PdRqRateArgs rateArgs) {
        return null;
    }

    @Override
    public ResponseEntity<?> addReview(PdRqAddReviewArgs addReviewArgs) {
        return null;
    }
}

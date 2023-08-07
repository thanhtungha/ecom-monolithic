package com.be.monolithic.service;

import com.be.monolithic.dto.product.*;
import org.springframework.http.ResponseEntity;

public interface IProductService {
    ResponseEntity<?> register(PdRqRegisterArgs registerArgs);
    ResponseEntity<?> update(PdRqUpdateArgs updateArgs);
    ResponseEntity<?> remove(PdRqRemoveArgs removeArgs);
    ResponseEntity<?> getProduct(PdRqGetProductArgs getProductArgs);
    ResponseEntity<?> rate(PdRqRateArgs rateArgs);
    ResponseEntity<?> addReview(PdRqAddReviewArgs addReviewArgs);

}

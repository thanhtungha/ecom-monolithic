package com.be.monolithic.controller;

import com.be.monolithic.dto.auth.AuRqRegisterArgs;
import com.be.monolithic.exception.RestExceptions;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/product")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping(path = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody AuRqRegisterArgs registerArgs) {
        throw new RestExceptions.NotImplemented();
    }
}

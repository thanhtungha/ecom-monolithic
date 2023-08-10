package com.be.monolithic.service;

import com.be.monolithic.dto.product.PdRqAddReviewArgs;
import com.be.monolithic.dto.product.PdRqRegisterArgs;
import com.be.monolithic.dto.product.PdRqUpdateArgs;
import com.be.monolithic.model.ProductModel;
import com.be.monolithic.model.UserInfo;

public interface IBaseService {
    boolean deleteUserData(UserInfo userInfo);
}

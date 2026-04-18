package com.classicbusinessmodel_schema.backend.module.product.service;

import com.classicbusinessmodel_schema.backend.module.product.dto.request.OrderDetailRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.OrderDetailResponse;
import java.util.List;

public interface OrderDetailService {

    OrderDetailResponse addItem(OrderDetailRequest request);

    List<OrderDetailResponse> getItemsByOrder(Integer orderNumber);

    OrderDetailResponse updateItem(Integer orderNumber, String productCode, OrderDetailRequest request);

    void deleteItem(Integer orderNumber, String productCode);
}
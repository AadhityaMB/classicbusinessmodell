package com.classicbusinessmodel_schema.backend.module.product.service;

import com.classicbusinessmodel_schema.backend.module.product.dto.request.OrderDetailRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.OrderDetailResponse;
import org.springframework.data.domain.Page;
import java.util.List;

public interface OrderDetailService {

    OrderDetailResponse addItem(OrderDetailRequest request);

    OrderDetailResponse getItem(Integer orderNumber, String productCode);

    Page<OrderDetailResponse> getItemsByOrder(Integer orderNumber, int page, int size, String sortBy, String direction);

    OrderDetailResponse updateItem(Integer orderNumber, String productCode, OrderDetailRequest request);

    void deleteItem(Integer orderNumber, String productCode);
}
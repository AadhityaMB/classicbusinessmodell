package com.classicbusinessmodel_schema.backend.module.product.service;

import com.classicbusinessmodel_schema.backend.module.product.dto.request.OrderDetailRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.OrderDetailResponse;
import org.springframework.data.domain.Page;
import java.util.List;

// Service interface for handling order item operations
public interface OrderDetailService {

    // Add a new item to an order
    OrderDetailResponse addItem(OrderDetailRequest request);

    // Fetch a specific item from an order
    OrderDetailResponse getItem(Integer orderNumber, String productCode);

    // Fetch paginated items for a given order
    Page<OrderDetailResponse> getItemsByOrder(Integer orderNumber, int page, int size, String sortBy, String direction);

    // Update an existing order item
    OrderDetailResponse updateItem(Integer orderNumber, String productCode, OrderDetailRequest request);

    // Remove an item from an order
    void deleteItem(Integer orderNumber, String productCode);
}
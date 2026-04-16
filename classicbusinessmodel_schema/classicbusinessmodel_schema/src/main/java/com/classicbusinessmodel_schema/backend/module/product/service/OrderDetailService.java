package com.classicbusinessmodel_schema.backend.module.product.service;

import com.classicbusinessmodel_schema.backend.entity.OrderDetails;
import java.util.List;

public interface OrderDetailService {

    // ADD ITEM
    OrderDetails addItemToOrder(OrderDetails orderDetail);

    List<OrderDetails> getItemsByOrder(Integer orderNumber);

    // UPDATE ITEM
    OrderDetails updateItem(Integer orderNumber, String productCode, OrderDetails updated);

    void deleteItem(Integer orderNumber, String productCode);
}

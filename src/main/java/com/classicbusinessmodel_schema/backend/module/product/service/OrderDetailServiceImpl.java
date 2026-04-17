package com.classicbusinessmodel_schema.backend.module.product.service;

import com.classicbusinessmodel_schema.backend.entity.OrderDetails;
import com.classicbusinessmodel_schema.backend.entity.OrderDetailsId;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.product.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository repository;

    public OrderDetailServiceImpl(OrderDetailRepository repository) {
        this.repository = repository;
    }

    // ADD ITEM
    @Override
    public OrderDetails addItemToOrder(OrderDetails orderDetail) {
        return repository.save(orderDetail);
    }

    // GET ITEMS BY ORDER
    @Override
    public List<OrderDetails> getItemsByOrder(Integer orderNumber) {
        return repository.findByOrderOrderNumber(orderNumber);
    }

    // UPDATE ITEM
    @Override
    public OrderDetails updateItem(Integer orderNumber, String productCode, OrderDetails updated) {

        OrderDetailsId id = new OrderDetailsId(orderNumber, productCode);

        OrderDetails existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));

        existing.setQuantityOrdered(updated.getQuantityOrdered());
        existing.setPriceEach(updated.getPriceEach());

        return repository.save(existing);
    }

    // DELETE ITEM
    @Override
    public void deleteItem(Integer orderNumber, String productCode) {

        OrderDetailsId id = new OrderDetailsId(orderNumber, productCode);

        OrderDetails existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));

        repository.delete(existing);
    }
}

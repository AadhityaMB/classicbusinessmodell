package com.classicbusinessmodel_schema.backend.module.product.service;

import com.classicbusinessmodel_schema.backend.entity.OrderDetails;
import com.classicbusinessmodel_schema.backend.entity.OrderDetailsId;
import com.classicbusinessmodel_schema.backend.entity.Orders;
import com.classicbusinessmodel_schema.backend.entity.Product;
import com.classicbusinessmodel_schema.backend.exception.*;
import com.classicbusinessmodel_schema.backend.module.product.dto.request.OrderDetailRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.OrderDetailResponse;
import com.classicbusinessmodel_schema.backend.module.product.repository.OrderDetailRepository;
import com.classicbusinessmodel_schema.backend.module.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository repository;
    private final ProductRepository productRepository;

    // Manual constructor injection
    public OrderDetailServiceImpl(OrderDetailRepository repository,
                                  ProductRepository productRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
    }

    @Override
    public OrderDetailResponse addItem(OrderDetailRequest request) {

        if (request.getOrderNumber() == null || request.getProductCode() == null) {
            throw new BadRequestException("Order number and product code are required");
        }

        if (request.getQuantityOrdered() == null || request.getQuantityOrdered() <= 0) {
            throw new InvalidDataException("Quantity must be greater than 0");
        }

        if (request.getPriceEach() == null || request.getPriceEach().doubleValue() <= 0) {
            throw new InvalidDataException("Price must be greater than 0");
        }

        Product product = productRepository.findById(request.getProductCode())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (request.getQuantityOrdered() > product.getQuantityInStock()) {
            throw new InsufficientStockException("Not enough stock available");
        }

        OrderDetailsId id = new OrderDetailsId(
                request.getOrderNumber(),
                request.getProductCode()
        );

        if (repository.existsById(id)) {
            throw new ResourceAlreadyExistsException("Order item already exists");
        }

        OrderDetails od = new OrderDetails();

        Orders order = new Orders();
        order.setOrderNumber(request.getOrderNumber());

        od.setOrder(order);
        od.setProduct(product);
        od.setQuantityOrdered(request.getQuantityOrdered());
        od.setPriceEach(request.getPriceEach());
        od.setOrderLineNumber(request.getOrderLineNumber());

        return mapToResponse(repository.save(od));
    }

    @Override
    public List<OrderDetailResponse> getItemsByOrder(Integer orderNumber) {

        if (orderNumber == null) {
            throw new BadRequestException("Order number cannot be null");
        }

        List<OrderDetails> items = repository.findByOrderOrderNumber(orderNumber);

        if (items.isEmpty()) {
            throw new ResourceNotFoundException("No items found for this order");
        }

        return items.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public OrderDetailResponse updateItem(Integer orderNumber, String productCode, OrderDetailRequest request) {

        if (orderNumber == null || productCode == null) {
            throw new BadRequestException("Order number and product code are required");
        }

        OrderDetailsId id = new OrderDetailsId(orderNumber, productCode);

        OrderDetails existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));

        if (request.getQuantityOrdered() != null && request.getQuantityOrdered() <= 0) {
            throw new InvalidDataException("Quantity must be greater than 0");
        }

        if (request.getPriceEach() != null && request.getPriceEach().doubleValue() <= 0) {
            throw new InvalidDataException("Price must be greater than 0");
        }

        if (request.getQuantityOrdered() != null) {
            existing.setQuantityOrdered(request.getQuantityOrdered());
        }

        if (request.getPriceEach() != null) {
            existing.setPriceEach(request.getPriceEach());
        }

        return mapToResponse(repository.save(existing));
    }

    @Override
    public void deleteItem(Integer orderNumber, String productCode) {

        if (orderNumber == null || productCode == null) {
            throw new BadRequestException("Order number and product code are required");
        }

        OrderDetailsId id = new OrderDetailsId(orderNumber, productCode);

        OrderDetails existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));

        try {
            repository.delete(existing);
        } catch (Exception e) {
            throw new DatabaseException("Failed to delete order item");
        }
    }

    private OrderDetailResponse mapToResponse(OrderDetails od) {
        return new OrderDetailResponse(
                od.getOrder().getOrderNumber(),
                od.getProduct().getProductCode(),
                od.getQuantityOrdered(),
                od.getPriceEach(),
                od.getOrderLineNumber()
        );
    }
}
package com.classicbusinessmodel_schema.backend.module.product.service;

import com.classicbusinessmodel_schema.backend.entity.OrderDetails;
import com.classicbusinessmodel_schema.backend.entity.OrderDetailsId;
import com.classicbusinessmodel_schema.backend.entity.Orders;
import com.classicbusinessmodel_schema.backend.entity.Product;
import com.classicbusinessmodel_schema.backend.exception.*;
import com.classicbusinessmodel_schema.backend.module.orders.repository.OrdersRepository;
import com.classicbusinessmodel_schema.backend.module.product.dto.request.OrderDetailRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.OrderDetailResponse;
import com.classicbusinessmodel_schema.backend.module.product.repository.OrderDetailRepository;
import com.classicbusinessmodel_schema.backend.module.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

// Service implementation for managing order item operations
@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {

    // Repository for accessing order data
    @Autowired
    private OrdersRepository orderRepository;

    // Repository for order item operations
    @Autowired
    private OrderDetailRepository repository;

    // Repository for product data (used for validation)
    @Autowired
    private ProductRepository productRepository;

    // Add a new item to an order with validation checks
    @Override
    public OrderDetailResponse addItem(OrderDetailRequest request) {

        // Validate required identifiers
        if (request.getOrderNumber() == null || request.getProductCode() == null) {
            throw new BadRequestException("Order number and product code are required");
        }

        // Fetch product and ensure it exists
        Product product = productRepository.findById(request.getProductCode())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Check stock availability
        if (request.getQuantityOrdered() > product.getQuantityInStock()) {
            throw new InsufficientStockException("Not enough stock available");
        }

        OrderDetailsId id = new OrderDetailsId(
                request.getOrderNumber(),
                request.getProductCode()
        );

        // Prevent duplicate order item
        if (repository.existsById(id)) {
            throw new ResourceAlreadyExistsException("Order item already exists");
        }

        OrderDetails od = new OrderDetails();

        // Fetch order and ensure it exists
        Orders order = orderRepository.findById(request.getOrderNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        od.setOrder(order);
        od.setProduct(product);
        od.setQuantityOrdered(request.getQuantityOrdered());
        od.setPriceEach(request.getPriceEach());
        od.setOrderLineNumber(request.getOrderLineNumber());

        // Save and return response DTO
        return mapToResponse(repository.save(od));
    }

    // Fetch a specific order item by order and product
    @Override
    public OrderDetailResponse getItem(Integer orderNumber, String productCode) {

        // Validate input parameters
        if (orderNumber == null || productCode == null) {
            throw new BadRequestException("Order number and product code are required");
        }

        OrderDetailsId id = new OrderDetailsId(orderNumber, productCode);
        OrderDetails item = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));

        return mapToResponse(item);
    }

    // Fetch paginated order items for a given order
    @Override
    public Page<OrderDetailResponse> getItemsByOrder(Integer orderNumber, int page, int size, String sortBy, String direction) {

        if (orderNumber == null) {
            throw new BadRequestException("Order number cannot be null");
        }
        // Build sorting configuration
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Fetch paginated data from repository
        Page<OrderDetails> itemsPage =
                repository.findByOrder_OrderNumber(orderNumber, pageable);

        if (itemsPage.isEmpty()) {
            throw new ResourceNotFoundException("No items found for this order");
        }

        // Convert entities to response DTOs
        return itemsPage.map(this::mapToResponse);
    }

    // Update quantity or price of an existing order item
    @Override
    public OrderDetailResponse updateItem(Integer orderNumber, String productCode, OrderDetailRequest request) {

        if (orderNumber == null || productCode == null) {
            throw new BadRequestException("Order number and product code are required");
        }

        OrderDetailsId id = new OrderDetailsId(orderNumber, productCode);

        // Fetch existing order item
        OrderDetails existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));

        // Validate quantity
        if (request.getQuantityOrdered() != null && request.getQuantityOrdered() <= 0) {
            throw new InvalidDataException("Quantity must be greater than 0");
        }

        // Validate price
        if (request.getPriceEach() != null && request.getPriceEach().doubleValue() <= 0) {
            throw new InvalidDataException("Price must be greater than 0");
        }

        // Update quantity after stock validation
        if (request.getQuantityOrdered() != null) {

            Product product = existing.getProduct(); // already linked

            if (request.getQuantityOrdered() > product.getQuantityInStock()) {
                throw new InsufficientStockException("Not enough stock available");
            }

            existing.setQuantityOrdered(request.getQuantityOrdered());
        }

        // Update price if provided
        if (request.getPriceEach() != null) {
            existing.setPriceEach(request.getPriceEach());
        }

        return mapToResponse(repository.save(existing));
    }

    // Delete an item from an order
    @Override
    public void deleteItem(Integer orderNumber, String productCode) {

        if (orderNumber == null || productCode == null) {
            throw new BadRequestException("Order number and product code are required");
        }

        OrderDetailsId id = new OrderDetailsId(orderNumber, productCode);

        // Ensure item exists before deletion
        OrderDetails existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));

        try {
            // Perform delete operation
            repository.delete(existing);
        } catch (Exception e) {
            throw new DatabaseException("Failed to delete order item");
        }
    }

    // Convert OrderDetails entity to response DTO
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
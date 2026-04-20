package com.classicbusinessmodel_schema.backend.module.product.service;

import com.classicbusinessmodel_schema.backend.entity.OrderDetails;
import com.classicbusinessmodel_schema.backend.entity.Orders;
import com.classicbusinessmodel_schema.backend.entity.Product;
import com.classicbusinessmodel_schema.backend.exception.BadRequestException;
import com.classicbusinessmodel_schema.backend.exception.InsufficientStockException;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.orders.repository.OrdersRepository;
import com.classicbusinessmodel_schema.backend.module.product.dto.request.OrderDetailRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.OrderDetailResponse;
import com.classicbusinessmodel_schema.backend.module.product.repository.OrderDetailRepository;
import com.classicbusinessmodel_schema.backend.module.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderDetailServiceTest {

    @Mock
    private OrderDetailRepository repository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrdersRepository orderRepository;

    @InjectMocks
    private OrderDetailServiceImpl service;

    // 1. ADD ITEM (SUCCESS)
    @Test
    void addItem_success() {

        OrderDetailRequest request = new OrderDetailRequest();
        request.setOrderNumber(1);
        request.setProductCode("P1");
        request.setQuantityOrdered(5);
        request.setPriceEach(BigDecimal.valueOf(100));
        request.setOrderLineNumber(1);

        Orders order = new Orders();
        order.setOrderNumber(1);

        Product product = new Product();
        product.setProductCode("P1");
        product.setQuantityInStock(10);

        when(productRepository.findById("P1"))
                .thenReturn(Optional.of(product));

        when(orderRepository.findById(1))
                .thenReturn(Optional.of(order));

        when(repository.existsById(any()))
                .thenReturn(false);

        when(repository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        OrderDetailResponse response = service.addItem(request);

        assertNotNull(response);

        verify(repository).save(any());

    }

    // 2. INVALID INPUT
    @Test
    void addItem_invalidInput() {

        OrderDetailRequest request = new OrderDetailRequest();

        assertThrows(BadRequestException.class,
                () -> service.addItem(request));

        verify(repository, never()).save(any());
    }

    // 3. PRODUCT NOT FOUND
    @Test
    void addItem_productNotFound() {

        OrderDetailRequest request = new OrderDetailRequest();
        request.setOrderNumber(1);
        request.setProductCode("P1");
        request.setQuantityOrdered(5);
        request.setPriceEach(BigDecimal.valueOf(100));
        request.setOrderLineNumber(1);

        when(productRepository.findById("P1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.addItem(request));

        verify(repository, never()).save(any());
    }

    // 4. INSUFFICIENT STOCK
    @Test
    void addItem_insufficientStock() {

        OrderDetailRequest request = new OrderDetailRequest();
        request.setOrderNumber(1);
        request.setProductCode("P1");
        request.setQuantityOrdered(50);
        request.setPriceEach(BigDecimal.valueOf(100));
        request.setOrderLineNumber(1);

        Product product = new Product();
        product.setQuantityInStock(10);

        when(productRepository.findById("P1"))
                .thenReturn(Optional.of(product));

        assertThrows(InsufficientStockException.class,
                () -> service.addItem(request));

        verify(repository, never()).save(any());
    }

    // 5. GET ITEMS BY ORDER
    @Test
    void getItemsByOrder_success() {

        Orders order = new Orders();
        order.setOrderNumber(1);

        Product product = new Product();
        product.setProductCode("P1");

        OrderDetails od = new OrderDetails();
        od.setOrder(order);
        od.setProduct(product);
        od.setQuantityOrdered(5);
        od.setPriceEach(BigDecimal.valueOf(100));
        od.setOrderLineNumber(1);

        when(repository.findByOrderOrderNumber(1))
                .thenReturn(List.of(od));

        List<OrderDetailResponse> response = service.getItemsByOrder(1);

        assertNotNull(response);
        assertEquals(1, response.size());
    }
}
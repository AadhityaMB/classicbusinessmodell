package com.classicbusinessmodel_schema.backend.module.product.service;

import com.classicbusinessmodel_schema.backend.entity.OrderDetails;
import com.classicbusinessmodel_schema.backend.entity.Orders;
import com.classicbusinessmodel_schema.backend.entity.Product;
import com.classicbusinessmodel_schema.backend.exception.BadRequestException;
import com.classicbusinessmodel_schema.backend.exception.InsufficientStockException;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderDetailServiceTest {

    @Mock
    private OrderDetailRepository repository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderDetailServiceImpl service;

    // 1. ADD ITEM (POSITIVE)
    @Test
    void addItem_success() {

        OrderDetailRequest request = new OrderDetailRequest();
        request.setOrderNumber(1);
        request.setProductCode("P1");
        request.setQuantityOrdered(5);
        request.setPriceEach(BigDecimal.valueOf(100));

        Product product = new Product();
        product.setQuantityInStock(10);

        when(productRepository.findById("P1")).thenReturn(Optional.of(product));
        when(repository.existsById(any())).thenReturn(false);
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        OrderDetailResponse response = service.addItem(request);

        assertNotNull(response);
        verify(repository).save(any());
    }

    // 2. ADD ITEM (NEGATIVE - INVALID INPUT)
    @Test
    void addItem_invalidInput() {

        OrderDetailRequest request = new OrderDetailRequest();

        assertThrows(BadRequestException.class,
                () -> service.addItem(request));
    }

    // 3. ADD ITEM (NEGATIVE - PRODUCT NOT FOUND)
    @Test
    void addItem_productNotFound() {

        OrderDetailRequest request = new OrderDetailRequest();
        request.setOrderNumber(1);
        request.setProductCode("P1");

        request.setQuantityOrdered(5);
        request.setPriceEach(BigDecimal.valueOf(100));

        when(productRepository.findById("P1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.addItem(request));
    }

    // 4. ADD ITEM (NEGATIVE - INSUFFICIENT STOCK)
    @Test
    void addItem_insufficientStock() {

        OrderDetailRequest request = new OrderDetailRequest();
        request.setOrderNumber(1);
        request.setProductCode("P1");
        request.setQuantityOrdered(50);
        request.setPriceEach(BigDecimal.valueOf(100));

        Product product = new Product();
        product.setQuantityInStock(10);

        when(productRepository.findById("P1"))
                .thenReturn(Optional.of(product));

        assertThrows(InsufficientStockException.class,
                () -> service.addItem(request));
    }

    // 5. GET ITEMS BY ORDER (POSITIVE)
    @Test
    void getItemsByOrder_success() {

        OrderDetails od = new OrderDetails();

        Orders order = new Orders();
        order.setOrderNumber(1);

        Product product = new Product();
        product.setProductCode("P1");

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
package com.classicbusinessmodel_schema.backend.module.product.service;

import com.classicbusinessmodel_schema.backend.entity.Product;
import com.classicbusinessmodel_schema.backend.entity.ProductLine;
import com.classicbusinessmodel_schema.backend.exception.BadRequestException;
import com.classicbusinessmodel_schema.backend.exception.ResourceAlreadyExistsException;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.product.dto.request.CreateProductRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.request.UpdateProductRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.ProductResponse;
import com.classicbusinessmodel_schema.backend.module.product.repository.ProductLineRepository;
import com.classicbusinessmodel_schema.backend.module.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Unit tests for ProductService using Mockito
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    // Mock dependencies for isolated service testing

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductLineRepository productLineRepository;

    @InjectMocks
    private ProductServiceImpl service;

    // Verify successful creation of a product
    @Test
    void createProduct_success() {

        CreateProductRequest request = new CreateProductRequest();
        request.setProductCode("P1");
        request.setProductLine("Classic Cars");

        ProductLine pl = new ProductLine();
        pl.setProductLine("Classic Cars");

        when(productRepository.existsProduct("P1")).thenReturn(false);
        when(productLineRepository.findById("Classic Cars")).thenReturn(Optional.of(pl));
        when(productRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        ProductResponse response = service.createProduct(request);

        assertNotNull(response);
        verify(productRepository).save(any());
    }

    // Verify exception when creating duplicate product
    @Test
    void createProduct_duplicate() {

        CreateProductRequest request = new CreateProductRequest();
        request.setProductCode("P1");

        when(productRepository.existsProduct("P1")).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class,
                () -> service.createProduct(request));
    }

    // Verify exception when product is not found
    @Test
    void getProduct_notFound() {

        when(productRepository.findById("P1")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getProductById("P1"));
    }

    // Verify validation failure for invalid delete input
    @Test
    void deleteProduct_invalidInput() {

        assertThrows(BadRequestException.class,
                () -> service.deleteProduct(""));
    }

    // Verify successful update of a product
    @Test
    void updateProduct_success() {

        UpdateProductRequest request = new UpdateProductRequest();
        request.setProductName("Updated");

        Product product = new Product();
        product.setProductCode("P1");

        ProductLine pl = new ProductLine();
        pl.setProductLine("Classic Cars");

        product.setProductLine(pl);

        when(productRepository.findById("P1")).thenReturn(Optional.of(product));
        when(productRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        ProductResponse response = service.updateProduct("P1", request);

        assertNotNull(response);
        assertEquals("Updated", response.getProductName());
    }
}
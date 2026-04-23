package com.classicbusinessmodel_schema.backend.module.product.service;

import com.classicbusinessmodel_schema.backend.entity.Product;
import com.classicbusinessmodel_schema.backend.entity.ProductLine;
import com.classicbusinessmodel_schema.backend.exception.DatabaseException;
import com.classicbusinessmodel_schema.backend.exception.ResourceAlreadyExistsException;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.product.dto.request.ProductLineRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.ProductLineResponse;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.ProductResponse;
import com.classicbusinessmodel_schema.backend.module.product.repository.ProductLineRepository;
import com.classicbusinessmodel_schema.backend.module.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductLineServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductLineRepository productLineRepository;

    @InjectMocks
    private ProductLineServiceImpl service;

    // 1. CREATE (POSITIVE)
    @Test
    void createProductLine_success() {

        ProductLineRequest request = new ProductLineRequest();
        request.setProductLine("Classic Cars");

        ProductLine pl = new ProductLine();
        pl.setProductLine("Classic Cars");

        when(productLineRepository.existsById("Classic Cars")).thenReturn(false);
        when(productLineRepository.save(any())).thenReturn(pl);

        ProductLineResponse response = service.createProductLine(request);

        assertNotNull(response);
        assertEquals("Classic Cars", response.getProductLine());
    }

    // 2. CREATE (NEGATIVE - DUPLICATE)
    @Test
    void createProductLine_duplicate() {

        ProductLineRequest request = new ProductLineRequest();
        request.setProductLine("Classic Cars");

        when(productLineRepository.existsById("Classic Cars")).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class,
                () -> service.createProductLine(request));
    }

    // 3. GET BY ID (NEGATIVE - NOT FOUND)
    @Test
    void getProductLine_notFound() {

        when(productLineRepository.findById("Classic Cars"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getProductLineById("Classic Cars"));
    }

    // 4. DELETE (NEGATIVE - HAS PRODUCTS)
    @Test
    void deleteProductLine_hasProducts() {

        ProductLine pl = new ProductLine();
        pl.setProductLine("Classic Cars");

        Product product = new Product();

        when(productLineRepository.findById("Classic Cars"))
                .thenReturn(Optional.of(pl));

        when(productRepository.findByProductLineProductLine("Classic Cars"))
                .thenReturn(List.of(product)); // not empty

        assertThrows(DatabaseException.class,
                () -> service.deleteProductLine("Classic Cars"));
    }

    // 5. GET PRODUCTS BY LINE (POSITIVE)
    @Test
    void getProductsByLine_success() {

        Product product = new Product();
        product.setProductCode("P1");
        product.setProductName("Test");

        ProductLine pl = new ProductLine();
        pl.setProductLine("Classic Cars");

        product.setProductLine(pl);

        when(productLineRepository.existsById("Classic Cars"))
                .thenReturn(true);

        when(productRepository.findByProductLineProductLine("Classic Cars"))
                .thenReturn(List.of(product));

        List<ProductResponse> response = service.getProductsByLine("Classic Cars");

        assertNotNull(response);
        assertEquals(1, response.size());
    }
}

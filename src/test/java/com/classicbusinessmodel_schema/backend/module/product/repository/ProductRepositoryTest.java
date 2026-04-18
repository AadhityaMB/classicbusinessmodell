package com.classicbusinessmodel_schema.backend.module.product.repository;

import com.classicbusinessmodel_schema.backend.entity.Product;
import com.classicbusinessmodel_schema.backend.entity.ProductLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductLineRepository productLineRepository;

    // Create shared ProductLine
    private ProductLine createProductLine() {
        ProductLine pl = new ProductLine();
        pl.setProductLine("Classic Cars");
        return productLineRepository.save(pl);
    }

    // Create Product
    private Product createProduct(ProductLine productLine) {
        Product product = new Product();
        product.setProductCode("S10_TEST");
        product.setProductName("Test Car");
        product.setProductLine(productLine);
        product.setProductScale("1:10");
        product.setProductVendor("Test Vendor");
        product.setProductDescription("Test Description");
        product.setQuantityInStock(100);
        product.setBuyPrice(BigDecimal.valueOf(50.00));
        product.setMSRP(BigDecimal.valueOf(100.00));
        return product;
    }

    // 1. CREATE (POSITIVE)
    @Test
    @DisplayName("Save Product")
    void saveProduct() {

        ProductLine pl = createProductLine();
        Product product = createProduct(pl);

        Product saved = productRepository.save(product);

        assertNotNull(saved);
        assertEquals("S10_TEST", saved.getProductCode());
    }

    // 2. READ BY ID (POSITIVE)
    @Test
    @DisplayName("Find Product By ID")
    void findById() {

        ProductLine pl = createProductLine();
        Product product = productRepository.save(createProduct(pl));

        Optional<Product> found = productRepository.findById(product.getProductCode());

        assertTrue(found.isPresent());
    }

    // 3. READ ALL (NEGATIVE - EMPTY DB)
    @Test
    @DisplayName("Find All Products - Empty")
    void findAllEmpty() {

        List<Product> list = productRepository.findAll();

        assertTrue(list.isEmpty());
    }

    // 4. UPDATE (POSITIVE)
    @Test
    @DisplayName("Update Product")
    void updateProduct() {

        ProductLine pl = createProductLine();
        Product product = productRepository.save(createProduct(pl));

        product.setProductName("Updated");

        Product updated = productRepository.save(product);

        assertEquals("Updated", updated.getProductName());
    }

    // 5. DELETE (NEGATIVE - NOT FOUND)
    @Test
    @DisplayName("Delete Product - Not Found")
    void deleteProductNotFound() {

        String invalidId = "INVALID";

        productRepository.deleteById(invalidId);

        Optional<Product> deleted = productRepository.findById(invalidId);

        assertFalse(deleted.isPresent());
    }
}
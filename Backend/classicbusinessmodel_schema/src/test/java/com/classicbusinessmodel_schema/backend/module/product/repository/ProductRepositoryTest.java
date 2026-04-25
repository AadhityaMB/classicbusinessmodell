package com.classicbusinessmodel_schema.backend.module.product.repository;

import com.classicbusinessmodel_schema.backend.entity.Product;
import com.classicbusinessmodel_schema.backend.entity.ProductLine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

// Test class for validating ProductRepository operations
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductLineRepository productLineRepository;

    // Helper methods for setting up test data

    // Create and persist a test product line
    private ProductLine createProductLine() {
        ProductLine pl = new ProductLine();
        pl.setProductLine("PL_" + System.currentTimeMillis());
        pl.setTextDescription("Test");
        pl.setHtmlDescription("<p>Test</p>");
        return productLineRepository.save(pl);
    }

    // Build a product entity for testing
    private Product createProduct(ProductLine pl) {
        Product product = new Product();
        product.setProductCode("P_" + System.currentTimeMillis());
        product.setProductName("Test Car");
        product.setProductLine(pl);
        product.setProductScale("1:10");
        product.setProductVendor("Vendor");
        product.setProductDescription("Desc");
        product.setQuantityInStock(100);
        product.setBuyPrice(BigDecimal.valueOf(50));
        product.setMSRP(BigDecimal.valueOf(100));
        return product;
    }

    // Test cases for repository CRUD operations

    // Verify saving a product
    @Test
    void saveProduct() {

        ProductLine pl = createProductLine();
        Product saved = productRepository.save(createProduct(pl));

        assertNotNull(saved);
    }

    // Verify fetching product by ID
    @Test
    void findById() {

        ProductLine pl = createProductLine();
        Product product = productRepository.save(createProduct(pl));

        Optional<Product> found = productRepository.findById(product.getProductCode());

        assertTrue(found.isPresent());
    }

    // Verify fetching all products
    @Test
    void findAll() {

        List<Product> list = productRepository.findAll();

        assertNotNull(list); // no empty assumption
    }

    // Verify updating a product
    @Test
    void updateProduct() {

        ProductLine pl = createProductLine();
        Product product = productRepository.save(createProduct(pl));

        product.setProductName("Updated");

        Product updated = productRepository.save(product);

        assertEquals("Updated", updated.getProductName());
    }

    // Verify deleting a product
    @Test
    void deleteProduct() {

        ProductLine pl = createProductLine();
        Product product = productRepository.save(createProduct(pl));

        productRepository.deleteById(product.getProductCode());

        Optional<Product> deleted = productRepository.findById(product.getProductCode());

        assertFalse(deleted.isPresent());
    }
}
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

    // Create shared ProductLine (avoids duplicate PK issue)
    private ProductLine createProductLine() {
        ProductLine pl = new ProductLine();
        pl.setProductLine("Classic Cars");
        return productLineRepository.save(pl);
    }

    // Create Product (uses SAME productLine)
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

    // CREATE
    @Test
    @DisplayName("Save Product")
    void saveProduct() {

        ProductLine pl = createProductLine();
        Product product = createProduct(pl);

        Product saved = productRepository.save(product);

        assertNotNull(saved);
        assertEquals("S10_TEST", saved.getProductCode());
    }

    // READ BY ID
    @Test
    @DisplayName("Find Product By ID")
    void findById() {

        ProductLine pl = createProductLine();
        Product product = productRepository.save(createProduct(pl));

        Optional<Product> found = productRepository.findById(product.getProductCode());

        assertTrue(found.isPresent());
        assertEquals("Test Car", found.get().getProductName());
    }

    // READ ALL
    @Test
    @DisplayName("Find All Products")
    void findAll() {

        ProductLine pl = createProductLine();
        productRepository.save(createProduct(pl));

        List<Product> list = productRepository.findAll();

        assertEquals(1, list.size());
    }

    // UPDATE
    @Test
    @DisplayName("Update Product")
    void updateProduct() {

        ProductLine pl = createProductLine();
        Product product = productRepository.save(createProduct(pl));

        product.setProductName("Updated");

        Product updated = productRepository.save(product);

        assertEquals("Updated", updated.getProductName());
    }

    // DELETE
    @Test
    @DisplayName("Delete Product")
    void deleteProduct() {

        ProductLine pl = createProductLine();
        Product product = productRepository.save(createProduct(pl));

        productRepository.deleteById(product.getProductCode());

        Optional<Product> deleted = productRepository.findById(product.getProductCode());

        assertFalse(deleted.isPresent());
    }
}

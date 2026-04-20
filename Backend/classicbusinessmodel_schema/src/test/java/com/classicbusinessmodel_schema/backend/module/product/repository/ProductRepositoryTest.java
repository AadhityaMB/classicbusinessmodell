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

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductLineRepository productLineRepository;

    // HELPERS

    private ProductLine createProductLine() {
        ProductLine pl = new ProductLine();
        pl.setProductLine("PL_" + System.currentTimeMillis());
        pl.setTextDescription("Test");
        pl.setHtmlDescription("<p>Test</p>");
        return productLineRepository.save(pl);
    }

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

    // TESTS

    // 1. CREATE
    @Test
    void saveProduct() {

        ProductLine pl = createProductLine();
        Product saved = productRepository.save(createProduct(pl));

        assertNotNull(saved);
    }

    // 2. READ BY ID
    @Test
    void findById() {

        ProductLine pl = createProductLine();
        Product product = productRepository.save(createProduct(pl));

        Optional<Product> found = productRepository.findById(product.getProductCode());

        assertTrue(found.isPresent());
    }

    // 3. READ ALL
    @Test
    void findAll() {

        List<Product> list = productRepository.findAll();

        assertNotNull(list); // no empty assumption
    }

    // 4. UPDATE
    @Test
    void updateProduct() {

        ProductLine pl = createProductLine();
        Product product = productRepository.save(createProduct(pl));

        product.setProductName("Updated");

        Product updated = productRepository.save(product);

        assertEquals("Updated", updated.getProductName());
    }

    // 5. DELETE
    @Test
    void deleteProduct() {

        ProductLine pl = createProductLine();
        Product product = productRepository.save(createProduct(pl));

        productRepository.deleteById(product.getProductCode());

        Optional<Product> deleted = productRepository.findById(product.getProductCode());

        assertFalse(deleted.isPresent());
    }
}
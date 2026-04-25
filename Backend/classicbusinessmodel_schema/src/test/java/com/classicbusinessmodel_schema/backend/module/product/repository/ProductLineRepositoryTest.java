package com.classicbusinessmodel_schema.backend.module.product.repository;

import com.classicbusinessmodel_schema.backend.entity.ProductLine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

// Test class for validating ProductLineRepository operations
@DataJpaTest
class ProductLineRepositoryTest {

    @Autowired
    private ProductLineRepository repository;

    // Helper method for creating test product line data

    // Build a product line entity for testing
    private ProductLine createProductLine() {
        ProductLine pl = new ProductLine();
        pl.setProductLine("PL_" + System.currentTimeMillis());
        pl.setTextDescription("Classic car collection");
        pl.setHtmlDescription("<p>Classic cars</p>");
        return pl;
    }

    // Verify saving a product line
    @Test
    void saveProductLine() {

        ProductLine saved = repository.save(createProductLine());

        assertNotNull(saved);
    }

    // Verify fetching product line by ID
    @Test
    void findById() {

        ProductLine saved = repository.save(createProductLine());

        Optional<ProductLine> found = repository.findById(saved.getProductLine());

        assertTrue(found.isPresent());
    }

    // Verify fetching all product lines
    @Test
    void findAll() {

        List<ProductLine> list = repository.findAll();

        assertNotNull(list);
    }

    // Verify updating a product line
    @Test
    void updateProductLine() {

        ProductLine pl = repository.save(createProductLine());

        pl.setTextDescription("Updated");

        ProductLine updated = repository.save(pl);

        assertEquals("Updated", updated.getTextDescription());
    }

    // Verify deleting a product line
    @Test
    void deleteProductLine() {

        ProductLine pl = repository.save(createProductLine());

        repository.deleteById(pl.getProductLine());

        Optional<ProductLine> deleted = repository.findById(pl.getProductLine());

        assertFalse(deleted.isPresent());
    }
}
package com.classicbusinessmodel_schema.backend.module.product.repository;

import com.classicbusinessmodel_schema.backend.entity.ProductLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductLineRepositoryTest {

    @Autowired
    private ProductLineRepository repository;

    // Create ProductLine
    private ProductLine createProductLine() {
        ProductLine pl = new ProductLine();
        pl.setProductLine("Classic Cars");
        pl.setTextDescription("Classic car collection");
        pl.setHtmlDescription("<p>Classic cars</p>");
        return pl;
    }

    // 1. CREATE (POSITIVE)
    @Test
    @DisplayName("Save ProductLine")
    void saveProductLine() {

        ProductLine saved = repository.save(createProductLine());

        assertNotNull(saved);
        assertEquals("Classic Cars", saved.getProductLine());
    }

    // 2. READ BY ID (POSITIVE)
    @Test
    @DisplayName("Find ProductLine By ID")
    void findById() {

        ProductLine saved = repository.save(createProductLine());

        Optional<ProductLine> found = repository.findById(saved.getProductLine());

        assertTrue(found.isPresent());
    }

    // 3. READ ALL (NEGATIVE - EMPTY DB)
    @Test
    @DisplayName("Find All ProductLines - Empty")
    void findAllEmpty() {

        List<ProductLine> list = repository.findAll();

        assertTrue(list.isEmpty());
    }

    // 4. UPDATE (POSITIVE)
    @Test
    @DisplayName("Update ProductLine")
    void updateProductLine() {

        ProductLine pl = repository.save(createProductLine());

        pl.setTextDescription("Updated Description");

        ProductLine updated = repository.save(pl);

        assertEquals("Updated Description", updated.getTextDescription());
    }

    // 5. DELETE (NEGATIVE - NOT FOUND)
    @Test
    @DisplayName("Delete ProductLine - Not Found")
    void deleteProductLineNotFound() {

        String invalidId = "INVALID";

        repository.deleteById(invalidId);

        Optional<ProductLine> deleted = repository.findById(invalidId);

        assertFalse(deleted.isPresent());
    }
}
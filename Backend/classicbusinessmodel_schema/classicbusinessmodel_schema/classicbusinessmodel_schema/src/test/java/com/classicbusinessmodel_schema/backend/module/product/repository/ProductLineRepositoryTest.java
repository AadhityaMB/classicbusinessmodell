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

    // CREATE
    @Test
    @DisplayName("Save ProductLine")
    void saveProductLine() {

        ProductLine saved = repository.save(createProductLine());

        assertNotNull(saved);
        assertEquals("Classic Cars", saved.getProductLine());
    }

    // READ BY ID
    @Test
    @DisplayName("Find ProductLine By ID")
    void findById() {

        ProductLine saved = repository.save(createProductLine());

        Optional<ProductLine> found = repository.findById(saved.getProductLine());

        assertTrue(found.isPresent());
        assertEquals("Classic Cars", found.get().getProductLine());
    }

    // READ ALL
    @Test
    @DisplayName("Find All ProductLines")
    void findAll() {

        repository.save(createProductLine());

        List<ProductLine> list = repository.findAll();

        assertEquals(1, list.size());
    }

    // UPDATE
    @Test
    @DisplayName("Update ProductLine")
    void updateProductLine() {

        ProductLine pl = repository.save(createProductLine());

        pl.setTextDescription("Updated Description");

        ProductLine updated = repository.save(pl);

        assertEquals("Updated Description", updated.getTextDescription());
    }

    // DELETE
    @Test
    @DisplayName("Delete ProductLine")
    void deleteProductLine() {

        ProductLine pl = repository.save(createProductLine());

        repository.deleteById(pl.getProductLine());

        Optional<ProductLine> deleted = repository.findById(pl.getProductLine());

        assertFalse(deleted.isPresent());
    }
}
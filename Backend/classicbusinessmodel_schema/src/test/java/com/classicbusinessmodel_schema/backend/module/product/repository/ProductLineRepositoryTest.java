package com.classicbusinessmodel_schema.backend.module.product.repository;

import com.classicbusinessmodel_schema.backend.entity.ProductLine;
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

    // HELPER
    private ProductLine createProductLine() {
        ProductLine pl = new ProductLine();
        pl.setProductLine("PL_" + System.currentTimeMillis());
        pl.setTextDescription("Classic car collection");
        pl.setHtmlDescription("<p>Classic cars</p>");
        return pl;
    }

    // 1. CREATE
    @Test
    void saveProductLine() {

        ProductLine saved = repository.save(createProductLine());

        assertNotNull(saved);
    }

    // 2. READ BY ID
    @Test
    void findById() {

        ProductLine saved = repository.save(createProductLine());

        Optional<ProductLine> found = repository.findById(saved.getProductLine());

        assertTrue(found.isPresent());
    }

    // 3. READ ALL
    @Test
    void findAll() {

        List<ProductLine> list = repository.findAll();

        assertNotNull(list); // no empty assumption
    }

    // 4. UPDATE
    @Test
    void updateProductLine() {

        ProductLine pl = repository.save(createProductLine());

        pl.setTextDescription("Updated");

        ProductLine updated = repository.save(pl);

        assertEquals("Updated", updated.getTextDescription());
    }

    // 5. DELETE
    @Test
    void deleteProductLine() {

        ProductLine pl = repository.save(createProductLine());

        repository.deleteById(pl.getProductLine());

        Optional<ProductLine> deleted = repository.findById(pl.getProductLine());

        assertFalse(deleted.isPresent());
    }
}
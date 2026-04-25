package com.classicbusinessmodel_schema.backend.module.product.service;

import com.classicbusinessmodel_schema.backend.entity.Product;
import com.classicbusinessmodel_schema.backend.entity.ProductLine;
import com.classicbusinessmodel_schema.backend.exception.BadRequestException;
import com.classicbusinessmodel_schema.backend.exception.DatabaseException;
import com.classicbusinessmodel_schema.backend.exception.ResourceAlreadyExistsException;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.product.dto.request.ProductLineRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.ProductLineResponse;
import com.classicbusinessmodel_schema.backend.module.product.dto.response.ProductResponse;
import com.classicbusinessmodel_schema.backend.module.product.repository.ProductLineRepository;
import com.classicbusinessmodel_schema.backend.module.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

// Service implementation for managing product lines and related products
@Service
@Transactional
public class ProductLineServiceImpl implements ProductLineService {

    // Repository for accessing product data
    @Autowired
    private ProductRepository productRepository;

    // Repository for product line operations
    @Autowired
    private ProductLineRepository productLineRepository;

    // Create a new product line after validation
    @Override
    public ProductLineResponse createProductLine(ProductLineRequest request) {

        // Validate product line name
        if (request.getProductLine() == null || request.getProductLine().isBlank()) {
            throw new BadRequestException("Product line cannot be empty");
        }

        // Prevent duplicate product line
        if (productLineRepository.existsProductLine(request.getProductLine())) {
            throw new ResourceAlreadyExistsException("Product line already exists");
        }

        ProductLine pl = new ProductLine();
        pl.setProductLine(request.getProductLine());
        pl.setTextDescription(request.getTextDescription());
        pl.setHtmlDescription(request.getHtmlDescription());

        // Save entity and return response DTO
        return mapToResponse(productLineRepository.save(pl));
    }

    // Delete a product line if no products are associated
    @Override
    public void deleteProductLine(String productLine) {

        // Ensure product line exists
        ProductLine pl = productLineRepository.findById(productLine)
                .orElseThrow(() -> new ResourceNotFoundException("Product line not found"));

        // Check if any products are linked to this product line
        List<Product> products = productRepository.findByProductLineProductLine(productLine);

        if (!products.isEmpty()) {
            throw new DatabaseException("Cannot delete product line with existing products");
        }

        // Perform delete operation
        productLineRepository.delete(pl);
    }

    // Fetch all product lines
    @Override
    public Page<ProductLineResponse> getAllProductLines(int page, int size, String sortBy, String direction) {

        // Build sorting configuration
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Fetch paginated data from repository
        Page<ProductLine> pageData = productLineRepository.findAll(pageable);

        if (pageData.isEmpty()) {
            throw new ResourceNotFoundException("No product lines found");
        }

        // Convert entities to response DTOs
        return pageData.map(this::mapToResponse);
    }

    // Fetch a specific product line by name
    @Override
    public ProductLineResponse getProductLineById(String productLine) {

        ProductLine pl = productLineRepository.findById(productLine)
                .orElseThrow(() -> new ResourceNotFoundException("Product line not found"));

        return mapToResponse(pl);
    }

    // Fetch all products under a given product line
    @Override
    public List<ProductResponse> getProductsByLine(String productLine) {

        // Validate product line existence
        if (!productLineRepository.existsProductLine(productLine)){
            throw new ResourceNotFoundException("Product line not found");
        }

        // Fetch products for the product line
        List<Product> products = productRepository.findByProductLineProductLine(productLine);

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for this product line");
        }

        // Convert product entities to response DTOs
        return products.stream()
                .map(this::mapProductToResponse)
                .toList();
    }

    // Convert ProductLine entity to response DTO
    private ProductLineResponse mapToResponse(ProductLine pl) {
        return new ProductLineResponse(
                pl.getProductLine(),
                pl.getTextDescription(),
                pl.getHtmlDescription()
        );
    }

    // Convert Product entity to response DTO
    private ProductResponse mapProductToResponse(Product product) {
        return new ProductResponse(
                product.getProductCode(),
                product.getProductName(),
                product.getProductLine().getProductLine(),
                product.getProductScale(),
                product.getProductVendor(),
                product.getProductDescription(),
                product.getQuantityInStock(),
                product.getBuyPrice(),
                product.getMSRP()
        );
    }
}
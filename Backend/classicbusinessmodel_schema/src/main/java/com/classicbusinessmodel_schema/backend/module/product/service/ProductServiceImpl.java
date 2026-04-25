package com.classicbusinessmodel_schema.backend.module.product.service;

import com.classicbusinessmodel_schema.backend.entity.Product;
import com.classicbusinessmodel_schema.backend.entity.ProductLine;
import com.classicbusinessmodel_schema.backend.exception.BadRequestException;
import com.classicbusinessmodel_schema.backend.exception.DatabaseException;
import com.classicbusinessmodel_schema.backend.exception.ResourceAlreadyExistsException;
import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.product.dto.request.CreateProductRequest;
import com.classicbusinessmodel_schema.backend.module.product.dto.request.UpdateProductRequest;
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

// Service implementation for managing product operations
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    // Service implementation for managing product operations
    @Autowired
    private ProductRepository productRepository;

    // Repository for product line validation and lookup
    @Autowired
    private ProductLineRepository productLineRepository;

    // Create a new product after validation checks
    @Override
    public ProductResponse createProduct(CreateProductRequest request) {

        // Validate product code
        if (request.getProductCode() == null || request.getProductCode().isBlank()) {
            throw new BadRequestException("Product code cannot be empty");
        }

        // Prevent duplicate product creation
        if (productRepository.existsProduct(request.getProductCode())) {
            throw new ResourceAlreadyExistsException("Product already exists");
        }

        // Fetch and validate product line
        ProductLine productLine = productLineRepository.findById(request.getProductLine())
                .orElseThrow(() -> new ResourceNotFoundException("Product line not found"));

        Product product = new Product();
        product.setProductCode(request.getProductCode());
        product.setProductName(request.getProductName());
        product.setProductLine(productLine);
        product.setProductScale(request.getProductScale());
        product.setProductVendor(request.getProductVendor());
        product.setProductDescription(request.getProductDescription());
        product.setQuantityInStock(request.getQuantityInStock());
        product.setBuyPrice(request.getBuyPrice());
        product.setMSRP(request.getMsrp());

        // Save entity and return response DTO
        return mapToResponse(productRepository.save(product));
    }

    // Update product details based on provided fields
    @Override
    public ProductResponse updateProduct(String productCode, UpdateProductRequest request) {

        // Validate product code
        if (productCode == null || productCode.isBlank()) {
            throw new BadRequestException("Product code cannot be empty");
        }

        // Fetch existing product
        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (request.getProductName() != null) {
            product.setProductName(request.getProductName());
        }

        // Update product line after validation
        if (request.getProductLine() != null) {
            ProductLine pl = productLineRepository.findById(request.getProductLine())
                    .orElseThrow(() -> new ResourceNotFoundException("Product line not found"));
            product.setProductLine(pl);
        }

        if (request.getProductScale() != null) {
            product.setProductScale(request.getProductScale());
        }

        if (request.getProductVendor() != null) {
            product.setProductVendor(request.getProductVendor());
        }

        if (request.getProductDescription() != null) {
            product.setProductDescription(request.getProductDescription());
        }

        if (request.getQuantityInStock() != null) {
            product.setQuantityInStock(request.getQuantityInStock());
        }

        if (request.getBuyPrice() != null) {
            product.setBuyPrice(request.getBuyPrice());
        }

        if (request.getMsrp() != null) {
            product.setMSRP(request.getMsrp());
        }

        // Save updated product and return response DTO
        return mapToResponse(productRepository.save(product));
    }

    // Delete a product after validating its existence
    @Override
    public void deleteProduct(String productCode) {

        if (productCode == null || productCode.isBlank()) {
            throw new BadRequestException("Product code cannot be empty");
        }

        // Ensure product exists
        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        try {
            // Perform delete operation
            productRepository.delete(product);
        } catch (Exception e) {
            throw new DatabaseException("Failed to delete product (possible FK constraint)");
        }
    }

    // Fetch paginated products with sorting
    @Override
    public Page<ProductResponse> getAllProducts(int page, int size, String sortBy, String direction) {

        // Build sorting configuration
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Fetch paginated data from repository
        Page<Product> productPage = productRepository.findAll(pageable);

        if (productPage.isEmpty()) {
            throw new ResourceNotFoundException("No products found");
        }

        // Convert entities to response DTOs
        return productPage.map(this::mapToResponse);
    }

    // Fetch a product by its product code
    @Override
    public ProductResponse getProductById(String productCode) {

        // Validate product code
        if (productCode == null || productCode.isBlank()) {
            throw new BadRequestException("Product code cannot be null or empty");
        }

        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return mapToResponse(product);
    }

    // Convert Product entity to response DTO
    private ProductResponse mapToResponse(Product product) {
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
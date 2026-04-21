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

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductLineRepository productLineRepository;

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {

        if (request.getProductCode() == null || request.getProductCode().isBlank()) {
            throw new BadRequestException("Product code cannot be empty");
        }

        if (productRepository.existsById(request.getProductCode())) {
            throw new ResourceAlreadyExistsException("Product already exists");
        }

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

        return mapToResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse updateProduct(String productCode, UpdateProductRequest request) {

        if (productCode == null || productCode.isBlank()) {
            throw new BadRequestException("Product code cannot be empty");
        }

        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (request.getProductName() != null) {
            product.setProductName(request.getProductName());
        }

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

        return mapToResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(String productCode) {

        if (productCode == null || productCode.isBlank()) {
            throw new BadRequestException("Product code cannot be empty");
        }

        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        try {
            productRepository.delete(product);
        } catch (Exception e) {
            throw new DatabaseException("Failed to delete product (possible FK constraint)");
        }
    }

    @Override
    public List<ProductResponse> getAllProducts(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage = productRepository.findAll(pageable);

        if (productPage.isEmpty()) {
            throw new ResourceNotFoundException("No products found");
        }

        return productPage.map(this::mapToResponse).getContent();
    }

    @Override
    public ProductResponse getProductById(String productCode) {

        if (productCode == null || productCode.isBlank()) {
            throw new BadRequestException("Product code cannot be null or empty");
        }

        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return mapToResponse(product);
    }

    @Override
    public List<ProductResponse> getProductsByLine(String productLine) {

        if (productLine == null || productLine.isBlank()) {
            throw new BadRequestException("Product line cannot be null or empty");
        }

        if (!productLineRepository.existsById(productLine)) {
            throw new ResourceNotFoundException("Product line not found");
        }

        List<Product> products = productRepository.findByProductLineProductLine(productLine);

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for this product line");
        }

        return products.stream()
                .map(this::mapToResponse)
                .toList();
    }

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
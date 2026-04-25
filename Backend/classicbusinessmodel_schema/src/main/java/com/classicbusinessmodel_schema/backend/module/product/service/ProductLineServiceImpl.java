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

@Service
@Transactional
public class ProductLineServiceImpl implements ProductLineService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductLineRepository productLineRepository;

    @Override
    public ProductLineResponse createProductLine(ProductLineRequest request) {

        if (request.getProductLine() == null || request.getProductLine().isBlank()) {
            throw new BadRequestException("Product line cannot be empty");
        }

        if (productLineRepository.existsById(request.getProductLine())) {
            throw new ResourceAlreadyExistsException("Product line already exists");
        }

        ProductLine pl = new ProductLine();
        pl.setProductLine(request.getProductLine());
        pl.setTextDescription(request.getTextDescription());
        pl.setHtmlDescription(request.getHtmlDescription());

        return mapToResponse(productLineRepository.save(pl));
    }

    @Override
    public void deleteProductLine(String productLine) {

        ProductLine pl = productLineRepository.findById(productLine)
                .orElseThrow(() -> new ResourceNotFoundException("Product line not found"));

        List<Product> products = productRepository.findByProductLineProductLine(productLine);

        if (!products.isEmpty()) {
            throw new DatabaseException("Cannot delete product line with existing products");
        }

        productLineRepository.delete(pl);
    }

    @Override
    public Page<ProductLineResponse> getAllProductLines(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ProductLine> pageData = productLineRepository.findAll(pageable);

        if (pageData.isEmpty()) {
            throw new ResourceNotFoundException("No product lines found");
        }

        return pageData.map(this::mapToResponse);
    }

    @Override
    public ProductLineResponse getProductLineById(String productLine) {

        ProductLine pl = productLineRepository.findById(productLine)
                .orElseThrow(() -> new ResourceNotFoundException("Product line not found"));

        return mapToResponse(pl);
    }

    @Override
    public ProductLineResponse updateProductLine(String productLine, ProductLineRequest request) {

        ProductLine existing = productLineRepository.findById(productLine)
                .orElseThrow(() -> new ResourceNotFoundException("Product line not found"));

        existing.setTextDescription(request.getTextDescription());
        existing.setHtmlDescription(request.getHtmlDescription());

        return mapToResponse(productLineRepository.save(existing));
    }

    @Override
    public List<ProductResponse> getProductsByLine(String productLine) {

        if (!productLineRepository.existsById(productLine)) {
            throw new ResourceNotFoundException("Product line not found");
        }

        List<Product> products = productRepository.findByProductLineProductLine(productLine);

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for this product line");
        }

        return products.stream()
                .map(this::mapProductToResponse)
                .toList();
    }

    private ProductLineResponse mapToResponse(ProductLine pl) {
        return new ProductLineResponse(
                pl.getProductLine(),
                pl.getTextDescription(),
                pl.getHtmlDescription()
        );
    }

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
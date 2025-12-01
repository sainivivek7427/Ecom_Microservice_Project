package com.micro.product.serviceimpl;

import com.micro.product.entity.Product;
import com.micro.product.repository.ProductRepository;
import com.micro.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        long now = System.currentTimeMillis();
        product.setCreatedDate(now);
        product.setUpdatedDate(now);
        product.setId(UUID.randomUUID().toString());
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public Product updateProduct(String id, Product updatedProduct) {
        Product existing = getProductById(id);
        existing.setPrice(updatedProduct.getPrice());
        existing.setDiscountPercent(updatedProduct.getDiscountPercent());
        existing.setDiscountPrice(updatedProduct.getDiscountPrice());
        existing.setUpdatedDate(System.currentTimeMillis());
        return productRepository.save(existing);
    }

    @Override
    public void deleteProductById(String id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    @Override
    public List<Product> getProductsByCategoryId(String categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> getProductsBySubCategoryId(String subCategoryId) {
        return productRepository.findBySubcategoryId(subCategoryId);
    }

    @Override
    public List<Product> getProductsByDiscount(Double discountPercent) {
        return productRepository.findByDiscountPercent(discountPercent);
    }

    @Override
    public List<Product> getNewArrivalProducts(Long fromEpoch) {
        return productRepository.findByCreatedDateGreaterThanEqual(fromEpoch);
    }

    @Override
    public List<Product> getTodayHotProducts() {
        long startOfDay = LocalDate.now().atStartOfDay(ZoneId.systemDefault())
                .toInstant().toEpochMilli();
        return productRepository.findAll().stream()
                .filter(p -> p.getCreatedDate() >= startOfDay)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getProductsByDiscountRange(double min, double max) {
        return productRepository.findByDiscountRange(min, max);
    }
}

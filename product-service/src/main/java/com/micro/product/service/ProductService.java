package com.micro.product.service;

import com.micro.product.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ProductService {

    Product saveProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(String id);
    Product updateProduct(String id, Product updatedProduct);
    void deleteProductById(String id);

    List<Product> getProductsByCategoryId(String categoryId);
    List<Product> getProductsBySubCategoryId(String subCategoryId);

    List<Product> getProductsByDiscount(Double discountPercent);
    List<Product> getNewArrivalProducts(Long fromEpoch);
    List<Product> getTodayHotProducts();
    List<Product> getProductsByDiscountRange(double min, double max);

    public Boolean getCategoryExist(String categoryId);

    public void importProductsFromCsv(MultipartFile file);
}

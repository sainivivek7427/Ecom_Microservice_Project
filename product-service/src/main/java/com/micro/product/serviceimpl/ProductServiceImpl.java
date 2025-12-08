package com.micro.product.serviceimpl;

import com.micro.product.client.CategoryClient;

import com.micro.product.dto.Category;
import com.micro.product.client.SubCategoryClient;
import com.micro.product.dto.Category;
import com.micro.product.dto.SubCategory;
import com.micro.product.entity.Product;
import com.micro.product.exception.ProductNotFoundException;
import com.micro.product.repository.ProductRepository;
import com.micro.product.service.ProductService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SubCategoryClient subCategoryClient;

    @Autowired
    private CategoryClient categoryClient;

    @Override
    public Product saveProduct(Product product) {
        long now = System.currentTimeMillis();
        product.setCreatedDate(now);
        product.setUpdatedDate(now);
        product.setId(UUID.randomUUID().toString());
        return productRepository.save(product);
    }


    public void importProductsFromCsv(MultipartFile file) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(reader);

            for (CSVRecord record : records) {

                Product p = new Product();
                p.setId(UUID.randomUUID().toString());
                p.setName(record.get("name"));
                p.setDescription(record.get("description"));
                p.setPrice(Double.valueOf(record.get("price")));
                p.setDiscountPercent(parseDouble(record.get("discountPercent")));
                p.setDiscountPrice(parseDouble(record.get("discountPrice")));
                p.setBrand(record.get("brand"));
                p.setStockQuantity(parseInt(record.get("stockQuantity")));

                // Image fields
                String imagePath = record.get("imagePath").trim();
                String imageName = record.get("imageName");

                p.setImageName(imageName);

                // Read image and convert to byte[]
                p.setImage(readImageAsBytes(imagePath));


                // Call Category Service to get Category ID
                Category category = categoryClient.getCategoryById(record.get("categoryId")).getBody();
                System.out.println("category "+category);
                if (category == null) {
                    throw  new NullPointerException("Category Data Not Found!");
//                    return ResponseEntity.badRequest().body("Category Data Not Found!");
                }

                SubCategory subCategory = subCategoryClient.getSubCategoryById(record.get("subcategoryId")).getBody();
                System.out.println("subcategory "+subCategory);
                if (subCategory == null) {
                    throw new NullPointerException("subCategory Data Not Found!");
//                    return ResponseEntity.badRequest().body("subCategory Data Not Found!");
                }

                p.setCategoryId(category.getId());
                p.setSubcategoryId(subCategory.getId());


                p.setActive(true);
                p.setCreatedDate(System.currentTimeMillis());
                p.setUpdatedDate(System.currentTimeMillis());

                productRepository.save(p);
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    private Double parseDouble(String val) {
        return (val == null || val.isEmpty()) ? null : Double.valueOf(val);
    }

    private Integer parseInt(String val) {
        return (val == null || val.isEmpty()) ? null : Integer.valueOf(val);
    }

    private byte[] readImageAsBytes(String path) {
        try {
            Path p = Paths.get(path);
            return Files.readAllBytes(p);
//            ClassPathResource resource = new ClassPathResource(path);
//            return resource.getInputStream().readAllBytes();
        } catch (Exception e) {
            System.out.println("⚠ Image file not found: " + path);
            return null;
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
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

        Category category = categoryClient.getCategoryById(categoryId).getBody();

        if (category == null) {
            throw new ProductNotFoundException("No products found for category: " + categoryId);
        }

        return productRepository.findByCategoryId(categoryId.trim());
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

    @Override
    public Boolean getCategoryExist(String categoryId){
        List<Product> productByCategoryId = productRepository.findByCategoryId(categoryId);
        return !productByCategoryId.isEmpty();
    }
}

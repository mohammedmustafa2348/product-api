package com.example.demo.service;

import com.example.demo.dao.ProductRepository;
import com.example.demo.dao.specs.ProductSpecification;
import com.example.demo.dao.specs.SearchCriteria;
import com.example.demo.dao.specs.SearchOperation;
import com.example.demo.model.Product;
import com.example.demo.model.ProductModel;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.stereotype.Service;

/**
   * Product Service Implementation.
   */
@Service
public class ProductServiceImpl implements ProductService {
  /**
   * Autowiring the Product Repository
   */
  @Autowired
  private ProductRepository productRepository;

  /**
   * Get All Products.
   * @return List of all products.
   */
  @Override
  public List<Product> getAllProducts() {
    return (List<Product>) productRepository.findAll();
  }

  /**
   * Get All Products.
   * @param request The product model to be matched
   * @return List of all products that matches the passed productModel values.
   */
  @Override
  public List<Product> getAllProducts(ProductModel request) {
    List<Product> productList = new ArrayList<>();

    ProductSpecification msTitleRating = new ProductSpecification();

    if (null != request.getType()) {
      msTitleRating.add(new SearchCriteria("type", request.getType(), SearchOperation.MATCH));
    }
    if (null != request.getMin_price()) {
      msTitleRating.add(new SearchCriteria("price", Double.parseDouble(request.getMin_price()), SearchOperation.GREATER_THAN_EQUAL));
    }
    if (null != request.getMax_price()) {
      msTitleRating.add(new SearchCriteria("price", Double.parseDouble(request.getMax_price()), SearchOperation.LESS_THAN_EQUAL));
    }
    if (null != request.getCity()) {
      msTitleRating.add(new SearchCriteria("address", request.getCity(), SearchOperation.MATCH));
    }
    if (null!= request.getGb_limit_min()) {
      msTitleRating.add(new SearchCriteria("type", "subscription", SearchOperation.MATCH));
      msTitleRating.add(new SearchCriteria("gbLimit", Integer.parseInt(request.getGb_limit_min()), true, SearchOperation.GREATER_THAN_EQUAL));
    }
    if (null != request.getGb_limit_max()) {
      msTitleRating.add(new SearchCriteria("type", "subscription", SearchOperation.MATCH));
      msTitleRating.add(new SearchCriteria("gbLimit", Integer.parseInt(request.getGb_limit_max()), true, SearchOperation.LESS_THAN_EQUAL));
    }
    if (null != request.getColor()) {
      msTitleRating.add(new SearchCriteria("type", "phone", SearchOperation.MATCH));
      msTitleRating.add(new SearchCriteria("color", request.getColor(), SearchOperation.MATCH));
    }
    productList = productRepository.findAll(msTitleRating);
    return productList;
  }

  /**
   * Get Product By Id.
   * @param id Id
   * @return Product
   */
  @Override
  public Product getProductById(final Long id) {
    return productRepository.findById(id).get();
  }

  /**
   * Save Product.
   * @param product Product to save
   * @return Saved Product
   */
  @Override
  public Product saveProduct(final Product product) {
    return productRepository.save(product);
  }

  /**
   * Save Product.
   * @param productList Product to save
   * @return Saved Product
   */
  @Override
  public void saveAllProducts(List<Product> productList) {
    productRepository.saveAll(productList);
  }

  /**
   * Update Product.
   * @param id Id
   * @param productToUpdate Product to Update
   * @return Updated Product
   */
  @Override
  public Product updateProductById(final Long id, final Product productToUpdate) {
    // Fetch the Product from db
    Product productFromDb = productRepository.findById(id).get();
    productFromDb.setType(productToUpdate.getType());
    productFromDb.setColor(productToUpdate.getProperties());
    productFromDb.setPrice(productToUpdate.getPrice());
    productFromDb.setAddress(productToUpdate.getAddress());
    return productRepository.save(productFromDb);
  }

  /**
   * Delete Product by Id.
   * @param id Id
   */
  @Override
  public void deleteProductById(final Long id) {
    productRepository.deleteById(id);
  }

  /**
   * Delete all products in the database
   */
  @Override
  public void deleteAllProducts() {
    productRepository.deleteAll();
  }
}

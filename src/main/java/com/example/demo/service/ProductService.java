package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.model.ProductModel;
import java.util.List;

/**
 * Product Service.
 */
public interface ProductService {

  /**
   * Get All Products.
   * @return List of all Products.
   */
  List<Product> getAllProducts();

  /**
   * Get All Products that matches request model.
   * @param request The product model based on which products will be filtered
   * @return List of all Products.
   */
  List<Product> getAllProducts(ProductModel request);

  /**
   * Get Product By Id.
   * @param id Id
   * @return Product
   */
  Product getProductById(Long id);

  /**
   * Save Product.
   * @param product Product to save
   * @return Saved Product
   */
  Product saveProduct(Product product);


  /**
   * Save Product.
   * @param productList Product to save
   * @return Saved Product
   */
  void saveAllProducts(List<Product> productList);

  /**
   * Update Product.
   * @param id Id
   * @param productToUpdate Product to Update
   * @return Updated Product
   */
  Product updateProductById(Long id, Product productToUpdate);

  /**
   * Delete Product by Id.
   * @param id Id
   */
  void deleteProductById(Long id);

  /**
   * Delete all products
   */
  void deleteAllProducts();
 }

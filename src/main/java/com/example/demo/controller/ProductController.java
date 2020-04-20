package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.model.ProductModel;
import com.example.demo.service.ProductService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

/**
 * Product Rest Controller.
 */
@RestController
@RequestMapping("/product")
public class ProductController {
  /**
   * Autowiring the Product Service.
   */
  @Autowired
  private ProductService productService;

  /**
   * get Api to return list of all products.
   * @param productRequestModel the model object used to filter the response
   * @return List of Products
   */
  @GetMapping()
  public ResponseEntity<List<Product>> getAllProducts( @ModelAttribute("productRequestModel") Optional<ProductModel> productRequestModel) {
    List<Product> productList;
    if(productRequestModel.isPresent()){
      productList = productService.getAllProducts(productRequestModel.get());
    }
    else{
      productList = productService.getAllProducts();
    }
    return new ResponseEntity<>(productList, HttpStatus.OK);
  }

  /**
   * get Api to return the Product by Id.
   * @param id Id
   * @return Products
   */
  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(
      @PathVariable("id") final Long id) {
    Product product = productService.getProductById(id);
    return new ResponseEntity<>(product, HttpStatus.OK);
  }

  /**
   * Api to save new Product.
   * @param product Product to add
   * @return Saved Product
   */
  @PostMapping("/save")
  public ResponseEntity<Product> saveProduct(@RequestBody final  Product product) {
    if(null != product.getProperties()){
      System.out.println(product.toString());
    }
    Product savedProduct = productService.saveProduct(product);
    return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
  }

  /**
   * Api to update an existing Product.
   * @param id Id to update
   * @param productToUpdate Product to update
   * @return Updated Product
   */
  @PutMapping("/update/{id}")
  public ResponseEntity<Product> updateProductById(
      @PathVariable("id") final Long id,
      @RequestBody Product productToUpdate) {
    Product updatedProduct = productService.updateProductById(id, productToUpdate);
    return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
  }

  /**
   * Api to delete a product.
   * @param id Id to delete
   * @return Success Message
   */
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteProductById(
      @PathVariable("id") final Long id) {
    productService.deleteProductById(id);
    return new ResponseEntity<>("Success", HttpStatus.OK);
  }

  /**
   * Api to delete all products.
   */
  @DeleteMapping("/delete")
  public ResponseEntity<String> deleteAllProducts() {
    productService.deleteAllProducts();
    return new ResponseEntity<>("Success", HttpStatus.OK);
  }
 }
package com.example.demo.integration;

import com.example.demo.DemoApplication;
import com.example.demo.model.Product;
import com.example.demo.model.ProductModel;
import com.example.demo.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

  private static List<Product> productList = new ArrayList<>();

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private ProductService productService;

  @LocalServerPort
  private int port;

  private String getRootUrl() {
    return "http://localhost:" + port;
  }

  @Test
  public void contextLoads() {
  }

  @Before
  public void cleanUpDb(){
    productService.deleteAllProducts();
    addSomeProducts();
  }

  @Test
  public void testGetAllProducts() {

    HttpHeaders headers = new HttpHeaders();
    ProductModel productModel = new ProductModel();
    HttpEntity<ProductModel> request = new HttpEntity<>(productModel, headers);

    ResponseEntity<List> response = restTemplate.exchange(getRootUrl() + "/product",
        HttpMethod.GET, request, List.class);
    Assert.assertNotNull(response.getBody());
    Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    Assert.assertEquals(response.getBody().size(),  productList.size());
  }

  @Test
  public void testGetAllProductsFilteredByCity() {

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<?> request = new HttpEntity<>(null, headers);

    ResponseEntity<List> response = restTemplate.exchange(getRootUrl() + "/product?city=Stockholm", HttpMethod.GET, request, List.class);
    Assert.assertNotNull(response.getBody());
    Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    Assert.assertEquals(response.getBody().size(),  productList.stream().filter(p -> p.getAddress().contains("Stockholm")).count());
  }

  @Test
  public void testGetAllProductsFilteredByType() {

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<?> request = new HttpEntity<>(null, headers);

    ResponseEntity<List> response = restTemplate.exchange(getRootUrl() + "/product?type=phone", HttpMethod.GET, request, List.class);
    Assert.assertNotNull(response.getBody());
    Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    Assert.assertEquals(response.getBody().size(),  productList.stream().filter(p -> p.getType().equals("phone")).count());
  }

  @Test
  public void testGetAllProductsFilteredByMinPrice() {
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<?> request = new HttpEntity<>(null, headers);

    ResponseEntity<List> response = restTemplate.exchange(getRootUrl() + "/product?min_price=100.0", HttpMethod.GET, request, List.class);
    Assert.assertNotNull(response.getBody());
    Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    Assert.assertEquals(response.getBody().size(),  productList.stream().filter(p -> p.getPrice() >= 100.0).count());
  }

  @Test
  public void testGetAllProductsFilteredByMaxPrice() {

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<?> request = new HttpEntity<>(null, headers);

    ResponseEntity<List> response = restTemplate.exchange(getRootUrl() + "/product?max_price=200.0", HttpMethod.GET, request, List.class);

    Assert.assertNotNull(response.getBody());
    Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    Assert.assertEquals(response.getBody().size(),  productList.stream().filter(p -> p.getPrice() <= 200.0).count());
  }

  @Test
  public void testGetAllPhonesFilteredByColor() {
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<?> request = new HttpEntity<>(null, headers);

    ResponseEntity<List> response = restTemplate.exchange(getRootUrl() + "/product?color=blue", HttpMethod.GET, request, List.class);

    Assert.assertNotNull(response.getBody());
    Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    Assert.assertEquals(response.getBody().size(),  productList.stream().filter(p -> p.getProperties().contains("blue")).count());
  }

  @Test
  public void testGetAllPhonesFilteredByGbMinimumLimit() {
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<?> request = new HttpEntity<>(null, headers);

    ResponseEntity<List> response = restTemplate.exchange(getRootUrl() + "/product?gb_limit_min=100", HttpMethod.GET, request, List.class);
    Assert.assertNotNull(response.getBody());
    Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    Assert.assertEquals(response.getBody().size(),  1);
  }

  @Test
  public void testGetAllPhonesFilteredByGbMaximumLimit() {
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<?> request = new HttpEntity<>(null, headers);

    ResponseEntity<List> response = restTemplate.exchange(getRootUrl() + "/product?gb_limit_max=20", HttpMethod.GET, request, List.class);
    Assert.assertNotNull(response.getBody());
    Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    Assert.assertEquals(response.getBody().size(),  2);
  }

  @Test
  public void testGetAllPhonesFilteredByType_City_MinPrice() {
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<?> request = new HttpEntity<>(null, headers);

    ResponseEntity<List> response = restTemplate.exchange(getRootUrl() + "/product?type=phone&city=Stockholm&min_price=250", HttpMethod.GET, request, List.class);

    Assert.assertNotNull(response.getBody());
    Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    long count = productList.stream().
        filter(p -> p.getType().equals("phone")
            && p.getAddress().contains("Stockholm")
            && p.getPrice() >= 250).
        count();
    Assert.assertEquals(response.getBody().size(), count);
  }

  public void addSomeProducts(){
    productList = new ArrayList<>();
    productList.add(new Product(null, "phone", "green", null, 250.50, "Kungsgatan, Stockholm"));
    productList.add(new Product(null, "phone", "blue", null, 300.00, "Kungsgatan, Stockholm"));
    productList.add(new Product(null, "phone", "white", null, 300.00, "Kungsgatan, Stockholm"));
    productList.add(new Product(null, "subscription", null, 10, 100.50, "Gamlastan, Gothenburg"));
    productList.add(new Product(null, "subscription", null, 20, 50.50, "Gamlastan, Linköping"));
    productList.add(new Product(null, "subscription", null, 100, 100.50, "Gamlastan, Stockholm"));
    productService.saveAllProducts(productList);
  }

}
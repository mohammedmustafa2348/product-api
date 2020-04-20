package com.example.demo.util;

import com.example.demo.model.Product;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**Util file to manipulate input csv data*/
public class CsvUtil {

  private static final Logger logger = LoggerFactory.getLogger(CsvUtil.class);

  public static void main(String args[]){
    String fileName = "";
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter file name:");
    fileName = scanner.next();
    List<String> insertQueryLines = convertCsvToInsertQueryStatements(fileName);
    insertQueryLines.stream().forEach(System.out::println);
  }

  public static List<String> convertCsvToInsertQueryStatements(String csvFileName){
    return createInsertQueryLines(getProductListFromCsvInputLine(readFromInputCsvFile(csvFileName)));
  }

  /**Create insert query lines for the list of products*/
  public static List<String> createInsertQueryLines(List<Product> productList){
    List<String> insertQueryLines = new ArrayList<>();
    for (Product product : productList) {
      String property = product.getProperties().split(":")[1];
      if("phone".equals(product.getType())){
        insertQueryLines.add(getPhoneInsertStatement(property, product.getPrice(), product.getAddress()));
      }
      else{
        int gbLimit = Integer.parseInt(property);
        insertQueryLines.add(getSubscriptionInsertStatement(gbLimit, product.getPrice(), product.getAddress()));
      }
    }
    return insertQueryLines;
  }

  public static List<Product> getProductListFromCsvInputLine(List<String> lines){
    List<Product> productList = new ArrayList<>();
    if(null != lines){
      for (String line: lines) {

        Product product = getProductFromInputCsvLine(line);
        if(null != product){
          productList.add(product);
        }
      }
    }
    return productList;
  }

  /**Generates a list of product objects from input csv file
   * @param fileName input csv file name
   * @return list of products or null if file does not exist*/
  public static List<String> readFromInputCsvFile(String fileName) {
    //read file into stream, try-with-resources
    try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
      List<String> inputList = stream.collect(Collectors.toList());
      return inputList;
    } catch (IOException e) {
      logger.error("Unable to read file:" + fileName);
      logger.error(e.getMessage());
    }
    return null;
  }

  /**Creates a product object from csv input line
   * @param inputLine the csv input line in format: Product type,Product properties,Price,Store address
   * @return Product created based in input line or null if not a valid line*/
  public static Product getProductFromInputCsvLine(String inputLine){
    Product product = null;
    //ignore the comment
    if(null!= inputLine && !inputLine.startsWith("#")){
      //input line format : Product type,Product properties,Price,Store address
      String[] productParams = inputLine.split(",");
      if(productParams.length == 5){ //there is a comma in address
        try{
          product = new Product();
          product.setType(productParams[0].trim());
          if("phone".equals(product.getType())){
            product.setColor(productParams[1].trim());
          }
          else{
            product.setGbLimit(productParams[1].trim());
          }
          product.setPrice(Double.parseDouble( productParams[2].trim()));
          product.setAddress(productParams[3]+", " +productParams[4]);
          return product;
        }
        catch(NumberFormatException e){
          logger.warn("Invalid input line in csv : " + inputLine);
        }
      }
      else{
        logger.warn("Invalid input line in csv : " + inputLine);
      }
    }
    return product;
  }

  public static String getPhoneInsertStatement(String color, double price, String address ){
    String insertStatement = "insert into product (type, color, price, address) values(";
    String outputLine = insertStatement +
        "'phone', '" +
        color + "', " +
        price + ", '" +
        address.replace("\"", "") + "');";
    return outputLine;
  }

  public static String getSubscriptionInsertStatement(int gbLimit, double price, String address ){
    String insertStatement = "insert into product (type, gb_limit, price, address) values(";
    String outputLine = insertStatement +
        "'subscription', " +
        gbLimit + ", " +
        price + ", '" +
        address.replace("\"", "") + "');";
    return outputLine;
  }

}

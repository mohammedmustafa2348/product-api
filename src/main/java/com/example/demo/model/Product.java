package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**Entity class that maps to the Product.*/
@Entity
public class Product {



  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String type;
  private String color;
  private Integer gbLimit;
  private Double price;
  private String address;

  public Product(){}

  public Product(Long id, String type, String color, Integer gbLimit, Double price, String address) {
    setId(id);
    setType(type);
    setColor(color);
    setGbLimit(gbLimit);
    setPrice(price);
    setAddress(address);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  /**if input is in format color:<color>*/
  public void setColor(String color) {
    if(null != color){
      if(color.contains(":")){
        try{
          this.color = color.split(":")[1];
        }
        catch(ArrayIndexOutOfBoundsException e){
        }
      }
      else{
        this.color = color;
      }
    }
  }

  /**if input is in format gb_limit:<num>*/
  public void setGbLimit(String gbLimit){
    if(null != gbLimit){
      if(gbLimit.contains(":")){
        try{
          this.gbLimit = Integer.parseInt(gbLimit.split(":")[1]);
        }
        catch(ArrayIndexOutOfBoundsException e){
        }
     }else{
        this.gbLimit = Integer.parseInt(gbLimit);
      }
    }
    else{
      this.gbLimit = 10; //default value
    }
  }

  public void setGbLimit(Integer gbLimit) {
    this.gbLimit = gbLimit;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  /**To display properties in the response.
   * There is no getters for color or gb_limit, so they will not be displayed in the json response*/
  public String getProperties(){
    if(type.equals("phone")){
      return "color:" + this.color;
    }
    //for subscription
    return "gb_limit:" + this.gbLimit;
  }

  /**Set the properties color or gbLimit using properties value.
   * Properties in the format:  */
  public void setProperties(String properties){
    if(null != properties){
      if(properties.contains("color")){
        setColor(properties);
      }
      else{
        setGbLimit(properties);
      }
    }
  }
}

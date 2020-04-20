package com.example.demo.model;

import java.util.StringTokenizer;

public class ProductModel {
  private String type;
  private String min_price;
  private String max_price;
  private String city;
  private String property;
  private String color;
  private String gb_limit_min;
  private String gb_limit_max;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMin_price() {
    return min_price;
  }

  public void setMin_price(String min_price) {
    this.min_price = min_price;
  }

  public String getMax_price() {
    return max_price;
  }

  public void setMax_price(String max_price) {
    this.max_price = max_price;
  }

  public String getProperty() {
    return property;
  }

  public void setProperty(String property) {
    this.property = property;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getGb_limit_min() {
    return gb_limit_min;
  }

  public void setGb_limit_min(String gb_limit_min) {
    this.gb_limit_min = gb_limit_min;
  }

  public String getGb_limit_max() {
    return gb_limit_max;
  }

  public void setGb_limit_max(String gb_limit_max) {
    this.gb_limit_max = gb_limit_max;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }
}

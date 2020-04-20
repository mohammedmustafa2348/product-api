package com.example.demo.dao.specs;

public class SearchCriteria {
  private String key;
  private Object value;
  private Boolean isNumericValue;
  private SearchOperation operation;

  public SearchCriteria() {
  }

  public SearchCriteria(String key, Object value, SearchOperation operation) {
    this.key = key;
    this.value = value;
    this.operation = operation;
    this.isNumericValue = false;
  }

  public SearchCriteria(String key, Object value, Boolean isNumericValue, SearchOperation operation) {
    this.key = key;
    this.value = value;
    this.operation = operation;
    this.isNumericValue = isNumericValue;
  }

  // getters and setters, toString()
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public SearchOperation getOperation() {
    return operation;
  }

  public void setOperation(SearchOperation operation) {
    this.operation = operation;
  }

  public Boolean isNumericValue() { return isNumericValue;   }

  public void setIsNumericValue(Boolean numericValue) { isNumericValue = numericValue;  }

  @Override
  public String toString() {
    return "SearchCriteria{" +
        "key='" + key + '\'' +
        ", value=" + value +
        ", IsNumber=" + isNumericValue +
        ", operation=" + operation +
        '}';
  }
}
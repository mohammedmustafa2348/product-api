package com.example.demo.dao.specs;

import com.example.demo.model.Product;
import javax.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification implements Specification<Product> {

  private List<SearchCriteria> searchCriteriaList;

  public ProductSpecification() {
    this.searchCriteriaList = new ArrayList<>();
  }

  public void add(SearchCriteria criteria) {
    searchCriteriaList.add(criteria);
  }

  @Override
  public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

    //create a new predicate list
    List<Predicate> predicates = new ArrayList<>();

    //add add criteria to predicates
    for (SearchCriteria criteria : searchCriteriaList) {
      Path<Object> key = root.get(criteria.getKey());
      String value = criteria.getValue().toString();
      switch(criteria.getOperation()){
        case LESS_THAN_EQUAL:
        case LESS_THAN:
        case GREATER_THAN:
        case GREATER_THAN_EQUAL:
          predicates.add(getPredicate(root, builder, criteria));
          break;
        case NOT_EQUAL:
          predicates.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
          break;
        case EQUAL:
          predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
          break;
        case MATCH:
          predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),  "%" + value.toLowerCase() + "%"));
          break;
        case MATCH_END:
          predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), value.toLowerCase() + "%"));
          break;
        case MATCH_START:
          predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), "%" + value.toLowerCase()));
          break;
        case IN:
          predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
          break;
        case NOT_IN:
          predicates.add(builder.not(root.get(criteria.getKey())).in(criteria.getValue()));
          break;
      }
    }

    return builder.and(predicates.toArray(new Predicate[0]));
  }

  private Predicate getPredicate(Root<Product> root, CriteriaBuilder builder, SearchCriteria criteria) {
    Predicate predicate = null;
    String criteriaValue = criteria.getValue().toString();
    if(SearchOperation.GREATER_THAN.equals(criteria.getOperation())){
      if (criteria.isNumericValue()) {
        predicate = builder.gt(root.get(criteria.getKey()), getNumber(criteriaValue));
      }
      predicate=  builder.greaterThan(root.get(criteria.getKey()), criteriaValue);
    }
    else if(SearchOperation.GREATER_THAN_EQUAL.equals(criteria.getOperation())){
      if (criteria.isNumericValue()) {
        predicate = builder.ge(root.get(criteria.getKey()), getNumber(criteriaValue));
      }
      predicate=  builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteriaValue);
    }
    else if(SearchOperation.LESS_THAN.equals(criteria.getOperation())){
      if (criteria.isNumericValue()) {
        predicate = builder.lt(root.get(criteria.getKey()), getNumber(criteriaValue));
      }
      predicate=  builder.lessThan(root.get(criteria.getKey()), criteriaValue);
    }
    else if(SearchOperation.LESS_THAN_EQUAL.equals(criteria.getOperation())){
      if (criteria.isNumericValue()) {
        predicate = builder.le(root.get(criteria.getKey()), getNumber(criteriaValue));
      }
      predicate=  builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteriaValue);
    }
    return predicate;
  }

  public Number getNumber(Object value){
    Number num = null;
    if ( value instanceof String ) {
      try {
        double d = Double.valueOf( (String) value );
        num = (Double) d; // Auto-boxing
      }
      catch ( NumberFormatException e ) {

      }
    }
    return num;
  }
}

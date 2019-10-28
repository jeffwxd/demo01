package com.example.demo01.utils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 *
 */
public class PredicateUtils {

    public static Predicate buildPredicate(CriteriaBuilder cb, String type, Path<?> property, Object value){
        Predicate predicate = null;
        if(value != null){
            if (type.equals(FilterConstants.EQ)) {
                predicate = cb.equal(property, value);
            } else if (type.equals(FilterConstants.NE)) {
                predicate = cb.notEqual(property, value);
            } else if (type.equals(FilterConstants.GT)) {
                predicate = cb.greaterThan(property.as(Integer.class), (Integer) value);
            } else if (type.equals(FilterConstants.GTSTRING)) {
                predicate = cb.greaterThan(property.as(String.class), (String) value);
            } else if (type.equals(FilterConstants.GTLONG)) {
                predicate = cb.greaterThan(property.as(Long.class), (Long) value);
            } else if (type.equals(FilterConstants.GTDOUBLE)) {
                predicate = cb.greaterThan(property.as(Double.class), (Double) value);
            } else if (type.equals(FilterConstants.GTDATE)) {
                predicate = cb.greaterThan(property.as(Date.class), (Date) value);
            } else if (type.equals(FilterConstants.GE)) {
                predicate = cb.greaterThanOrEqualTo(property.as(Integer.class), (Integer) value);
            } else if (type.equals(FilterConstants.GESTRING)) {
                predicate = cb.greaterThanOrEqualTo(property.as(String.class), (String) value);
            } else if (type.equals(FilterConstants.GELONG)) {
                predicate = cb.greaterThanOrEqualTo(property.as(Long.class), (Long) value);
            } else if (type.equals(FilterConstants.GEDOUBLE)) {
                predicate = cb.greaterThanOrEqualTo(property.as(Double.class), (Double) value);
            } else if (type.equals(FilterConstants.GEDATE)) {
                predicate = cb.greaterThanOrEqualTo(property.as(Date.class), (Date) value);
            } else if (type.equals(FilterConstants.LT)) {
                predicate = cb.lessThan(property.as(Integer.class), (Integer) value);
            } else if (type.equals(FilterConstants.LTSTRING)) {
                predicate = cb.lessThan(property.as(String.class), (String) value);
            } else if (type.equals(FilterConstants.LTLONG)) {
                predicate = cb.lessThan(property.as(Long.class), (Long) value);
            } else if (type.equals(FilterConstants.LTDOUBLE)) {
                predicate = cb.lessThan(property.as(Double.class), (Double) value);
            } else if (type.equals(FilterConstants.LTDATE)) {
                predicate = cb.lessThan(property.as(Date.class), (Date) value);
            } else if (type.equals(FilterConstants.LE)) {
                predicate = cb.lessThanOrEqualTo(property.as(Integer.class), (Integer) value);
            } else if(type.equals(FilterConstants.LESTRING)){
                predicate = cb.lessThanOrEqualTo(property.as(String.class), (String) value);
            } else if (type.equals(FilterConstants.LELONG)) {
                predicate = cb.lessThanOrEqualTo(property.as(Long.class), (Long) value);
            } else if (type.equals(FilterConstants.LEDOUBLE)) {
                predicate = cb.lessThanOrEqualTo(property.as(Double.class), (Double) value);
            } else if (type.equals(FilterConstants.LEDATE)) {
                predicate = cb.lessThanOrEqualTo(property.as(Date.class), (Date) value);
            } else if (type.equals(FilterConstants.LIKE)) {
                predicate = cb.like(property.as(String.class), String.valueOf(value));
            } else if (type.equals(FilterConstants.NOTLIKE)) {
                predicate = cb.notLike(property.as(String.class), String.valueOf(value));
            } else if (type.equals(FilterConstants.IN)) {
                Collection collection = (Collection) value;
                if (collection.size() > 0) {
                    CriteriaBuilder.In in = cb.in(property);
                    Iterator iterator = collection.iterator();
                    while (iterator.hasNext()) {
                        in.value(iterator.next());
                    }
                    predicate = in;
                }
            } else if (type.equals(FilterConstants.NOTIN)) {
                Collection collection = (Collection) value;
                if (collection.size() > 0) {
                    CriteriaBuilder.In in = cb.in(property);
                    Iterator iterator = collection.iterator();
                    while (iterator.hasNext()) {
                        in.value(iterator.next());
                    }
                    predicate = cb.not(in);
                }
            } else if (type.equals(FilterConstants.BETWEEN)) {
                Map<String, Integer> map = (Map<String, Integer>) value;
                Integer lo = map.get(FilterConstants.LO);
                Integer go = map.get(FilterConstants.GO);
                predicate = cb.between(property.as(Integer.class), lo, go);
            }else if (type.equals(FilterConstants.BETWEENLONG)) {
                Map<String, Long> map = (Map<String, Long>) value;
                Long lo = map.get(FilterConstants.LO);
                Long go = map.get(FilterConstants.GO);
                predicate = cb.between(property.as(Long.class), lo, go);
            }else if (type.equals(FilterConstants.BETWEENSTRING)) {
                Map<String, String> map = (Map<String, String>) value;
                String lo = map.get(FilterConstants.LO);
                String go = map.get(FilterConstants.GO);
                predicate = cb.between(property.as(String.class), lo, go);
            }  else if (type.equals(FilterConstants.BETWEENDATE)) {
                Map<String, Date> map = (Map<String, Date>) value;
                Date lo = map.get(FilterConstants.LO);
                Date go = map.get(FilterConstants.GO);
                predicate = cb.between(property.as(Date.class), lo, go);
            }
        } else {
            if (type.equals(FilterConstants.ISNULL)) {
                predicate = cb.isNull(property);
            } else if (type.equals(FilterConstants.ISNOTNULL)) {
                predicate = cb.isNotNull(property);
            } else if (type.equals(FilterConstants.ISEMPTY)) {
                predicate = cb.equal(property, "");
            } else if (type.equals(FilterConstants.ISNOTEMPTY)) {
                predicate = cb.notEqual(property, "");
            }
        }
        return predicate;
    }
}

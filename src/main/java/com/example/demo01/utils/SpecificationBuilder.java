package com.example.demo01.utils;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * EveryBody think that the fluent api is the best.
 * This is the fluent api for spring jpa query api.
 *
 */
public class SpecificationBuilder<T> {

    private List<Specification> specifications;

    private SpecificationBuilder(){
        resetOrInit();
    }

    private void resetOrInit(){
        specifications = new ArrayList<>();
    }

    public static <T> SpecificationBuilder<T> builder(){
        return new SpecificationBuilder<T>();
    }

    /**
     * 当条件condition 为ture 时 执行consumer
     * @param condition
     * @param consumer
     * @return
     */
    public SpecificationBuilder<T> when(boolean condition, Consumer<SpecificationBuilder> consumer){
       if(condition) {
           consumer.accept(this);
       }
       return this;
    }
    public <M,V> SpecificationBuilder<T> when(boolean condition, BiConsumer<M,V> consumer,M m,V v){
       if(condition) {
           consumer.accept(m,v);
       }
       return this;
    }
   /* public <V> SpecificationBuilder<T> when(boolean condition,V obj,BiConsumer<SpecificationBuilder,V> consumer){
       if(condition) {
           consumer.accept(this,obj);
       }
       return this;
    }*/
   /* public <V> SpecificationBuilder<T> when(V obj, Function<V,Boolean> condition, Consumer<SpecificationBuilder> consumer){
        Boolean b = condition.apply(obj);
        if(b!=null&&b) {
           consumer.accept(this);
       }
       return this;
    }*/

    public SpecificationBuilder<T> eq(String propertyName, Object value){
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.EQ, value));
        }
        return this;
    }

    public SpecificationBuilder<T> ne(String propertyName, Object value){
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.NE, value));
        }
        return this;
    }

    /**
     * 等于
     *
     * @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> eqOrIsNull(String propertyName, Object value) {
        if(null==value) {
            specifications.add(buildSpecification(propertyName, FilterConstants.ISNULL, null));
        }else {
            specifications.add(buildSpecification(propertyName, FilterConstants.EQ, value));
        }
        return this;
    }

    /**
     * 不等于
     *
     * @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> neOrIsNotNull(String propertyName, Object value) {
        if(null==value) {
            specifications.add(buildSpecification(propertyName, FilterConstants.ISNOTNULL, null));
        }else {
            specifications.add(buildSpecification(propertyName, FilterConstants.NE, value));
        }
        return this;
    }

    /**
     * 大于
     *
     * @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> gt(String propertyName, Integer value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.GT, value));
        }
        return this;
    }
    /**
     * 大于
     *
     * @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> gt(String propertyName, String value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.GTSTRING, value));
        }
        return this;
    }

    /**
     * 大于
     *
     * @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> gt(String propertyName, Date value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.GTDATE, value));
        }
        return this;
    }

    /**
     * 大于
     *
     * @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> gt(String propertyName, Long value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.GTLONG, value));
        }
        return this;
    }

    /**
     * 大于
     *
     * @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> gt(String propertyName, Double value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.GTDOUBLE, value));
        }
        return this;
    }

    /**
     * 大于等于
     *  @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> ge(String propertyName, String value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.GESTRING, value));
        }
        return this;
    }

    /**
     * 大于等于
     *  @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> ge(String propertyName, Integer value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.GE, value));
        }
        return this;
    }

    /**
     * 大于等于
     *  @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> ge(String propertyName, Date value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.GEDATE, value));
        }
        return this;
    }

    /**
     * 大于等于
     *  @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> ge(String propertyName, Long value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.GELONG, value));
        }
        return this;
    }

    /**
     * 大于等于
     *  @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> ge(String propertyName, Double value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.GEDOUBLE, value));
        }
        return this;
    }

    /**
     * 小于
     *  @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> lt(String propertyName, Integer value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.LT, value));
        }
        return this;
    }
    /**
     * 小于
     *  @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> lt(String propertyName, String value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.LTSTRING, value));
        }
        return this;
    }

    /**
     * 小于
     *  @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> lt(String propertyName, Date value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.LTDATE, value));
        }
        return this;
    }

    /**
     * 小于
     *  @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> lt(String propertyName, Long value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.LTLONG, value));
        }
        return this;
    }

    /**
     * 小于
     *  @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> lt(String propertyName, Double value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.LTDOUBLE, value));
        }
        return this;
    }

    /**
     * 小于等于
     *  @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> le(String propertyName, String value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.LESTRING, value));
        }
        return this;
    }

    /**
     * 小于等于
     *  @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> le(String propertyName, Integer value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.LE, value));
        }
        return this;
    }

    /**
     * 小于等于
     *  @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> le(String propertyName, Date value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.LEDATE, value));
        }
        return this;
    }

    /**
     * 小于等于
     *  @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> le(String propertyName, Long value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.LELONG, value));
        }
        return this;
    }

    /**
     * 小于等于
     *  @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> le(String propertyName, Double value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.LEDOUBLE, value));
        }
        return this;
    }

    /**
     * 相似
     *  @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> like(String propertyName, String value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.LIKE, value));
        }
        return this;
    }

    /**
     * 不相似
     *  @param propertyName
     * @param value
     */
    public SpecificationBuilder<T> notLike(String propertyName, String value) {
        if(value != null){
            specifications.add(buildSpecification(propertyName, FilterConstants.NOTLIKE, value));
        }
        return this;
    }

    /**
     * 包含
     *  @param propertyName
     * @param value
     */
    @SuppressWarnings("rawtypes")
    public SpecificationBuilder<T> in(String propertyName, Collection value) {
        if(value != null && value.size() > 0){
            specifications.add(buildSpecification(propertyName, FilterConstants.IN, value));
        }
        return this;
    }

    /**
     * 不包含
     *  @param propertyName
     * @param value
     */
    @SuppressWarnings("rawtypes")
    public SpecificationBuilder<T> notIn(String propertyName, Collection value) {
        if(value != null && value.size() > 0){
            specifications.add(buildSpecification(propertyName, FilterConstants.NOTIN, value));
        }
        return this;
    }

    /**
     * 之间
     *  @param propertyName
     * @param lo
     * @param go
     */
    public SpecificationBuilder<T> between(String propertyName, Integer lo, Integer go) {
        if(lo != null && go != null){
            Map<String, Integer> between = new HashMap<String, Integer>(6);
            between.put(FilterConstants.LO, lo);
            between.put(FilterConstants.GO, go);
            specifications.add(buildSpecification(propertyName, FilterConstants.BETWEEN, between));
        }
        return this;
    }

    /**
     * 之间
     *  @param propertyName
     * @param lo
     * @param go
     */
    public SpecificationBuilder<T> between(String propertyName, Long lo, Long go) {
        if(lo != null && go != null){
            Map<String, Long> between = new HashMap<String, Long>(6);
            between.put(FilterConstants.LO, lo);
            between.put(FilterConstants.GO, go);
            specifications.add(buildSpecification(propertyName, FilterConstants.BETWEENLONG, between));
        }
        return this;
    }

    /**
     * 之间
     *  @param propertyName
     * @param lo
     * @param go
     */
    public SpecificationBuilder<T> between(String propertyName, String lo, String go) {
        if(lo != null && go != null){
            Map<String, String> between = new HashMap<String, String>(6);
            between.put(FilterConstants.LO, lo);
            between.put(FilterConstants.GO, go);
            specifications.add(buildSpecification(propertyName, FilterConstants.BETWEENSTRING, between));
        }
        return this;
    }

    /**
     * 之间
     *  @param propertyName
     * @param lo
     * @param go
     */
    public SpecificationBuilder<T> between(String propertyName, Date lo, Date go) {
        if(lo != null && go != null){
            Map<String, Date> between = new HashMap<String, Date>(6);
            between.put(FilterConstants.LO, lo);
            between.put(FilterConstants.GO, go);
            specifications.add(buildSpecification(propertyName, FilterConstants.BETWEENDATE, between));
        }
        return this;
    }

    /**
     * 空
     *
     * @param propertyName
     */
    public SpecificationBuilder<T> isNull(String propertyName) {
        specifications.add(buildSpecification(propertyName, FilterConstants.ISNULL, null));
        return this;
    }

    /**
     * 非空
     *
     * @param propertyName
     */
    public SpecificationBuilder<T> isNotNull(String propertyName) {
        specifications.add(buildSpecification(propertyName, FilterConstants.ISNOTNULL, null));
        return this;
    }

    /**
     * 空
     *
     * @param propertyName
     */
    public SpecificationBuilder<T> isEmpty(String propertyName) {
        specifications.add(buildSpecification(propertyName, FilterConstants.ISEMPTY, null));
        return this;
    }

    /**
     * 非空
     *
     * @param propertyName
     */
    public SpecificationBuilder<T> isNotEmpty(String propertyName) {
        specifications.add(buildSpecification(propertyName, FilterConstants.ISNOTEMPTY, null));
        return this;
    }

    /**
     * combine
     *
     * @param specification
     * @return
     */
    public SpecificationBuilder<T> combine(Specification<T>... specification) {
        for(Specification<T> spec : specification){
            specifications.add(spec);
        }
        return this;
    }

    public Specification<T> and(){
        AndSpecification<T> specification = new AndSpecification<>(specifications);
        resetOrInit();
        return specification;
    }

    public Specification<T> or(){
        OrSpecification<T> specification = new OrSpecification<>(specifications);
        resetOrInit();
        return specification;
    }

    private <T> Specification<T> buildSpecification(String propertyName, String operator, Object value) {
        return (root, query, cb) -> {
            Path<?> path = null;
            if (null!=propertyName && !propertyName.isEmpty()) {
                path = root.get(propertyName);
            }
            if(path == null){
                throw new RuntimeException(String.format("PropertyName[%s] not found on class : %s", propertyName, root.getJavaType().toString()));
            }
            return PredicateUtils.buildPredicate(cb, operator, path, value);
        };
    }
}

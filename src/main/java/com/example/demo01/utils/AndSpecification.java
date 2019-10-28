package com.example.demo01.utils;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * 组合
 *
 *
 */
public class AndSpecification<T> extends AbstractSpecification<T> {

    public AndSpecification(List<Specification> specifications) {
        super(specifications);
    }

    @Override
    protected Specification<T> combine(Specification<T> specs, Specification<T> next) {
        return specs.and(next);
    }
}

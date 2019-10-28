package com.example.demo01.utils;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * 组合
 *
 *
 */
public class OrSpecification<T> extends AbstractSpecification<T> {

    public OrSpecification(List<Specification> specifications) {
        super(specifications);
    }

    @Override
    protected Specification<T> combine(Specification<T> specs, Specification<T> next) {
        return specs.or(next);
    }
}

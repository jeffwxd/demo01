package com.example.demo01.utils;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public abstract class AbstractSpecification<T> implements Specification<T> {

    protected List<Specification> specifications;

    public AbstractSpecification(List<Specification> specifications) {
        this.specifications = specifications;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if(specifications.size() == 0){
            return null;
        }
        Iterator<Specification> iterator = specifications.iterator();
        Specification<T> first = iterator.next();
        Specification<T> specs = Specification.where(first);
        while (iterator.hasNext()){
            specs = combine(specs, iterator.next());
        }
        return specs.toPredicate(root, query, cb);
    }

    /**
     * 组合
     * @param specs
     * @param next
     * @return
     */
    protected abstract Specification<T> combine(Specification<T> specs, Specification<T> next);
}

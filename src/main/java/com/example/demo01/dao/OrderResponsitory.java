package com.example.demo01.dao;

import com.example.demo01.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderResponsitory extends JpaRepository<OrderEntity,Long>,
        JpaSpecificationExecutor<OrderEntity> {

}

package com.example.demo01.service.impl;

import com.example.demo01.dao.OrderResponsitory;
import com.example.demo01.entity.OrderEntity;
import com.example.demo01.model.OrderBo;
import com.example.demo01.page.Pagination;
import com.example.demo01.service.OrderService;
import com.example.demo01.utils.SpecificationBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderResponsitory orderResponsitory;
    @Override
    public Pagination<OrderBo> getList(String beginTime, String endTime,
                                       Collection<Long> merchIds, String merchName, Integer page, Integer size){
        SpecificationBuilder<OrderEntity> builder = SpecificationBuilder.builder();
        //添加查询条件
        builder.when(!CollectionUtils.isEmpty(merchIds), o->  o.in(OrderEntity.Fields.merchId, merchIds))
                .ge(OrderEntity.Fields.finishedTime, beginTime)
                .le(OrderEntity.Fields.finishedTime, endTime)
                .when(StringUtils.isNotBlank(merchName), o -> o.like(OrderEntity.Fields.merchName, "%" + merchName + "%"));
        //根据完成时间排序
        Sort sort = new Sort(Sort.Direction.DESC, "finishedTime");
        PageRequest pageData = PageRequest.of(page, size, sort);
        Page<OrderEntity> entityPage = orderResponsitory.findAll(builder.and(), pageData);
        List<OrderEntity> entities = entityPage.getContent();
        if (CollectionUtils.isEmpty(entities)) {
            return Pagination.build(new ArrayList<>(), 0);
        }
        List<OrderBo> result = new ArrayList<>();
        entities.forEach(e -> {
            result.add(new OrderBo(
                    e.getId(),
                    e.getShopId(),
                    e.getShopName(),
                    e.getMerchId(),
                    e.getMerchName()
            ));
        });
        return Pagination.build(result, entityPage.getTotalElements());
    }
}

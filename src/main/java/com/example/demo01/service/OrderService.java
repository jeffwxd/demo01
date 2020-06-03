package com.example.demo01.service;

import com.example.demo01.model.OrderBo;
import com.example.demo01.page.Pagination;

import java.util.Collection;
import java.util.List;

public interface OrderService {

     Pagination<OrderBo> getList(String beginTime, String endTime,
                                       Collection<Long> merchIds,String merchName,Integer page,Integer size);

     List<String> getList();
}

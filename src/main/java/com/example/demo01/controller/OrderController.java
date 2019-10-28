package com.example.demo01.controller;

import com.example.demo01.context.AdminSessionContext;
import com.example.demo01.model.OrderBo;
import com.example.demo01.page.Pagination;
import com.example.demo01.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@RequestMapping("/order")
@Api(description = "获取订单列表的信息")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/detail")
    @ApiOperation("获取所有明细")
    Pagination<OrderBo> getList(
            AdminSessionContext adminSessionContext,
            @RequestParam("beginTime") @ApiParam(value = "开始时间：yyyy-MM-dd hh:mm:ss", required = true) @NotNull String beginTime,
            @RequestParam("endTime") @ApiParam(value = "结束时间：yyyy-MM-dd hh:mm:ss", required = true) @NotNull String endTime,
            @RequestParam(value = "merchIds", required = false) @ApiParam("站点id") Collection<Long> merchIds,
            @RequestParam(value = "merchName", required = false) @ApiParam("站点名称") String merchName,
            @RequestParam("page") @ApiParam(value = "页码", required = true) @NotNull @Min(0) Integer page,
            @RequestParam("size") @ApiParam(value = "分页大小", required = true) @NotNull @Min(1) Integer size) {

        System.out.println(adminSessionContext.getUserName());
        return orderService.getList(beginTime, endTime, merchIds, merchName, page, size);
    }

    @GetMapping("/index")
    @ApiOperation("示例")
    public String index() {
        return "index";
    }
}

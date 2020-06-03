package com.example.demo01.test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.example.demo01.model.OrderBo;
import org.springframework.beans.BeanUtils;

/**
 * 两个对象或集合同名属性赋值
 */
public class ObjectConversion {

    /**
     * 从List<A> copy到List<B>
     *
     * @param list  List<B>
     * @param clazz B
     * @return List<B>
     */
    public static <T> List<T> copy(List<?> list, Class<T> clazz) {
        String oldOb = JSON.toJSONString(list);
        return JSON.parseArray(oldOb, clazz);
    }

    /**
     * 从对象A copy到 对象B
     *
     * @param ob    A
     * @param clazz B.class
     * @return B
     */
    public static <T> T copy(Object ob, Class<T> clazz) {
        String oldOb = JSON.toJSONString(ob);
        return JSON.parseObject(oldOb, clazz);
    }

    public static void main(String[] args) {

        OrderBo b1 = new OrderBo();
        b1.setId(1L);
        b1.setMerhName("江蘇");
		OrderBo b2 = new OrderBo();
		b2.setId(2L);
		b2.setMerhName("江西");
		List<OrderBo> bos = new ArrayList<>();
		bos.add(b1);
		bos.add(b2);
        List<OrderBo> collect = bos.stream().filter(o -> o.getId().equals(1L)).collect(Collectors.toList());
        System.out.println(collect);

    }
}


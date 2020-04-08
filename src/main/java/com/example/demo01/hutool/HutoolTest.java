package com.example.demo01.hutool;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import java.util.List;
import java.util.Map;

/**
 * @Auther wxd
 * @Date 2020-04-01 11:31
 */
public class HutoolTest {
    public static void main(String[] args) {
        ExcelReader reader = ExcelUtil.getReader(ResourceUtil.getStream("priceIndex.xls"));
        List<Map<String, Object>> readAll = reader.readAll();
        System.out.println(readAll);
    }
}
